package com.wlm.wlm.jfarmDemo;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;


/**
 * @author wuliming
 */
public class HelloWorld1 extends Application {

    @Override
    public void start(Stage primaryStage) {

        // 创建一个标签，用于存放我们的Hello World文本，并设置让它在父容器中居中
        Label label = new Label("hello");
        label.setAlignment(Pos.CENTER);

        // 设置字体大小、颜色（当然我们也可以用css来设置，先这样写） 或者顺带设置字体
        label.setFont(Font.font(30));
        label.setTextFill(Color.web("#FFFFFF"));
        label.setBackground(new Background(new BackgroundFill(Paint.valueOf("black"), null, null)));
        // 设置设置图标

        // 三部曲 1、初始化一个场景
        Scene scene = new Scene(label, 400, 300);
        // 2、将场景放入窗口
        primaryStage.setScene(scene);
        // 3、打开窗口
        primaryStage.show();
    }

    public static void main(String[] args) throws CloneNotSupportedException {

        People people1 = new People(1, "张三");
        People people2 = (People) people1.clone();

        Dog dog1 = new Dog(people1, "张三狗");
        Dog dog2 = (Dog) dog1.clone();

        System.out.println(people1 == people2);
        System.out.println(people1.equals(people2));
        System.out.println(dog2.getPeople() == people1);
        System.out.println(dog2.getPeople().equals(people1));
        System.out.println(dog2.getPeople() == people2);
        System.out.println(dog2.getPeople().equals(people2));
    }
}
