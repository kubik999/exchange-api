package pl.app.security;

import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.app.entity.AppUser;
import pl.app.system.AppProfile;

import java.util.Optional;
import java.util.function.Predicate;

@Service
@Profile(AppProfile.DEV)
public class UserAuthServiceImpl implements UserAuthService {

    private static final Predicate<Authentication> isAuthenticated = auth -> auth != null
            && !(auth instanceof AnonymousAuthenticationToken);

    @Override
    public Optional<AppUser> getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return isAuthenticated.test(authentication)
                ? Optional.of((AppUser) authentication.getPrincipal())
                : Optional.empty();
    }
}
