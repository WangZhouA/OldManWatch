package com.saiyi.oldmanwatch.http;

/**
 * @Author liwenbo
 * @Date 18/9/14
 * @Describe 公共全局变量存放
 */
public class Constant {
    //    private static String root = "http://172.16.10.68:8086/";//网络基地址
    private static String root = "http://58.250.30.13:8952/";//网络基地址


    public static String getRoot() {
        return root;
    }

    public static void setRoot(String root) {
        Constant.root = root;
    }


    public static final int SELECT_ADDRESS = 101;
    public static final int TESTCODE = -1;
}
