package pl.app.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import pl.app.entity.BankAccount;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;

@Service
@Transactional
@AllArgsConstructor
class ExchangeMoneyServicePlnToUsdStrategyImpl implements ExchangeMoneyServiceStrategy {

    private static String URL = "http://api.nbp.pl/api/exchangerates/rates/c/usd";

    private RestTemplate restTemplate;

    public boolean accept(ExchangeCommand exchangeCommand) {
        return ExchangeNominal.PLN.equals(exchangeCommand.getNominalFrom())
                && ExchangeNominal.USD.equals(exchangeCommand.getNominalTo());
    }

    @Override
    public BankAccount exchange(BankAccount userBankAccount, ExchangeCommand exchangeCommandContext) {

        double actualPln = userBankAccount.getActualPln();
        double actualUsd = userBankAccount.getActualUsd();
        double toExchangeValuePln = exchangeCommandContext.getValue();
        Double usdRate = getUsdRate();
        double boughtUsd = toExchangeValuePln / usdRate;
        BigDecimal boughtUsdRounded = new BigDecimal(boughtUsd).setScale(2, RoundingMode.HALF_UP);
        boughtUsd = boughtUsdRounded.doubleValue();
        userBankAccount.setActualPln(actualPln - toExchangeValuePln);
        userBankAccount.setActualUsd(actualUsd + boughtUsd);
        return userBankAccount;
    }

    private Double getUsdRate() {

        LinkedHashMap forObject = restTemplate.getForObject(URL, LinkedHashMap.class);
        ObjectMapper mapper = new ObjectMapper();
        NbpUsdV1 nbpUsdV1 = mapper.convertValue(forObject, NbpUsdV1.class);
        return nbpUsdV1.getRates().stream()
                .findFirst()
                .map(x -> (Double) x.get("ask"))
                .orElseThrow(() -> new RuntimeException("nie znaleziono ceny dolara"));
    }
}
