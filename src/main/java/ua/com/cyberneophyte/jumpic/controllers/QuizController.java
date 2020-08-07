package ua.com.cyberneophyte.jumpic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.com.cyberneophyte.jumpic.domain.Answer;
import ua.com.cyberneophyte.jumpic.domain.Chapter;
import ua.com.cyberneophyte.jumpic.domain.Lesson;
import ua.com.cyberneophyte.jumpic.domain.Quiz;
import ua.com.cyberneophyte.jumpic.forms.QuizForm;
import ua.com.cyberneophyte.jumpic.service.AnswerService;
import ua.com.cyberneophyte.jumpic.service.QuizService;
import ua.com.cyberneophyte.jumpic.validators.QuizFormValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/courseEditor/{course}/{module}/{chapter}")
public class QuizController {
    private final QuizService quizService;
    private final AnswerService answerService;
    private final QuizFormValidator quizFormValidator;


    public QuizController(QuizService quizService,AnswerService answerService, QuizFormValidator quizFormValidator) {
        this.quizService = quizService;
        this.answerService = answerService;
        this.quizFormValidator = quizFormValidator;
    }

    @GetMapping("/quizEditor")
    public String showQuizEditor(Model model,QuizForm quizForm) {
        quizForm.getAnswers().add(new Answer());
        model.addAttribute("quizForm", quizForm);
        return "quizEditor";
    }

    @RequestMapping(value ={ "/quizEditor","/{lesson}/editQuizLesson"} , params = {"addRow"})
    public String addRow(Model model, QuizForm quizForm) {
        quizForm.getAnswers().add(new Answer());
        model.addAttribute("quizForm", quizForm);
        return "quizEditor";
    }

    @RequestMapping(value ={ "/quizEditor","/{lesson}/editQuizLesson"} , params = {"removeRow"})
    public String removeRow(Model model, QuizForm quizForm,
                            @RequestParam(name = "removeRow") Long answerId) {

        quizForm.getAnswers().removeIf(answer -> answer.getId().equals(answerId));
        model.addAttribute("quizForm", quizForm);
        answerService.deleteAnswerById(answerId);
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
        Quiz quiz = quizService.createQuizFromQuizForm(quizForm);
        quizService.addQuizToChapter(quiz,chapter);
        return "redirect:/courseEditor/{course}";
    }

    @GetMapping(value = "/{lesson}/editQuiz" )
    public String editQuizInChapterShowForm(@Valid QuizForm quizForm,BindingResult bindingResult, Model model,
                                            @PathVariable Chapter chapter, Lesson lesson) {
        quizForm = quizService.createQuizFormFromLessonId(lesson.getId());
        model.addAttribute("quizForm",quizForm);
        model.addAttribute("actionLink","editQuizLesson");
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
        Quiz quiz = quizService.createQuizFromQuizForm(quizForm);
        quizService.editQuizLessonInChapter(quiz,chapter);
        return "redirect:/courseEditor/{course}";
    }

}
