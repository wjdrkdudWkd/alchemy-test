package com.example.alchemytest.util.onepage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;


public class SHA512Utils {
    /**
     * One Page
     * https://www.showdoc.com.cn/2249608605068913/10094007884920298
     */

    public static final String ENCODE = "UTF-8";
    private static Logger logger = LoggerFactory.getLogger(SHA512Utils.class);

    /**
     * Sign the parameter with SHA
     *
     * @param signParams
     * @param key
     * @return
     */
    public static String SHAEncrypt(SortedMap<Object, Object> signParams, String key) {
        String sign = null;
        StringBuffer sb = new StringBuffer();
        Set es = signParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !v.equals("null") && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);
        logger.info("signStr: {}", sb);
        sign = encrypt(sb.toString(), ENCODE).toUpperCase();
        logger.info("sign: {}", sign);
        return sign;
    }

    public static String encrypt(String aValue, String encoding) {
        aValue = aValue.trim();
        byte value[];
        try {
            value = aValue.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            value = aValue.getBytes();
        }
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        return toHex(md.digest(value));
    }

    public static String toHex(byte input[]) {
        if (input == null) return null;
        StringBuffer output = new StringBuffer(input.length * 2);
        for (int i = 0; i < input.length; i++) {
            int current = input[i] & 0xff;
            if (current < 16) output.append("0");
            output.append(Integer.toString(current, 16));
        }
        return output.toString();
    }

    /**
     * Verfify
     *
     * @param signParams
     * @param key
     * @return
     */
    public static boolean verifySHA(SortedMap<Object, Object> signParams, String key) {
        String verifySign = (String) signParams.get("sign");
        String sign = SHAEncrypt(signParams, key);
        if (sign.equalsIgnoreCase(verifySign)) {
            return true;
        }
        return false;
    }
}
