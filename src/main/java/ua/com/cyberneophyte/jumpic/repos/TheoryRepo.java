package ua.com.cyberneophyte.jumpic.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.cyberneophyte.jumpic.domain.Theory;

public interface TheoryRepo  extends JpaRepository<Theory,Long> {
    Theory findTheoryById(Long id);

}
