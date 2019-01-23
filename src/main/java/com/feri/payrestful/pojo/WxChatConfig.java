package com.feri.payrestful.pojo;

import com.feri.payrestful.wxchatpay.WXPayConfig;
import com.feri.payrestful.wxchatpay.WXPayConstants;
import java.io.InputStream;
/**
 *@Author feri
 *@Date Created in 2019/1/23 15:28
 */
public class WxChatConfig extends WXPayConfig {
    @Override
    public String getAppID() {
        return WXPayConstants.APP_ID;
    }

    @Override
    public String getMchID() {
        return WXPayConstants.MCH_ID;
    }

    @Override
    public String getKey() {
        return WXPayConstants.API_KEY;
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }
}
