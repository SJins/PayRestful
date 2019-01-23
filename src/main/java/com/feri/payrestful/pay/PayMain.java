package com.feri.payrestful.pay;

import com.feri.payrestful.pojo.WxChatConfig;
import com.feri.payrestful.util.HttpUtil;
import com.feri.payrestful.wxchatpay.WXPay;
import com.feri.payrestful.wxchatpay.WXPayUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
/**
 *@Author feri
 *@Date Created in 2019/1/23 16:52
 */
public class PayMain {
    public static void main(String[] args) throws Exception {
        WxChatConfig chatConfig = new WxChatConfig();
        WXPay wxPay = new WXPay(new WxChatConfig());
        Map<String, String> data = new HashMap<>();
        data.put("appid", chatConfig.getAppID());
        data.put("mch_id", chatConfig.getMchID());
        data.put("nonce_str", UUID.randomUUID().toString());
        data.put("bill_date", "20170603");
        data.put("sign_type","MD5");
        data.put("bill_type", "ALL");
        System.out.println(WXPayUtil.mapToXml(wxPay.fillRequestData(data)));
        //data.put("bill_date",new SimpleDateFormat("yyyyMMdd").format())
        String responseMap = HttpUtil.httpRequest(WXPayUtil.mapToXml(wxPay.fillRequestData(data)),
                "https://api.mch.weixin.qq.com/pay/closeorder");
        System.out.println((responseMap));
    }

}
