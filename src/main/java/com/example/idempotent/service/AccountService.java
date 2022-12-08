package com.example.idempotent.service;

import com.example.idempotent.mapper.TAccountMapper;
import com.example.idempotent.model.TAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    @Autowired
    private TAccountMapper tAccountMapper;

    /**
     * 给本地账户加钱
     * @param userId
     */
    public Integer addAccount(Long userId) {
        TAccount record = new TAccount();
        record.setAccount(new BigDecimal(100));
        record.setUserId(userId);
        int count = tAccountMapper.updateAccountByUserId(record);
        return count;
    }
}
