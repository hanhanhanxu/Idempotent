package com.example.idempotent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TAccount {
    private Long id;

    private Long userId;

    private BigDecimal account;
}