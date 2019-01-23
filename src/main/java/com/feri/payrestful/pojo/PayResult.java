package com.feri.payrestful.pojo;

/**
 *@Author feri
 *@Date Created in 2019/1/23 15:45
 */
public class PayResult {
    private String prepayid;
    private String codeurl;
    private String payurl;

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getCodeurl() {
        return codeurl;
    }

    public void setCodeurl(String codeurl) {
        this.codeurl = codeurl;
    }

    public String getPayurl() {
        return payurl;
    }

    public void setPayurl(String payurl) {
        this.payurl = payurl;
    }
}
