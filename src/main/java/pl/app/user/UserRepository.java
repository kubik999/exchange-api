package pl.app.user;

import pl.app.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer> {

    Optional<AppUser> findByName(String username);

    Optional<AppUser> findByPesel(String pesel);
}
