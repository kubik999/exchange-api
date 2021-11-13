package pl.app.entity;

import lombok.Getter;
import lombok.Setter;
import pl.app.exchange.ExchangeNominal;

import javax.persistence.*;
import java.util.Map;

@Entity
@Getter
@Setter
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
}
