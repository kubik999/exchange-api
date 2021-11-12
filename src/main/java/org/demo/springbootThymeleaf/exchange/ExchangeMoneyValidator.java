package org.demo.springbootThymeleaf.exchange;

import org.demo.springbootThymeleaf.entity.BankAccount;
import org.demo.springbootThymeleaf.shared.Exchange;

public class ExchangeMoneyValidator {

    public static void validate(double userBankAccount, double toExchange) {

        if (userBankAccount < toExchange) {
            throw new RuntimeException("nie ma tyle kasy");
        }
    }

    public static void validate2(double actualUsd, double toExchangeUsd) {
        if (actualUsd < toExchangeUsd) {
            throw new RuntimeException("nie ma tyle kasy");
        }
    }
}
