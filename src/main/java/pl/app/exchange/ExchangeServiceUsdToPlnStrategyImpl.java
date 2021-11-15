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
        NbpUsdV1 nbpUsdV1 = exchangeUsdService.getUsdRate();
        return Objects.nonNull(nbpUsdV1)
                ? nbpUsdV1.getRates().stream()
                .findFirst()
                .map(obj -> (Double) obj.get("bid"))
                .orElseThrow(() -> new RuntimeException("Nie znaleziono ceny dolara"))
                : 4.0;
    }
}
