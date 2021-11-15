package pl.app.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import pl.app.entity.AppUser;
import pl.app.exchange.ExchangeCommand;
import pl.app.exchange.ExchangeNominal;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AppUserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceFacade userServiceFacade;

    @Before
    public void setUp() {
        userService.deleteAll();
    }

    @Test
    public void shouldSaveUser() {

        AppUserDto appUserDto = UserFactoryTest.validUser().build();

        ExtendedModelMap model = new ExtendedModelMap();
        BindingResult result = new BeanPropertyBindingResult(appUserDto, "form");

        userServiceFacade.saveUser(appUserDto, model, result);
        List<AppUser> allUsers = userService.findAll();
        assertThat(userService.findAll().size()).isEqualTo(1);
        assertThat(allUsers.get(0).getPesel()).isEqualTo("46041882812");
        assertThat(allUsers.get(0).getName()).isEqualTo("testNAme");
        assertThat(allUsers.get(0).getLastName()).isEqualTo("testLastName");
        assertThat(allUsers.get(0).getBirthDate()).isEqualTo(LocalDate.now().minusYears(20));
        assertThat(allUsers.get(0).getAccountBalance().getActualPln()).isEqualTo(200);
        assertThat(allUsers.get(0).getAccountBalance().getActualUsd()).isEqualTo(50);
        assertThat(allUsers.get(0).getPassword()).isNotNull();
    }

    @Test
    public void shouldNotSaveUserWhenInvalidPesel() {

        AppUserDto appUserDto = UserFactoryTest.validUser()
                .pesel("11223344")
                .build();
        ExtendedModelMap model = new ExtendedModelMap();
        BindingResult result = new BeanPropertyBindingResult(appUserDto, "form");

        assertThat(userServiceFacade.saveUser(appUserDto, model, result)).isEmpty();
        assertThat(userService.findAll()).isEmpty();
        assertThat(model.get("invalidPesel")).isNotNull();
    }

    @Test
    public void shouldNotSaveUserWhenUserIsTooYoung() {

        AppUserDto appUserDto = UserFactoryTest.validUser()
                .birthDate(LocalDate.now().minusYears(12))
                .build();
        ExtendedModelMap model = new ExtendedModelMap();
        BindingResult result = new BeanPropertyBindingResult(appUserDto, "form");

        assertThat(userServiceFacade.saveUser(appUserDto, model, result)).isEmpty();
        assertThat(userService.findAll()).isEmpty();
        assertThat(model.get("underagePerson")).isNotNull();
    }

    @Test
    public void shouldNotSaveUserWhenAlreadyExistUserWithEqualPesel() {

        AppUserDto appUserDto1 = UserFactoryTest.validUser()
                .build();
        ExtendedModelMap model = new ExtendedModelMap();
        BindingResult result = new BeanPropertyBindingResult(appUserDto1, "form");

        userServiceFacade.saveUser(appUserDto1, model, result);
        List<AppUser> allUsers = userService.findAll();
        assertThat(allUsers.size()).isEqualTo(1);
        assertThat(allUsers.get(0).getPesel()).isEqualTo("46041882812");
        assertThat(allUsers.get(0).getName()).isEqualTo("testNAme");
        assertThat(allUsers.get(0).getLastName()).isEqualTo("testLastName");
        assertThat(allUsers.get(0).getBirthDate()).isEqualTo(LocalDate.now().minusYears(20));
        assertThat(allUsers.get(0).getAccountBalance().getActualPln()).isEqualTo(200);
        assertThat(allUsers.get(0).getAccountBalance().getActualUsd()).isEqualTo(50);
        assertThat(allUsers.get(0).getPassword()).isNotNull();


        AppUserDto appUserDto2 = UserFactoryTest.validUser()
                .build();
        userServiceFacade.saveUser(appUserDto2, model, result);

        userServiceFacade.saveUser(appUserDto1, model, result);
        allUsers = userService.findAll();
        assertThat(allUsers.size()).isEqualTo(1);
        assertThat(allUsers.get(0).getPesel()).isEqualTo("46041882812");
        assertThat(model.get("userAlreadyExist")).isNotNull();
    }

    @Test
    public void shouldExchangeMoneyForUserFromPlnToUsd() {

        AppUserDto appUserDto = UserFactoryTest.validUser().build();

        ExtendedModelMap modelUser = new ExtendedModelMap();
        BindingResult resultUser = new BeanPropertyBindingResult(appUserDto, "form");

        userServiceFacade.saveUser(appUserDto, modelUser, resultUser);

        ExchangeCommand exchangeCommand = UserFactoryTest.validExchangeCommand().build();
        ExtendedModelMap model = new ExtendedModelMap();
        BindingResult result = new BeanPropertyBindingResult(exchangeCommand, "form");

        userServiceFacade.exchangeLoggedUserMoney(exchangeCommand, result, model);
        Optional<AppUser> user = userService.findByPesel(appUserDto.getPesel());
        assertThat(user.get().getAccountBalance().getActualPln()).isEqualTo(100);
        assertThat(user.get().getAccountBalance().getActualUsd()).isEqualTo(75);
    }

    @Test
    public void shouldExchangeMoneyForUserFromUsdToPln() {

        AppUserDto appUserDto = UserFactoryTest.validUser().build();

        ExtendedModelMap modelUser = new ExtendedModelMap();
        BindingResult resultUser = new BeanPropertyBindingResult(appUserDto, "form");

        userServiceFacade.saveUser(appUserDto, modelUser, resultUser);

        ExchangeCommand exchangeCommand = ExchangeCommand.builder()
                .value(10)
                .nominalFrom(ExchangeNominal.USD)
                .nominalTo(ExchangeNominal.PLN)
                .build();
        ExtendedModelMap model = new ExtendedModelMap();
        BindingResult result = new BeanPropertyBindingResult(exchangeCommand, "form");

        userServiceFacade.exchangeLoggedUserMoney(exchangeCommand, result, model);
        Optional<AppUser> user = userService.findByPesel(appUserDto.getPesel());
        assertThat(user.get().getAccountBalance().getActualPln()).isEqualTo(240);
        assertThat(user.get().getAccountBalance().getActualUsd()).isEqualTo(40);
    }
}
