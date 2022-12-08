package com.example.idempotent.service;

import com.example.idempotent.mapper.TOrderMapper;
import com.example.idempotent.model.TOrder;
import com.example.idempotent.model.TOrderExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private TOrderMapper tOrderMapper;

    public TOrder queryOrderByOrderId(String orderId) {
        log.info("orderId:{}", orderId);
        TOrder tOrder = tOrderMapper.selectByOrderId(orderId);
        return tOrder;
    }

    public Integer modifyOrderState(String orderId, Integer orderState) {
        TOrder record = new TOrder();
        record.setStatus(orderState);
        TOrderExample example = new TOrderExample();
        TOrderExample.Criteria criteria = example.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        criteria.andStatusEqualTo(0);
        int count = tOrderMapper.updateByExampleSelective(record, example);
        return count;
    }


}
