package pl.app.user;

import lombok.AllArgsConstructor;
import pl.app.entity.AppUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Predicate<Authentication> isAuthenticated = auth -> auth != null
            && !(auth instanceof AnonymousAuthenticationToken);
    private UserRepository repo;

    @Override
    public Optional<AppUser> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return isAuthenticated.test(authentication)
                ? Optional.of((AppUser) authentication.getPrincipal())
                : Optional.empty();
    }

    @Override
    public AppUser saveUser(AppUser user) {
        return repo.save(user);
    }

    @Override
    public List<AppUser> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<AppUser> findByName(String name) {
        return repo.findByName(name);
    }

    @Override
    public Optional<AppUser> findByPesel(String pesel) {
        return repo.findByPesel(pesel);
    }
}





