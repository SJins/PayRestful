package com.feri.payrestful.pay;

import com.feri.payrestful.util.ZxingUtil;

/**
 *@Author feri
 *@Date Created in 2019/1/23 16:09
 */
public class Qrcode_Test {
    public static void main(String[] args) {
        System.out.println(ZxingUtil.encode("你好，我是二维码","JPEG",400,400,"qrcode.jpeg"));
    }
}
