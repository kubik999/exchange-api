package org.demo.springbootThymeleaf.service;

import org.demo.springbootThymeleaf.entity.User;
import org.demo.springbootThymeleaf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private UserRepository teamsRepository;

    //@Autowired
    public UserServiceImpl(UserRepository theTeamsRepository) {
        teamsRepository = theTeamsRepository;
    }

    @Override
    public void save(User theTeam) {
        teamsRepository.save(theTeam);
    }

    @Override
    public List<User> findAll() {
        return teamsRepository.findAll();
    }

    @Override
    public Optional<User> findByName(String name) {
        return teamsRepository.findByName(name);
    }
}





