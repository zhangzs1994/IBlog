package com.tydk.iblog.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: zzs
 * @date: 2019/10/14 0014 上午 10:55
 * @description: 自定义设置布局ID注解
 *      元注解、@Retention、@Target。
 *      Retention：注解的保留期
            RetentionPolicy.SOURCE 注解只在源码阶段保留，在编译器进行编译时它将被丢弃忽视。
            RetentionPolicy.CLASS 注解只被保留到编译进行的时候，它并不会被加载到 JVM 中。
            RetentionPolicy.RUNTIME 注解可以保留到程序运行的时候，它会被加载进入到 JVM 中，所以在程序运行时可以获取到它们。

        Target：作用域
            ElementType.ANNOTATION_TYPE 可以给一个注解进行注解
            ElementType.CONSTRUCTOR 可以给构造方法进行注解
            ElementType.FIELD 可以给属性进行注解
            ElementType.LOCAL_VARIABLE 可以给局部变量进行注解
            ElementType.METHOD 可以给方法进行注解
            ElementType.PACKAGE 可以给一个包进行注解
            ElementType.PARAMETER 可以给一个方法内的参数进行注解
            ElementType.TYPE 可以给一个类型进行注解，比如类、接口、枚举
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ContentView {

    int value() default -1;

}
