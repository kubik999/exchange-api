package org.demo.springbootThymeleaf.service;


import org.demo.springbootThymeleaf.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void save(User theTeam);
    Optional<User> findByName(String name);

    List<User> findAll();
}
