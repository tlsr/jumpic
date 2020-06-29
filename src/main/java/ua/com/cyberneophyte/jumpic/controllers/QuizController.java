package ua.com.cyberneophyte.jumpic.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.cyberneophyte.jumpic.domain.Quiz;
import ua.com.cyberneophyte.jumpic.repos.QuizRepo;

import java.util.Map;

@Controller
public class QuizController {
  /*  private final Logger logger = LoggerFactory.getLogger(QuizController.class);
    private final QuizRepo quizRepo;

    public QuizController(QuizRepo quizRepo) {
        this.quizRepo = quizRepo;
    }*/

  /*  @PostMapping(path = "/addQuiz")
    public String addNewQuiz(@RequestParam String question) {
        Quiz n = new Quiz();
        n.setQuestion(question);
        quizRepo.save(n);
        logger.info("adding new quiz to database {}", question);
        return "redirect:/editor";
    }

    @GetMapping(path = "/editor")
    public String setUpEditorPage() {
        return "editor";
    }

    @GetMapping(path = "/all")
    public String getAllQuizzes(Map<String, Object> model) {
        Iterable<Quiz> all = quizRepo.findAll();
        model.put("Quizes", all);
        logger.info("showing all quizes to database");
        return "QuizeList";
    }*/
}
