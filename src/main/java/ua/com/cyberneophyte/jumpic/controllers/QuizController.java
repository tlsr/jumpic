package ua.com.cyberneophyte.jumpic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.com.cyberneophyte.jumpic.domain.*;
import ua.com.cyberneophyte.jumpic.forms.QuizForm;
import ua.com.cyberneophyte.jumpic.repos.AnswerRepo;
import ua.com.cyberneophyte.jumpic.repos.LessonRepo;
import ua.com.cyberneophyte.jumpic.service.AnswerService;
import ua.com.cyberneophyte.jumpic.service.LessonService;
import ua.com.cyberneophyte.jumpic.service.QuizService;

import javax.validation.Valid;

@Controller
@RequestMapping("/courseEditor/{course}/{module}/{chapter}")
public class QuizController {
    private final LessonRepo lessonRepo;
    private final QuizService quizService;
    private final LessonService lessonService;
    private final AnswerRepo answerRepo;
    private final AnswerService answerService;


    public QuizController(LessonRepo lessonRepo, QuizService quizService, LessonService lessonService, AnswerRepo answerRepo, AnswerService answerService) {
        this.lessonRepo = lessonRepo;
        this.quizService = quizService;
        this.lessonService = lessonService;
        this.answerRepo = answerRepo;
        this.answerService = answerService;
    }

    @GetMapping("/quizEditor")
    public String showQuizEditor(Model model, Chapter chapter, QuizForm quizForm) {
        quizForm.getAnswers().add(new Answer());
        model.addAttribute("quizForm", quizForm);
        return "/quizEditor";
    }

    @RequestMapping(value ={ "/quizEditor","/edditQuiz/editQuizLesson"} , params = {"addRow"})
    public String addRow(Model model, QuizForm quizForm, BindingResult bindingResult) {
        quizForm.getAnswers().add(new Answer());
        model.addAttribute("quizForm", quizForm);
        return "/quizEditor";
    }

    @RequestMapping(value ={ "/quizEditor","/edditQuiz/editQuizLesson"} , params = {"removeRow"})
    public String removeRow(Model model, QuizForm quizForm, BindingResult bindingResult,
                            @RequestParam(name = "removeRow") Long answerId) {

        quizForm.getAnswers().removeIf(answer -> answer.getId().equals(answerId));
        model.addAttribute("quizForm", quizForm);
        answerService.deleteAnswerById(answerId);
//        answerRepo.deleteAnswersById(answerId);
/*        quizForm.getAnswers().add(new Answer());*/

        return "/quizEditor";
    }

    @RequestMapping(value = "/quizEditor", params = {"saveQuiz"})
    public String saveQuiz(Model model, QuizForm quizForm,
                           @PathVariable Chapter chapter) {
        Quiz quiz = quizService.createQuizeFromQuizForm(quizForm);
        quizService.addQuizToChapter(quiz,chapter);
       // lessonService.addLessonToChapter(quiz,chapter);
        return "redirect:/courseEditor/{course}";
    }

    @GetMapping(value = "/edditQuiz/{lesson}" )
    public String editQuizInChapterShowForm(QuizForm quizForm, Model model,
                                            @PathVariable Chapter chapter, Lesson lesson) {
        Quiz quiz = quizService.findQuizeById(lesson.getId());
        quizForm.setId(quiz.getId());
        quizForm.setAnswers(quiz.getAnswers());
        quizForm.setQuestion(quiz.getQuestion());
        quizForm.setTitle(quiz.getTitle());
        model.addAttribute("acctionLink","editQuizLesson");
        return "/quizEditor";
    }

    @PostMapping("/edditQuiz/editQuizLesson")
    public String editQuizLessonInChapter(Model model,
                                            @PathVariable Chapter chapter,
                                            Quiz quiz,
                                            @Valid QuizForm quizForm,
                                            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "/quizEditor";
        }
        quiz = quizService.createQuizeFromQuizForm(quizForm);
        quizService.editQuizLessonInChapter(quiz,chapter);
        /*theory = theoryService.createTheoryFromTheoryForm(theoryForm);
        theoryService.editTheoryLessonInChapter(theory, chapter);*/
        return "redirect:/courseEditor/{course}";
    }

}
