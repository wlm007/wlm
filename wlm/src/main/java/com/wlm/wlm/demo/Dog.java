package com.wlm.wlm.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuliming
 * @date 2021/7/19 11:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dog implements Cloneable{

    private People people;

    private String name;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Dog dog = (Dog) super.clone();
        dog.people = (People) people.clone();
        return dog;
    }
}
