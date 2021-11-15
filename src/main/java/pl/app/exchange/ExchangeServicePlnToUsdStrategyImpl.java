package pl.app.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import pl.app.entity.BankAccount;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.app.system.AppProfile;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Objects;

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

        NbpUsdV1 nbpUsdV1 = exchangeUsdService.getUsdRate();
        return Objects.nonNull(nbpUsdV1)
                ? nbpUsdV1.getRates().stream()
                .findFirst()
                .map(obj -> (Double) obj.get("ask"))
                .orElseThrow(() -> new RuntimeException("Nie znaleziono ceny dolara"))
                : 4.0;
    }
}
