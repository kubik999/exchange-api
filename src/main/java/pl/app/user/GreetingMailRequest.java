package pl.app.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class GreetingMailRequest {

    @NotBlank(message = "Adres e-mail jest wymagany")
    @Email(message = "Podaj poprawny adres e-mail")
    private String email;
}
