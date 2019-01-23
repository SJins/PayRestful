package com.feri.payrestful.pay;

import com.feri.payrestful.wxchatpay.*;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 *@Author feri
 *@Date Created in 2019/1/23 14:46
 */
public class Pay_Test {
    public static String APP_ID = "wx632c8f211f8122c6";
    public static String MCH_ID = "1497984412";
    public static String API_KEY = "sbNCm1JnevqI36LrEaxFwcaT0hkGxFnC";

    public static void main(String[] args) throws Exception {
        WXPay wxPay=new WXPay(new WXPayConfig() {

            @Override
            public String getAppID() {
                return APP_ID;
            }

            @Override
            public String getMchID() {
                return MCH_ID;
            }

            @Override
            public String getKey() {
                return API_KEY;
            }

            @Override
            public InputStream getCertStream() {
                return null;
            }
        });
        Map<String,String> data=new LinkedHashMap<>();
        data.put("appid",APP_ID);
        data.put("mch_id",MCH_ID);
        data.put("nonce_str",UUID.randomUUID().toString());
        data.put("body","千锋学习Java");
        data.put("out_trade_no",(new Random().nextInt(1000000)+10000)+"xph");
        data.put("total_fee","1");
        data.put("spbill_create_ip","10.8.155.45");
        data.put("notify_url","http://10.8.155.45/pay");
        data.put("trade_type","NATIVE");
        Map<String,String> signMap=wxPay.fillRequestData(data);
        WXPayReport.REPORT_URL="https://api.mch.weixin.qq.com/pay/unifiedorder";
        String requestParm=WXPayUtil.mapToXml(signMap);
        System.out.println("请求参数："+requestParm);
        String xml=WXPayReport.httpRequest(requestParm,8000,8000);
        System.out.println("响应内容："+xml);
        Map<String,String> response=WXPayUtil.xmlToMap(xml);
        System.out.println("获取支付信息："+response);
        if(response.get("return_code").equals("SUCCESS")) {
            if (WXPayUtil.isSignatureValid(response, response.get("sign"), WXPayConstants.SignType.MD5)) {
                if(response.get("result_code").equals("SUCCESS")){
                    String url=response.get("code_url");
                    //生成二维码
                    //返回给用户

                }

            }
        }else {
            System.out.println(response.get("return_msg"));
        }


    }
}
