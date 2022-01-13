package com.wlm.wlm.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wlm.wlm.enums.FileEnum;
import com.wlm.wlm.model.wx.WxMenu;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.UUID;

/**
 * @author wuliming
 * @date 2021/12/16 9:52
 */
@Component
@Data
public class WxUtils {

    /**
     * 获取token 200次/天
     */
    private String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=appId&secret=appSecret";

    /**
     * 菜单创建  100 次/天
     */
    private String menuCreateUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    /**
     * 获取用户基本信息  500000 次/天
     */
    private String accessUserUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";

    /**
     * 发送模板消息
     */
    private String messageSendUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    /**
     * 获取微信服务器ip地址
     */
    private String getWxApiListUrl = "https://api.weixin.qq.com/cgi-bin/get_api_domain_ip?access_token=ACCESS_TOKEN";

    /**
     * 上传图片
     */
    private String uploadImgUrl = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";

    /**
     * 上传临时素材
     */
    private String uploadMediaUrl = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

    /**
     * token  2小时有效期
     */
    private String token = "52_5VFCto3R3YgRSEgp8rGY9yNWxxZy01JXiWaGm31ynf0JUzP2i2HCARb58T-yS5rvxNt8hUSUau_23WEOtBfJYeRrF81-H5QFMq6Giuzrf_4B2-uL_Gah4PXjOhKpl1sinNM_eB9NenKigjB-PGKaAHASJN";

    private Integer expiresIn = 7200;

    private Long nowTime;

    public String getAccessToken(String appId, String appSecret) {
        Long now = System.currentTimeMillis();
        // 微信token失效时间为7200秒 在此时间内再次请求获取token时直接返回上一次保存的token
        if (nowTime != null && (now - nowTime <= expiresIn)) {
            System.out.println("直接返回token：" + token);
            return token;
        }
        JSONObject result = httpsRequest(accessTokenUrl.replace("appId", appId).replace("appSecret", appSecret), "GET", null);
        // 将token保存并记录当前时间
        token = result.getString("access_token");
        System.out.println("请求wx获取token：" + token);
        nowTime = System.currentTimeMillis();
        return result.toJSONString();
    }

    public int createMenu(WxMenu menu) {
        int result = 0;
        String url = menuCreateUrl.replace("ACCESS_TOKEN", token);
        String menuJson = JSONObject.toJSON(menu).toString();
        System.out.println(menuJson);
        JSONObject jsonObject = httpsRequest(url,"POST", menuJson);
        if (null != jsonObject) {
            result = jsonObject.getInteger("errcode");
            System.out.println("请求返回code：" + result);
        }
        return result;
    }

    public JSONObject getWxUserInfo(String openId) {
        String url = accessUserUrl.replace("ACCESS_TOKEN", token).replace("OPENID", openId);
        return this.httpsRequest(url, "GET", null);
    }


    public String uploadImg(File file) {
        if (!file.exists() || !file.isFile()) {
            throw new RuntimeException("文件不存在");
        }
        String url = uploadMediaUrl.replace("ACCESS_TOKEN", token).replace("TYPE", "image");

        StringBuilder sb = new StringBuilder();
        try {
            HttpsURLConnection conn = getHttpsConn(url);
            if (null == conn) {
                throw new RuntimeException("建立网络连接失败");
            }
            // 设置请求方式
            conn.setRequestMethod("POST");
            // 输入输出开启
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // 设置请求头信息
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");

            // 设置边界
            String boundary = "----------" + System.currentTimeMillis();
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            // 请求正文信息
            // 第一部分：
            // 必须多两道线
            sb.append("--");
            sb.append(boundary);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"file\";filename=\"").append(file.getName()).append("\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");

            byte[] head = sb.toString().getBytes(StandardCharsets.UTF_8);
            // 获得输出流
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // 输出表头
            out.write(head);

            // 文件正文部分
            // 把文件已流文件的方式 推入到url中
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            int bytes;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            in.close();

            // 结尾部分
            // 定义最后数据分隔线
            byte[] foot = ("\r\n--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8);
            out.write(foot);
            out.flush();
            out.close();


            readResultInput(sb, conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();

    }

    private void readResultInput(StringBuilder sb, HttpsURLConnection conn) throws IOException {
        InputStream is = conn.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader bis = new BufferedReader(isr);
        String str;
        while ((str = bis.readLine()) != null) {
            sb.append(str);
        }
        bis.close();
        isr.close();
        is.close();
        conn.disconnect();
    }

    private JSONObject httpsRequest(String url, String questType, String outputStr) {
        String getType = "GET";
        StringBuilder sb = new StringBuilder();
        JSONObject result = null;
        try {
            HttpsURLConnection conn = getHttpsConn(url);
            if (null == conn) {
                throw new RuntimeException("建立网络连接失败");
            }
            // 设置请求方式
            conn.setRequestMethod(questType);
            if (getType.equalsIgnoreCase(questType)) {
                conn.getContent();
            }
            if (null != outputStr) {
                // 输入输出开启
                conn.setDoOutput(true);
                conn.setDoInput(true);
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.write(outputStr.getBytes(StandardCharsets.UTF_8));
                out.flush();
                out.close();
            }
            readResultInput(sb, conn);
            result = JSON.parseObject(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private HttpsURLConnection getHttpsConn(String url) {
        HttpsURLConnection conn = null;
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {}
                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {}
                @Override
                public X509Certificate[] getAcceptedIssuers() {return null;}
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = ctx.getSocketFactory();
            URL httpUrl = new URL(url);
            //建立连接
            conn = (HttpsURLConnection) httpUrl.openConnection();
            conn.setSSLSocketFactory(ssf);
            //设置不要缓存
            conn.setUseCaches(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public String uploadImgByCurl(String filePath) {
        String url = uploadMediaUrl.replace("ACCESS_TOKEN", token).replace("TYPE", "image");
        String[] cmd = {"curl -F \"file=@" + filePath + "\"", url};
        return execCurl(cmd);
    }

    private String execCurl(String[] cmd) {
        ProcessBuilder process = new ProcessBuilder(cmd);
        Process p;
        try {
            p = process.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            return builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String commUpload(MultipartFile file, String uploadDateStr, String system) {
        String basePath = "/root/temp/";
        // 获取文件名称
        String fileName = file.getOriginalFilename();
        // 获取文件扩展名
        String fileExt = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
        // 获取文件类型
        Integer fileType = FileEnum.getFileType(fileExt);
        // 定义文件存放目录
        String dirPath = system + "/" + uploadDateStr + "/" + FileEnum.getFilePath(fileExt) +"/";
        // 获取文件具体路径
        String newFilePath = dirPath + UUID.randomUUID() + "." + fileExt;
        // 文件存放目录是否存在,不存在新增
        File uploadFilePath = new File(basePath + dirPath);
        if (!uploadFilePath.exists()) {
            uploadFilePath.mkdirs();
        }
        // 新建文件
        File newFile = new File(basePath + newFilePath);
        try {
            FileCopyUtils.copy(file.getBytes(), newFile);
            // 获取文件缩略图路径,视频第一帧图片路径等
//            String snapUrl = createOtherFile(newFilePath, fileExt);
            // 获取文件大小
//            String fileSize = FileUtil.getFileSize(newFile, fileExt, fileType);
            System.out.println(newFile.getPath());
            return uploadImg(newFile);
//            uploadImgByCurl(newFile.getPath());
        } catch (IOException e) {
            throw new RuntimeException("文件复制异常!文件路径为:" + newFilePath);
        }
    }
}
