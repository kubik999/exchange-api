package pl.app.exchange;

import java.util.Map;

public class ExchangeMoneyValidator {

    public static void validate(Map<ExchangeNominal, Double> moneyResourceMap, ExchangeCommand exchangeCommand) {

        double value = moneyResourceMap.getOrDefault(exchangeCommand.getNominalFrom(), 0.0);
        if (value < exchangeCommand.getValue()) {
            throw new RuntimeException("Nie ma takiej ilości pieniędzy na koncie");
        }
    }
}
