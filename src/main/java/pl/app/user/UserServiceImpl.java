package pl.app.user;

import lombok.AllArgsConstructor;
import pl.app.entity.AppUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
class UserServiceImpl implements UserService {

    private UserRepository repo;

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

    public void deleteAll() {
        repo.deleteAll();
    }
}





