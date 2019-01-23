package com.feri.payrestful.service;

import com.feri.payrestful.pojo.PayOrder;
import com.feri.payrestful.vo.ResultVO;

/**
 *@Author feri
 *@Date Created in 2019/1/23 15:26
 */
public interface PayService {
    //统一下单
    ResultVO unifiedorder(PayOrder payOrder);
    //查询订单状态
    ResultVO queryOrder(String id);
    //关单
    ResultVO canelOrder(String id);
    //下载对账单
    ResultVO downZd(String billdate);


}
