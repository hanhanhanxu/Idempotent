package com.example.idempotent.controller;

import com.example.idempotent.handler.OrderHandler;
import com.example.idempotent.model.ReqParam;
import com.example.idempotent.model.TOrder;
import com.example.idempotent.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("api")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderHandler orderHandler;


    /**
     * 支付宝回调接口
     * 对接支付宝，用户使用支付宝支付我们的订单，支付成功后回调我们.
     * 此接口要求幂等，因为可能一次支付由于网络原因多次回调
     * @param param
     */
    @RequestMapping("recharge")
    public String alipayRecharge(@RequestBody ReqParam param) {
        String tradeNo = param.getTradeNo();
        //1、先判断订单是否已处理
        //select * from t_order where order_id = trade_no;
        log.info("tradeNo:{}", tradeNo);
        TOrder order = orderService.queryOrderByOrderId(tradeNo);
        if (order == null) {
            return "error";
        }
        if (order != null && order.getStatus() == 1) {
            return "have Completed";
        }
        //2、处理订单，原子性
        orderHandler.finshedOrder(tradeNo, order.getUserId());
        return "Completed";
    }
}
