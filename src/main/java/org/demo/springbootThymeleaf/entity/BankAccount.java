package org.demo.springbootThymeleaf.entity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double actualPln;
    private double actualUsd;

    public BankAccount() {
    }

    public double getActualPln() {
        return actualPln;
    }

    public void setActualPln(double actualPln) {
        this.actualPln = actualPln;
    }

    public double getActualUsd() {
        return actualUsd;
    }

    public void setActualUsd(double actualUsd) {
        this.actualUsd = actualUsd;
    }
}
