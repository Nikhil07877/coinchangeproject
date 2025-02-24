package com.sample.coinchange.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum CoinType {
    PENNY(new BigDecimal("0.01")),
    NICKEL(new BigDecimal("0.05")),
    DIME(new BigDecimal("0.10")),
    QUARTER(new BigDecimal("0.25"))
    ;

    @JsonValue
    private final BigDecimal amount;

    public static boolean isValidBill(int bill) {
        List<Integer> validBills = Arrays.asList(1, 2, 5, 10, 20, 50, 100);
        return validBills.contains(bill);
    }
}
