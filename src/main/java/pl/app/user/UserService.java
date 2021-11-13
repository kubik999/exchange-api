package pl.app.user;


import pl.app.entity.AppUser;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<AppUser> getUser();

    AppUser saveUser(AppUser user);

    Optional<AppUser> findByName(String name);

    Optional<AppUser> findByPesel(String pesel);

    List<AppUser> findAll();
}
