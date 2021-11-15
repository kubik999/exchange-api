package pl.app.exchange;

import pl.app.entity.BankAccount;

public interface ExchangeServiceStrategy {

     boolean accept(ExchangeCommand exchangeCommand);

    BankAccount exchange(BankAccount userBankAccount, ExchangeCommand toExchangeCommand);
}
