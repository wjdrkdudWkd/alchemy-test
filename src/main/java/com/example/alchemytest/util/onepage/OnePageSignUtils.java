package com.example.alchemytest.util.onepage;

import java.util.SortedMap;
import java.util.TreeMap;

public class OnePageSignUtils {
    /**
     * https://www.showdoc.com.cn/2249608605068913/10094007884920298
     */


    /**
     * @param merchantNum         Merchant No
     * @param merchantOrderNum Merchant Order No
     * @param currency                Fiat Code
     * @param amount                 Amount (based in cent)
     * @param payment               Pay Method   BSC_USDT
     * @param secrete                Secret
     * @return
     */
    public static String getOrderSign(String merchantNum, String merchantOrderNum, String currency, String amount, String payment, String secrete) {
        SortedMap<Object, Object> map = new TreeMap<>();
        map.put("merchantNum", merchantNum);
        map.put("merchantOrderNum", merchantOrderNum);
        map.put("currency", currency);
        map.put("amount", amount);
        map.put("payment", payment);
        String sign = SHA512Utils.SHAEncrypt(map, secrete);
        return sign;
    }
    /**
     * @param merchantNum      Merchant No
     * @param tradeNo          Trade No
     * @param nonceStr         Random String
     * @param secrete          Secreet
     * @return
     */
    public static String getQuerySign(String merchantNum, String tradeNo, String nonceStr, String secrete) {
        SortedMap<Object, Object> map = new TreeMap<>();
        map.put("merchantNum", merchantNum);
        map.put("tradeNo", tradeNo);
        map.put("nonceStr", nonceStr);
        String sign = SHA512Utils.SHAEncrypt(map, secrete);
        return sign;
    }
    public static void main(String[] args) {
        //String sign = getQuerySign("TEST000001", "100216790500458820001", "234983298", "6fdbaac29eb94bc6b36547ad705e9298");
        String sign = getOrderSign("TEST000001", "100216789632012990006", "USD", "1500", "BSC_USDT","6fdbaac29eb94bc6b36547ad705e9298");
        System.out.println(sign);
    }
    /**
     * @param merchantNum      Merchant No
     * @param merchantOrderNum Merchant Order No
     * @param currency          Fiat Code
     * @param amount            Amount (based in cent)
     * @param payment          Pay Method   BSC_USDT
     * @param orderStatus      Order Status
     * @param secrete          Secret
     * @return
     */
    public static String getCallbackSign(String merchantNum, String merchantOrderNum, String currency, String amount, String payment,String orderStatus, String secrete) {
        SortedMap<Object, Object> map = new TreeMap<>();
        map.put("merchantNum", merchantNum);
        map.put("merchantOrderNum", merchantOrderNum);
        map.put("currency", currency);
        map.put("amount", amount);
        map.put("payment", payment);
        map.put("orderStatus", orderStatus);
        String sign = SHA512Utils.SHAEncrypt(map, secrete);
        return sign;
    }
}
