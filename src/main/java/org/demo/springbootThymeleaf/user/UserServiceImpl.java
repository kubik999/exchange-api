package org.demo.springbootThymeleaf.user;

import org.demo.springbootThymeleaf.entity.AppUser;
import org.demo.springbootThymeleaf.repository.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;


@Service
public class UserServiceImpl implements UserService {

    private static final Predicate<Authentication> isValidToken = auth -> auth != null
            && !(auth instanceof AnonymousAuthenticationToken);

    private UserRepository repo;

    public UserServiceImpl(UserRepository userRepository) {
        this.repo = userRepository;
    }

    @Override
    public Optional<AppUser> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return isValidToken.test(authentication)
                ? Optional.of((AppUser) authentication.getPrincipal())
                : Optional.empty();
    }

    @Override
    public void save(AppUser user) {
        repo.save(user);
    }

    @Override
    public List<AppUser> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<AppUser> findByName(String name) {
        return repo.findByName(name);
    }
}





