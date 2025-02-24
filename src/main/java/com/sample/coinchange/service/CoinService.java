package com.sample.coinchange.service;

import com.sample.coinchange.dto.CoinType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

@Service
public class CoinService {
    private final Map<CoinType, Integer> coinInventory;

    public CoinService() {
        coinInventory = new HashMap<>();
        for (CoinType coinType : CoinType.values()) {
            coinInventory.put(coinType, 100);
        }
    }

    public boolean canProvideChange(BigDecimal changeRequired) {
        Map<CoinType, Integer> coinsNeeded = getCoinsForChange(changeRequired);
        if (coinsNeeded == null) {
            return false;
        }
        for (Map.Entry<CoinType, Integer> entry : coinsNeeded.entrySet()) {
            CoinType coinType = entry.getKey();
            int neededCount = entry.getValue();
            int availableCount = coinInventory.get(coinType);

            if (availableCount < neededCount) {
                return false;
            }
        }
        return true;
    }

    public Map<CoinType, Integer> getCoinsForChange(BigDecimal changeRequired) {
        Map<CoinType, Integer> coinsNeeded = new HashMap<>();

        List<CoinType> sortedCoins = new ArrayList<>(List.of(CoinType.values()));
        sortedCoins.sort((c1, c2) -> c2.getAmount().compareTo(c1.getAmount()));

        for (CoinType coinType : sortedCoins) {
            BigDecimal coinValue = coinType.getAmount();
            int coinCount = changeRequired.divide(coinValue, 0, BigDecimal.ROUND_DOWN).intValue();

            if (coinCount > 0) {
                int availableCount = coinInventory.get(coinType);
                if (coinCount > availableCount) {
                    coinCount = availableCount;
                }
                coinsNeeded.put(coinType, coinCount);
                changeRequired = changeRequired.subtract(coinValue.multiply(BigDecimal.valueOf(coinCount)));
            }

            if (changeRequired.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }
        }
        return changeRequired.compareTo(BigDecimal.ZERO) <= 0 ? coinsNeeded : null;
    }

    public void updateCoins(Map<CoinType, Integer> coinsUsed) {
        coinsUsed.forEach((coinType, count) -> coinInventory.put(coinType,
                coinInventory.get(coinType) - count));
    }
}