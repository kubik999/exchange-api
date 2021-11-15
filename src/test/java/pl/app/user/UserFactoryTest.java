package pl.app.user;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.app.entity.AppUser;
import pl.app.entity.BankAccount;
import pl.app.exchange.ExchangeCommand;
import pl.app.exchange.ExchangeNominal;

import java.time.LocalDate;

@AllArgsConstructor
public class UserFactoryTest {

    public static AppUserDto.AppUserDtoBuilder validUser() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setActualPln(200);
        bankAccount.setActualUsd(50);

        AppUserDto.AppUserDtoBuilder appUserDto = AppUserDto.builder()
                .pesel("46041882812")
                .name("testNAme")
                .lastName("testLastName")
                .birthDate(LocalDate.now().minusYears(20))
                .accountBalance(bankAccount)
                .password(new BCryptPasswordEncoder().encode("pass"));
        return appUserDto;
    }

    public static AppUser validUserDomain() {

        AppUserDto userDto = validUser().build();
        AppUser appUser = new AppUser();
        appUser.setPesel(userDto.getPesel());
        appUser.setName(userDto.getName());
        appUser.setLastName(userDto.getLastName());
        appUser.setBirthDate(userDto.getBirthDate());
        appUser.setAccountBalance(userDto.getAccountBalance());
        appUser.setPassword(userDto.getPassword());
        return appUser;
    }

    public static ExchangeCommand.ExchangeCommandBuilder validExchangeCommand() {

        return ExchangeCommand.builder()
                .value(100)
                .nominalFrom(ExchangeNominal.PLN)
                .nominalTo(ExchangeNominal.USD);
    }
}
