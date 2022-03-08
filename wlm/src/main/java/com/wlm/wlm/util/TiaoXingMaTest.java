package com.wlm.wlm.util;

import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * 条形码测试
 * @author wuliming
 *
 */
public class TiaoXingMaTest {
	
	public static File generateFile(String msg, String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
		try {
			generate(msg, new FileOutputStream(file));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return file;
	}
	
	public static byte[] generate(String msg) {
        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        generate(msg, ous);
        return ous.toByteArray();
    }
	
	public static void generate(String msg, OutputStream out) {
		if (null == msg || "".equals(msg) || out == null) {
			return;
		}
		Code39Bean bean = new Code39Bean();
		// 精细度
		final int dpi = 150;
		// module 	宽度
		final double moduleWidth = UnitConv.in2mm(1.0f / dpi);
		
		// 配置对象
		bean.setModuleWidth(moduleWidth);
		bean.setWideFactor(3);
		bean.doQuietZone(false);
		
		String format = "image/png";
		
		try {
			BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, format, dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
			bean.generateBarcode(canvas, msg);
			
			canvas.finish();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public static void main(String[] args) {
		String msg = "12354343";
		String path = "D:/img/test_13.png";
		generateFile(msg, path);
		System.out.println("条形码生成: == " + generateFile(msg, path));
	}

}
