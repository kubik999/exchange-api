package pl.app.entity;

import lombok.Getter;
import pl.app.exchange.ExchangeNominal;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Entity
@Getter
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double actualPln;
    private double actualUsd;

    public BankAccount() {
    }

    public Map<ExchangeNominal, Double> getMoneyResourceMap() {
        return Map.of(ExchangeNominal.PLN, actualPln,
                ExchangeNominal.USD, actualUsd
        );
    }

    public void setActualPln(double actualPln) {
        this.actualPln = new BigDecimal(actualPln).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public void setActualUsd(double actualUsd) {
        this.actualUsd = new BigDecimal(actualUsd).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
