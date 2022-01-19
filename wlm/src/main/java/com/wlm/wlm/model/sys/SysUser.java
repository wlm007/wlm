package com.wlm.wlm.model.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wuliming
 * @date 2021/7/20 14:52
 */
@Data
public class SysUser implements Serializable {

    private static final long serialVersionUID = 598763157665470792L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer age;

    private String username;

    private String password;

    private String email;

    private String deptNo;

    private String roleNo;
}
