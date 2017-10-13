package sdk.hhyk.com.libhhyk_sdk.entity;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import sdk.hhyk.com.libhhyk_sdk.Util.StringUtil;

/**
 * Created by Aven 2017/10/12.
 */

public class OrderModel {
//    merchantid	商户编号	String(20)	必填	平台为商户分配的唯一编号
//    outtradeno	商户订单号	String(64)	必填	商户生成的订单号，需要保证商户唯一
//    商户订单号在支付平台已经存在的处理逻辑如下：如果订单未支付成功、支付金额和币种与前一笔相同，平台应答时返回前一笔生成的transid，且其它支付信息都以前一笔为准；否则平台会返回订单号重复错误。
//    subject	商品名称	String(64)	必填	商品名称
//    amount	支付金额	String(12)	必填	支付金额
//    取值范围为[0.01，100000000.00]，精确到小数点后两位
//    currency	货币类型	String(3)	必填	货币类型，参见ISO4217
//    notifyurl	支付结果通知地址	String(128)	必填	商户服务端接收支付结果通知的地址
//    customerid	用户标识	String(64)	必填	用户在商户端的唯一标识
//    注：为了保证用户的支付数据安全，customerid必须可以唯一标识一个用户

private String merchantid;
    private String outtradeno;
    private String subject;
    private String amount;
    private String currency;
    private String notifyurl;
    private String customerid;

    public String getMerchantid() {
        return merchantid;
    }

    /**
     *
     * @param merchantid商户编号
     */
    public void setMerchantid(String merchantid) {
        this.merchantid = merchantid;
    }

    public String getOuttradeno() {
        return outtradeno;
    }

    /**
     *
     * @param outtradeno 商户订单号
     */
    public void setOuttradeno(String outtradeno) {
        this.outtradeno = outtradeno;
    }

    public String getSubject() {
        return subject;
    }

    /**
     *
     * @param subject 商品名称
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAmount() {
        return amount;
    }

    /**
     *
     * @param amount 支付金额
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    /**
     *
     * @param currency 货币类型
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNotifyurl() {
        return notifyurl;
    }

    public void setNotifyurl(String notifyurl) {
        this.notifyurl = notifyurl;
    }

    public String getCustomerid() {
        return customerid;
    }

    /**
     *
     * @param customerid 用户标识
     */
    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    @Override
    public String toString() {
        JSONObject jsonObject=new JSONObject();

        checkParamValue();
        try {
            jsonObject.put("merchantid",merchantid);
            jsonObject.put("outtradeno",outtradeno);
            jsonObject.put("subject",subject);
            jsonObject.put("amount",amount);
            jsonObject.put("currency",currency);
            jsonObject.put("notifyurl",notifyurl);
            jsonObject.put("customerid",customerid);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonObject.toString();
    }
    public Map<String ,String> toMap(){
        Map<String ,String > map=new HashMap<String ,String >() ;
        checkParamValue();
        map.put("merchantid",merchantid);
        map.put("outtradeno",outtradeno);
        map.put("subject",subject);
        map.put("amount",amount);
        map.put("currency",currency);
        map.put("notifyurl",notifyurl);
        map.put("customerid",customerid);


return map;

    }

    private void checkParamValue(){
        if (StringUtil.isEmpty(merchantid)){
            merchantid="";
        }
        if (StringUtil.isEmpty(outtradeno)){
            outtradeno="";
        }
        if (StringUtil.isEmpty(subject)){
            subject="";
        }
        if (StringUtil.isEmpty(amount)){
            amount="";
        }
        if (StringUtil.isEmpty(currency)){
            currency="";
        }
        if (StringUtil.isEmpty(notifyurl)){
            notifyurl="";
        }
        if (StringUtil.isEmpty(customerid)){
            customerid="";
        }

    }
}
