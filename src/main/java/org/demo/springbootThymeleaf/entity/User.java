package org.demo.springbootThymeleaf.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "{error.emptyRequiredField}")
    @Size(max=20, message = "{error.limitCharsTo20}")
    private String name;

    @NotNull(message = "{error.emptyRequiredField}")
    @Size(max=20, message = "{error.limitCharsTo20}")
    private String lastName;

    //@Column(unique=true) //<- if data should be stored in DB permanently and field should identify the user. In form not necessary
    //@Email(message="{error.emailNotValid}")
    @NotNull(message = "{error.emptyRequiredField}")
    private String pesel;

    //@Column(unique=true)) // <- if data should be stored in DB permanently and field should identify the user. In form not necessary
    //@Pattern(regexp="(^$|[0-9]{6,12})", message="{error.phoneNumberNotValid}")
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "bank_account_id", nullable = false, referencedColumnName = "id")
    private BankAccount stanKonta;

    private boolean isEnabled = true;

    private String password;

    public User() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode("pass");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
//
    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public BankAccount getStanKonta() {
        return stanKonta;
    }

    public void setStanKonta(BankAccount stanKonta) {
        this.stanKonta = stanKonta;
    }



    @Override
    public String toString() {
        return "Team [" +
                "id= " + id +
                ", name= " + name +
                ", surname= " + lastName +
                ", email= " + pesel +
                ", phone= " + stanKonta +
                "]";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}











