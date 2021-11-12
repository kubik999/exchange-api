package org.demo.springbootThymeleaf.shared;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Exchange {

    private double toExchangePln;
    private double toExchangeUsd;
   // private double kurs;

    public Exchange() {
    }

    public double getToExchangePln() {
        return toExchangePln;
    }

    public void setToExchangePln(double toExchangePln) {
        this.toExchangePln = toExchangePln;
    }

    public double getToExchangeUsd() {
        return toExchangeUsd;
    }

    public void setToExchangeUsd(double toExchangeUsd) {
        this.toExchangeUsd = toExchangeUsd;
    }
}
