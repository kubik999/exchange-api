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
class ExchangeMoneyServiceUsdToPlnStrategyImpl implements ExchangeMoneyServiceStrategy {

    private static String URL = "http://api.nbp.pl/api/exchangerates/rates/c/usd";
    private RestTemplate restTemplate;

    public boolean accept(ExchangeCommand exchangeCommand) {
        return ExchangeNominal.USD.equals(exchangeCommand.getNominalFrom())
                && ExchangeNominal.PLN.equals(exchangeCommand.getNominalTo());
    }

    @Override
    public BankAccount exchange(BankAccount userBankAccount, ExchangeCommand exchangeCommandContext) {
        double actualPln = userBankAccount.getActualPln();
        double actualUsd = userBankAccount.getActualUsd();
        double toExchangeValueUsd = exchangeCommandContext.getValue();
        Double usdRate = getUsdRate();
        double newPln = toExchangeValueUsd * usdRate;
        BigDecimal boughtUsdRounded = new BigDecimal(newPln).setScale(2, RoundingMode.HALF_UP);
        newPln = boughtUsdRounded.doubleValue();
        userBankAccount.setActualPln(actualPln + newPln);
        userBankAccount.setActualUsd(actualUsd - toExchangeValueUsd);
        return userBankAccount;
    }

    private double getUsdRate() {
        LinkedHashMap forObject = restTemplate.getForObject(URL, LinkedHashMap.class);
        ObjectMapper mapper = new ObjectMapper();
        NbpUsdV1 nbpUsdV1 = mapper.convertValue(forObject, NbpUsdV1.class);
        return nbpUsdV1.getRates().stream()
                .findFirst()
                .map(x -> (Double) x.get("bid"))
                .orElseThrow(() -> new RuntimeException("nie znaleziono ceny dolara"));
    }
}
