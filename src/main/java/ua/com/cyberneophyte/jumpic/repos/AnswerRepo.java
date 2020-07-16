package ua.com.cyberneophyte.jumpic.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.cyberneophyte.jumpic.domain.Answer;

public interface AnswerRepo extends JpaRepository<Answer,Long> {
    void deleteAnswersByQuizId(Long quizId);
    void deleteAnswersById(Long answerId);
}
