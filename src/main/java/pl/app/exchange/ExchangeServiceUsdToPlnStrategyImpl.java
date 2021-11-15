package pl.app.exchange;

import lombok.AllArgsConstructor;
import pl.app.entity.BankAccount;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
class ExchangeServiceUsdToPlnStrategyImpl implements ExchangeServiceStrategy {

    private ExchangeUsdService exchangeUsdService;

    public boolean accept(ExchangeCommand exchangeCommand) {
        return ExchangeNominal.USD.equals(exchangeCommand.getNominalFrom())
                && ExchangeNominal.PLN.equals(exchangeCommand.getNominalTo());
    }

    @Override
    public BankAccount exchange(BankAccount userBankAccount, ExchangeCommand exchangeCommandContext) {
        double actualPln = userBankAccount.getActualPln();
        double actualUsd = userBankAccount.getActualUsd();
        double toExchangeValueUsd = exchangeCommandContext.getValue();
        double usdRate = getUsdRateBid();
        double newPln = toExchangeValueUsd * usdRate;
        BigDecimal boughtUsdRounded = new BigDecimal(newPln).setScale(2, RoundingMode.HALF_UP);
        newPln = boughtUsdRounded.doubleValue();
        userBankAccount.setActualPln(actualPln + newPln);
        userBankAccount.setActualUsd(actualUsd - toExchangeValueUsd);
        return userBankAccount;
    }

    private double getUsdRateBid() {
        Optional<NbpUsdV1> nbpUsdV1Optional = exchangeUsdService.getUsdRate();
        NbpUsdV1 nbpUsdV1 = nbpUsdV1Optional.orElseThrow(() -> new RuntimeException("Brak wartości ceny usdy"));
        return nbpUsdV1.getBid();
    }
}
