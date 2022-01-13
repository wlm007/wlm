package com.wlm.wlm.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuliming
 * @date 2021/7/19 11:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class People implements Cloneable{

    private int age;

    private String name;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
