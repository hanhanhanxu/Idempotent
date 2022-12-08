package com.example.idempotent.handler;

import com.example.idempotent.service.AccountService;
import com.example.idempotent.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderHandler {
    @Autowired
    private OrderService orderService;
    @Autowired
    private AccountService accountService;


    /**
     * 如何保证原子性：乐观锁，事务回滚（异常会自动回滚）
     * @param orderId
     * @param userId
     */
    //rollbackFor = Exception.class
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void finshedOrder(String orderId, Long userId) {
        //本地账户加钱
        Integer count = accountService.addAccount(userId);
        if (count != 1) {
            throw new RuntimeException("本地账户加钱失败");
        }
        //修改订单状态: update t_order set status = 1 where order_id = ? and status = 0; 这个一定是只执行一次的
        count = orderService.modifyOrderState(orderId, 1);
        if (count != 1) {
            throw new RuntimeException("订单状态完成失败");
        }
    }
}
