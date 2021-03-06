package ua.com.cyberneophyte.jumpic.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.cyberneophyte.jumpic.domain.User;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}
