package com.feri.payrestful.pojo;

/**
 *@Author feri
 *@Date Created in 2019/1/23 15:24
 */
public class PayOrder {
    private String id;//订单编号
    private int money;//钱  单位分
    private String info;//描述信息
    private String requestip;

    public String getRequestip() {
        return requestip;
    }

    public void setRequestip(String requestip) {
        this.requestip = requestip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
