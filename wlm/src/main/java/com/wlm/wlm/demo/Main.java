package com.wlm.wlm.demo;

import javax.swing.*;
import java.awt.*;


public class Main {
    public static void main(String[] args) {
        JFrame jf = new JFrame("用户登录");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //使用默认的浮动布局
        JPanel panel01 = new JPanel();
        panel01.add(new JLabel("用户名"));
        panel01.add(new JTextField(10));

        JPanel panel02 = new JPanel();
        panel02.add(new JLabel("密   码"));
        panel02.add(new JPasswordField(10));

        //使用浮动布局, 且容器中组件居中显示
        JPanel panel03 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel03.add(new JButton("登录"));
        panel03.add(new JButton("注册"));

        // 创建一个垂直盒子容器
        Box vBox = Box.createVerticalBox();
        vBox.add(panel01);
        vBox.add(panel02);
        vBox.add(panel03);

        jf.setContentPane(vBox);
        jf.setLocationRelativeTo(null);
        jf.setSize(900, 900);
        jf.setVisible(true);
    }

}
