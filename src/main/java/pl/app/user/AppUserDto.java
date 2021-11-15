package pl.app.user;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import pl.app.entity.BankAccount;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class AppUserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "{error.emptyRequiredField}")
    private String pesel;

    @NotNull(message = "{error.emptyRequiredField}")
    @Size(max = 20, message = "{error.limitCharsTo20}")
    private String name;

    @NotNull(message = "{error.emptyRequiredField}")
    @Size(max = 20, message = "{error.limitCharsTo20}")
    private String lastName;

    @NotNull(message = "{error.emptyRequiredField}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "bank_account_id", nullable = false, referencedColumnName = "id")
    private BankAccount accountBalance;

    @NotNull(message = "{error.emptyRequiredField}")
    private String password;
}
