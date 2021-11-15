package pl.app.user;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.app.entity.AppUser;
import pl.app.security.UserAuthService;
import pl.app.system.AppProfile;

import java.util.Optional;

@Service
@Profile(AppProfile.TEST)
public class UserAuthServiceFake implements UserAuthService {
    public Optional<AppUser> getUser() {
        return Optional.of(UserFactoryTest.validUserDomain());
    };
}
