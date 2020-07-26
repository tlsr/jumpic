package ua.com.cyberneophyte.jumpic.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ua.com.cyberneophyte.jumpic.domain.Quiz;

import java.util.List;

//
public interface QuizRepo extends JpaRepository<Quiz, Long> {
    Quiz findQuizById(Long id);
}
