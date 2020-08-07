package ua.com.cyberneophyte.jumpic.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.cyberneophyte.jumpic.repos.AnswerRepo;

@Service
@Transactional
public class AnswerService {
    private final AnswerRepo answerRepo;

    public AnswerService(AnswerRepo answerRepo) {
        this.answerRepo = answerRepo;
    }

    public void deleteAnswerById(Long answerId) {
        answerRepo.deleteAnswersById(answerId);
    }
}
