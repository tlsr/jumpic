package ua.com.cyberneophyte.jumpic.repos;

import org.springframework.data.repository.CrudRepository;
import ua.com.cyberneophyte.jumpic.domain.Quiz;

public interface QuizRepo extends CrudRepository<Quiz, Integer> {
}
