package com.example.idempotent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TOrder {
    private Long id;

    private Long userId;

    private String orderId;

    // 0 未处理 1 已处理
    private Integer status;
}