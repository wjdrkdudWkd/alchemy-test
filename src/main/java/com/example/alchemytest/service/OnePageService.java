package com.example.alchemytest.service;

import com.example.alchemytest.dto.OnePageOrderSignDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.example.alchemytest.util.onepage.OnePageSignUtils.getOrderSign;
import static com.example.alchemytest.util.onepage.OnePageSignUtils.getQuerySign;

@Service
@RequiredArgsConstructor
public class OnePageService {

    @Value("${onepage.domain}")
    String domain;

    @Value("${onepage.app.secret}")
    String appSecret;

    /**
     * Create Order
     * https://www.showdoc.com.cn/2249608605068913/10094008147696159
     * @return
     */
    public Map<String, Object> createOrder() {
        OnePageOrderSignDto orderSignDto = OnePageOrderSignDto.builder().build();
        String orderSign = getOrderSign(orderSignDto.getMerchantNum(), orderSignDto.getMerchantOrderNum(), orderSignDto.getCurrency(), orderSignDto.getAmount(), orderSignDto.getPayment(), appSecret);
        System.out.println("orderSign = " + orderSign);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount", orderSignDto.getAmount());
        jsonObject.put("callbackUrl", "https://dev-api.paygobd.com/tradeCore/mock/callback/callback/success");
        jsonObject.put("cancelUrl", "https://dev.lmnova.com/");
        jsonObject.put("currency", orderSignDto.getCurrency());
        jsonObject.put("email", "wjdrkdudwkd@playnomm.com");
        jsonObject.put("orderDetails", "{\"photo\": \"https://img0.baidu.com/it/u=2345533827,3392778732&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500\",\"describe\": \"주문 설명\"}");
        jsonObject.put("expiredTime", "7200"); // 유효 시간 : 중국기준 -3600
        jsonObject.put("merchantNum", orderSignDto.getMerchantNum());
        jsonObject.put("merchantOrderNum", orderSignDto.getMerchantOrderNum()); // 중복시 error
        jsonObject.put("payment", orderSignDto.getPayment());
        jsonObject.put("remark", "remark");
        jsonObject.put("returnUrl", "https://dev.lmnova.com/");
        jsonObject.put("sign", orderSign);
        jsonObject.put("language", "EN");

        Map<String, Object> result = requestApi(domain, "/api/v1.0/order/create", "POST", jsonObject);
        return result;
    }

    /**
     * Query Order's Status
     * https://www.showdoc.com.cn/2249608605068913/10094010039789465
     * @return
     */
    public Map<String, Object> getQueryOrder(String tradeNo) {
        String uuid = UUID.randomUUID().toString();
        OnePageOrderSignDto orderSignDTO = OnePageOrderSignDto.builder().build();
        String querySign = getQuerySign(orderSignDTO.getMerchantNum(), tradeNo, uuid, appSecret);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("merchantNum", orderSignDTO.getMerchantNum());
        jsonObject.put("nonceStr", uuid);
        jsonObject.put("sign", querySign);
        jsonObject.put("tradeNo", tradeNo);
        jsonObject.put("language", "EN");

        Map<String, Object> result = requestApi(domain, "/api/v1.0/order/queryOrder", "POST", jsonObject);
        return result;
    }

    public Map<String, Object> requestApi(String domain, String apiUrl, String method, JSONObject jsonParam) {

        try {
            URL url = new URL(domain + apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);

            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            if(jsonParam != null) {
                try(OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonParam.toJSONString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String line = "";
            StringBuffer result = new StringBuffer();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            int responseCode = conn.getResponseCode();

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(result.toString());
            JSONObject element = (JSONObject) obj;

            br.close();
            return element;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }
}
