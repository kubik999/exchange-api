package pl.app.exchange;

import pl.app.entity.BankAccount;

public interface ExchangeMoneyServiceStrategy {

     boolean accept(ExchangeCommand exchangeCommand);

    BankAccount exchange(BankAccount userBankAccount, ExchangeCommand toExchangeCommand);
}
