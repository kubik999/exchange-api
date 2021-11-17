package pl.app.security;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import pl.app.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository appUserRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return appUserRepo.findByPesel(s)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono użytkownika"));
    }
}
