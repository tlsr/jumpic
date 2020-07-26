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
import ua.com.cyberneophyte.jumpic.validators.QuizFormValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/courseEditor/{course}/{module}/{chapter}")
public class QuizController {
    private final LessonRepo lessonRepo;
    private final QuizService quizService;
    private final LessonService lessonService;
    private final AnswerRepo answerRepo;
    private final AnswerService answerService;
    private final QuizFormValidator quizFormValidator;


    public QuizController(LessonRepo lessonRepo, QuizService quizService, LessonService lessonService, AnswerRepo answerRepo, AnswerService answerService, QuizFormValidator quizFormValidator) {
        this.lessonRepo = lessonRepo;
        this.quizService = quizService;
        this.lessonService = lessonService;
        this.answerRepo = answerRepo;
        this.answerService = answerService;
        this.quizFormValidator = quizFormValidator;
    }

    @GetMapping("/quizEditor")
    public String showQuizEditor(Model model, Chapter chapter, QuizForm quizForm) {
        quizForm.getAnswers().add(new Answer());
        model.addAttribute("quizForm", quizForm);
        return "quizEditor";
    }

    @RequestMapping(value ={ "/quizEditor","/{lesson}/editQuizLesson"} , params = {"addRow"})
    public String addRow(Model model, QuizForm quizForm, BindingResult bindingResult) {
        quizForm.getAnswers().add(new Answer());
        model.addAttribute("quizForm", quizForm);
        return "quizEditor";
    }

    @RequestMapping(value ={ "/quizEditor","/{lesson}/editQuizLesson"} , params = {"removeRow"})
    public String removeRow(Model model, QuizForm quizForm, BindingResult bindingResult,
                            @RequestParam(name = "removeRow") Long answerId) {

        quizForm.getAnswers().removeIf(answer -> answer.getId().equals(answerId));
        model.addAttribute("quizForm", quizForm);
        answerService.deleteAnswerById(answerId);
//        answerRepo.deleteAnswersById(answerId);
/*        quizForm.getAnswers().add(new Answer());*/

        return "quizEditor";
    }

    @RequestMapping(value = "/quizEditor", params = {"saveQuiz"})
    public String saveQuiz(Model model,@Valid QuizForm quizForm,
                           BindingResult bindingResult,
                           @PathVariable Chapter chapter) {
        quizFormValidator.validate(quizForm,bindingResult);
        if(bindingResult.hasErrors()){
            return "quizEditor";
        }
        Quiz quiz = quizService.createQuizeFromQuizForm(quizForm);
        quizService.addQuizToChapter(quiz,chapter);
        return "redirect:/courseEditor/{course}";
    }

    @GetMapping(value = "/{lesson}/edditQuiz" )
    public String editQuizInChapterShowForm(@Valid QuizForm quizForm,BindingResult bindingResult, Model model,
                                            @PathVariable Chapter chapter, Lesson lesson) {
        Quiz quiz = quizService.findQuizeById(lesson.getId());
        quizForm.setId(quiz.getId());
        quizForm.setAnswers(quiz.getAnswers());
        quizForm.setQuestion(quiz.getQuestion());
        quizForm.setTitle(quiz.getTitle());
        quizForm.setPoints(quiz.getPoints());
        model.addAttribute("quizForm",quizForm);
        model.addAttribute("acctionLink","editQuizLesson");
        return "quizEditor";
    }

    @PostMapping(value = "/{lesson}/editQuizLesson", params = {"saveQuiz"})
    public String editQuizLessonInChapter( @Valid QuizForm quizForm,
                                          BindingResult bindingResult,
                                            @PathVariable Chapter chapter,
                                            Model model) {
        quizFormValidator.validate(quizForm,bindingResult);
        if (bindingResult.hasErrors()) {
            return "quizEditor";
        }
        Quiz quiz = quizService.createQuizeFromQuizForm(quizForm);
        quizService.editQuizLessonInChapter(quiz,chapter);
        return "redirect:/courseEditor/{course}";
    }

}
