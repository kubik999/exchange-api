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
class ExchangeServicePlnToUsdStrategyImpl implements ExchangeServiceStrategy {

    private ExchangeUsdService exchangeUsdService;

    public boolean accept(ExchangeCommand exchangeCommand) {
        return ExchangeNominal.PLN.equals(exchangeCommand.getNominalFrom())
                && ExchangeNominal.USD.equals(exchangeCommand.getNominalTo());
    }

    @Override
    public BankAccount exchange(BankAccount userBankAccount, ExchangeCommand exchangeCommandContext) {

        double actualPln = userBankAccount.getActualPln();
        double actualUsd = userBankAccount.getActualUsd();
        double toExchangeValuePln = exchangeCommandContext.getValue();
        double usdRate = getUsdRateAsk();
        double boughtUsd = toExchangeValuePln / usdRate;
        BigDecimal boughtUsdRounded = new BigDecimal(boughtUsd).setScale(2, RoundingMode.HALF_UP);
        boughtUsd = boughtUsdRounded.doubleValue();
        userBankAccount.setActualPln(actualPln - toExchangeValuePln);
        userBankAccount.setActualUsd(actualUsd + boughtUsd);
        return userBankAccount;
    }

    private double getUsdRateAsk() {

        Optional<NbpUsdV1> nbpUsdV1Optional = exchangeUsdService.getUsdRate();
        NbpUsdV1 nbpUsdV1 = nbpUsdV1Optional.orElseThrow(() -> new RuntimeException("Brak wartości ceny usd"));
        return nbpUsdV1.getAsk();
    }
}
