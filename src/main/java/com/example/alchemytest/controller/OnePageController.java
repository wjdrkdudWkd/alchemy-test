package com.example.alchemytest.controller;

import com.example.alchemytest.service.OnePageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/onepage")
@Api(tags = {"One Page API"})
public class OnePageController {

    private final OnePageService onePageService;

    @ApiOperation(value = "Create Order", notes = "https://www.showdoc.com.cn/2249608605068913/10094008147696159")
    @GetMapping("/v1/order/create")
    public ResponseEntity<Object> createOrder() {
        // 결제 창 호출 (금액입력)
        // https://ramp.alchemypay.org?fiat=KRW&amount=33000.00&crypto=USDT&network=BSC&alpha2=KR&language=en-US
        return new ResponseEntity<>(onePageService.createOrder(), HttpStatus.OK);
    }

    @ApiOperation(value = "Query Order's Status")
    @GetMapping("/v1/order/queryOrder")
    public ResponseEntity<Object> getQueryOrder(String tradeNo) {
        return new ResponseEntity<>(onePageService.getQueryOrder(tradeNo), HttpStatus.OK);
    }
}
