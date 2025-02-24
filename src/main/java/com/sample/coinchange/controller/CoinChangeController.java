package com.sample.coinchange.controller;

import com.sample.coinchange.dto.CoinType;
import com.sample.coinchange.service.CoinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@Slf4j
public class CoinChangeController {

    private final CoinService coinService;

    public CoinChangeController(CoinService coinService) {
        this.coinService = coinService;
    }

    @GetMapping("/api/change/{bill}")
    public ResponseEntity<?> getChange(@PathVariable Integer bill) {
        if (!CoinType.isValidBill(bill)) {
            return ResponseEntity.badRequest().body("Invalid bill. Accepted bills are: 1, 2, 5, 10, 20, 50, 100.");
        }

        BigDecimal changeRequired = BigDecimal.valueOf(bill - 1);

        if (changeRequired.compareTo(BigDecimal.ZERO) == 0) {
            return ResponseEntity.ok("No change required.");
        }

        if (!coinService.canProvideChange(changeRequired)) {
            return ResponseEntity.status(400).body("Insufficient coins to provide change.");
        }

        Map<CoinType, Integer> coinsUsed = coinService.getCoinsForChange(changeRequired);
        if (coinsUsed == null) {
            return ResponseEntity.status(400).body("Unable to provide exact change.");
        }
        coinService.updateCoins(coinsUsed);
        return ResponseEntity.ok(coinsUsed);
    }
}
