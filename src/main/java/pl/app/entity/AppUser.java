package pl.app.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Getter
@Setter
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "{error.emptyRequiredField}")
    @Size(max=20, message = "{error.limitCharsTo20}")
    private String name;

    @NotNull(message = "{error.emptyRequiredField}")
    @Size(max=20, message = "{error.limitCharsTo20}")
    private String lastName;

    @NotNull(message = "{error.emptyRequiredField}")
    private String pesel;

    @NotNull(message = "{error.emptyRequiredField}")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate birthDate;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "bank_account_id", nullable = false, referencedColumnName = "id")
    private BankAccount accountBalance;

    @NotNull(message = "{error.emptyRequiredField}")
    private String password;

    private boolean isEnabled = true;

    public AppUser() {
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}











