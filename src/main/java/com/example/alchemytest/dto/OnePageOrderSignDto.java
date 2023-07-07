package com.example.alchemytest.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OnePageOrderSignDto {

    private String merchantNum = "TEST000001";
    private String merchantOrderNum = String.valueOf(Math.floor(Math.random() * 1000000001));
    private String currency = "USD";
    private String amount = "1500";
    private String payment = "BSC_USDT";


    @Builder
    public OnePageOrderSignDto() {
        this.merchantNum = "TEST000001";
        this.merchantOrderNum = String.valueOf(Math.floor(Math.random() * 1000000001));
        this.currency = "KRW";
        this.amount = "3300000"; // .00
        this.payment = "BSC_USDT";
    }
}
