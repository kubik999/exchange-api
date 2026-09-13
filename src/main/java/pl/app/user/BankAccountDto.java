package pl.app.user;

import lombok.Getter;
import lombok.Setter;
import pl.app.exchange.ExchangeNominal;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Getter
@Setter
public class BankAccountDto {

    private double value;
    private ExchangeNominal nominal = ExchangeNominal.PLN;

    public BankAccountDto() {
    }

}
