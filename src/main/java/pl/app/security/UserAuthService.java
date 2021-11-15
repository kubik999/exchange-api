package pl.app.security;

import pl.app.entity.AppUser;

import java.util.Optional;

public interface UserAuthService {
    Optional<AppUser> getUser();
}
