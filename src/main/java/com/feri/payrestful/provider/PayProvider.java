package com.feri.payrestful.provider;

import com.feri.payrestful.pojo.PayOrder;
import com.feri.payrestful.pojo.PayResult;
import com.feri.payrestful.pojo.WxChatConfig;
import com.feri.payrestful.service.PayService;
import com.feri.payrestful.util.Base64Util;
import com.feri.payrestful.util.HttpUtil;
import com.feri.payrestful.vo.ResultVO;
import com.feri.payrestful.wxchatpay.WXPay;
import com.feri.payrestful.wxchatpay.WXPayConstants;
import com.feri.payrestful.wxchatpay.WXPayReport;
import com.feri.payrestful.wxchatpay.WXPayUtil;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 *@Author feri
 *@Date Created in 2019/1/23 15:28
 */
@Service
public class PayProvider implements PayService {
    @Override
    public ResultVO unifiedorder(PayOrder payOrder) {
        try {
            WxChatConfig chatConfig=new WxChatConfig();
            WXPay wxPay=new WXPay(new WxChatConfig());
            Map<String,String> data=new HashMap<>();
            data.put("appid",chatConfig.getAppID());
            data.put("mch_id",chatConfig.getMchID());
            data.put("nonce_str",UUID.randomUUID().toString());
            data.put("body",payOrder.getInfo());
//            data.put("out_trade_no",(new Random().nextInt(1000000)+10000)+"xph");
            data.put("out_trade_no",payOrder.getId());
           // data.put("total_fee",payOrder.getMoney()+"");
            data.put("total_fee","1");
            data.put("spbill_create_ip",payOrder.getRequestip());
            data.put("notify_url","http://10.8.155.45/pay");
            data.put("trade_type","NATIVE");

            //https://api.mch.weixin.qq.com/pay/unifiedorder
            Map<String,String> responseMap=WXPayUtil.xmlToMap(HttpUtil.httpRequest(WXPayUtil.mapToXml(wxPay.fillRequestData(data)),"https://api.mch.weixin.qq.com/pay/unifiedorder"));
            if(responseMap.get("return_code").equals("SUCCESS")) {
//                if (WXPayUtil.isSignatureValid(responseMap, responseMap.get("sign"), WXPayConstants.SignType.MD5)) {
                    if(responseMap.get("result_code").equals("SUCCESS")){
                        PayResult result=new PayResult();
                        result.setCodeurl(responseMap.get("code_url"));
                        result.setPrepayid(responseMap.get("prepay_id"));
                        result.setPayurl(WXPayConstants.EWMURL+"/createqrcode.do?msg="+Base64Util.base64Enc(result.getCodeurl().getBytes()));
                        return ResultVO.execOK(result);
                    }else{
                        return ResultVO.exec(0,"支付结果错误");
                    }
//                }else {
//                    return ResultVO.exec(0,"签名错误");
//                }
            }else {
                return  ResultVO.exec(0,responseMap.get("return_msg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultVO.execERROR();
    }

    @Override
    public ResultVO queryOrder(String id) {
        try {
            WxChatConfig chatConfig=new WxChatConfig();
            WXPay wxPay=new WXPay(new WxChatConfig());
            Map<String,String> data=new HashMap<>();
            data.put("appid",chatConfig.getAppID());
            data.put("mch_id",chatConfig.getMchID());
            data.put("nonce_str",UUID.randomUUID().toString());
            data.put("out_trade_no",id);

            //https://api.mch.weixin.qq.com/pay/unifiedorder
            Map<String,String> responseMap=WXPayUtil.xmlToMap(HttpUtil.httpRequest(WXPayUtil.mapToXml(wxPay.fillRequestData(data)),
                    "https://api.mch.weixin.qq.com/pay/orderquery"));
            if(responseMap.get("return_code").equals("SUCCESS")) {
                if(responseMap.containsKey("trade_state")){
                    return ResultVO.exec(1,responseMap.get("trade_state"));
                }else {
                    return ResultVO.execERROR();
                }
            }else {
                return  ResultVO.exec(0,responseMap.get("return_msg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultVO.execERROR();
    }

    @Override
    public ResultVO canelOrder(String id) {
        //https://api.mch.weixin.qq.com/pay/closeorder
        try {
            WxChatConfig chatConfig=new WxChatConfig();
            WXPay wxPay=new WXPay(new WxChatConfig());
            Map<String,String> data=new HashMap<>();
            data.put("appid",chatConfig.getAppID());
            data.put("mch_id",chatConfig.getMchID());
            data.put("nonce_str",UUID.randomUUID().toString());
            data.put("out_trade_no",id);
            Map<String,String> responseMap=WXPayUtil.xmlToMap(HttpUtil.httpRequest(WXPayUtil.mapToXml(wxPay.fillRequestData(data)),
                    "https://api.mch.weixin.qq.com/pay/closeorder"));
            if(responseMap.get("return_code").equals("SUCCESS")) {
                if(responseMap.containsKey("result_code")){
                    if(responseMap.get("result_code").equals("SUCCESS")){
                        return ResultVO.execOK("订单取消成功");
                    }else {
                        return ResultVO.execERROR(responseMap.get("err_code_des"));
                    }
                }else {
                    return ResultVO.execERROR();
                }
            }else {
                return  ResultVO.exec(0,responseMap.get("return_msg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultVO.execERROR();
    }

    @Override
    public ResultVO downZd(String billdate) {
        if(billdate.matches("[0-9]{8}")){
            //https://api.mch.weixin.qq.com/pay/downloadbill
            try {
                WxChatConfig chatConfig = new WxChatConfig();
                WXPay wxPay = new WXPay(new WxChatConfig());
                Map<String, String> data = new HashMap<>();
                data.put("appid", chatConfig.getAppID());
                data.put("mch_id", chatConfig.getMchID());
                data.put("nonce_str", UUID.randomUUID().toString());
                data.put("bill_date", billdate);
                data.put("sign_type","MD5");
                data.put("bill_type", "ALL");
                System.out.println(WXPayUtil.mapToXml(wxPay.fillRequestData(data)));
                //data.put("bill_date",new SimpleDateFormat("yyyyMMdd").format())
                String responseMap = HttpUtil.httpRequest(WXPayUtil.mapToXml(wxPay.fillRequestData(data)),
                        "https://api.mch.weixin.qq.com/pay/closeorder");
                return ResultVO.execOK(responseMap);
            }catch (Exception e){

            }
        }
        return ResultVO.execERROR();
    }
}
