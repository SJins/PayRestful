package com.feri.payrestful.controller;

import com.feri.payrestful.pojo.PayOrder;
import com.feri.payrestful.pojo.PayResult;
import com.feri.payrestful.service.PayService;
import com.feri.payrestful.util.Base64Util;
import com.feri.payrestful.util.ZxingUtil;
import com.feri.payrestful.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *@Author feri
 *@Date Created in 2019/1/23 15:20
 */
@RestController
@Api(value = "微信支付",tags = "微信支付相关")
public class PayController {

    @Autowired
    private PayService payService;
    //生成支付信息
    @ApiOperation(value = "统一下单接口",notes = "实现下单的功能")
    @PostMapping("wxpay.do")
    public ResultVO pay(@RequestBody PayOrder payOrder, HttpServletRequest request){
        payOrder.setRequestip(request.getRemoteAddr());
        return payService.unifiedorder(payOrder);
    }
    //生成支付二维码
    @ApiOperation(value = "生成二维码",notes = "生成二维码，内容使用Base64进行编码")
    @GetMapping("createqrcode.do")
    public void createqrcode(String msg, HttpServletResponse response) throws IOException {
        BufferedImage bufferedImage=ZxingUtil.createImage(new String(Base64Util.base64Dec(msg)),300,300);
        ImageIO.write(bufferedImage,"JPEG",response.getOutputStream());
    }

    //查询订单支付状态
    @ApiOperation(value = "查询订单状态",notes = "查询订单的状态，传递订单号")
    @GetMapping("wxpayquery.do")
    public ResultVO querystatus(String id){
        return payService.queryOrder(id);
    }
    //关闭订单
    @ApiOperation(value = "取消订单",notes = "取消订单，传递订单号")
    @GetMapping("wxpaycancel.do")
    public ResultVO cancel(String id){
        return payService.canelOrder(id);
    }

    //下载指定日期的对账单
    @ApiOperation(value = "查询指定日期的对账单",notes = "查询指定日期的对账，日期格式：20190123")
    @GetMapping("wxpaybill.do")
    public ResultVO querybill(String date){
        return payService.downZd(date);
    }

}