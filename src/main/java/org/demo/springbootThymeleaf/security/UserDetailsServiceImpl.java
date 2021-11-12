package org.demo.springbootThymeleaf.security;

import org.demo.springbootThymeleaf.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository appUserRepo;

    public UserDetailsServiceImpl(UserRepository appUserRepo) {
        this.appUserRepo = appUserRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // todo throw if not exist
        return appUserRepo.findByName(s).get();
    }
}
