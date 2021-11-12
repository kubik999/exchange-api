package org.demo.springbootThymeleaf.user;



import org.demo.springbootThymeleaf.entity.AppUser;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<AppUser> getUser();

    void save(AppUser theTeam);

    Optional<AppUser> findByName(String name);

    List<AppUser> findAll();
}
