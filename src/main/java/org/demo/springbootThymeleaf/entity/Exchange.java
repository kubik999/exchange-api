package org.demo.springbootThymeleaf.entity;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Exchange {

    private String toExchangePln;
   // private double kurs;

    public Exchange() {
    }

    public String getToExchangePln() {
        return toExchangePln;
    }

    public void setToExchangePln(String toExchangePln) {
        this.toExchangePln = toExchangePln;
    }
}
