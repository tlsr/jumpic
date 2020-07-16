package ua.com.cyberneophyte.jumpic.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.cyberneophyte.jumpic.domain.*;
import ua.com.cyberneophyte.jumpic.forms.QuizForm;
import ua.com.cyberneophyte.jumpic.repos.AnswerRepo;
import ua.com.cyberneophyte.jumpic.repos.ChapterRepo;
import ua.com.cyberneophyte.jumpic.repos.QuizRepo;

import java.util.List;

@Service
@Transactional
public class QuizService {
    private final LessonService lessonService;
    private final ChapterRepo chapterRepo;
    private final QuizRepo quizRepo;
    private final AnswerRepo answerRepo;


    public QuizService(LessonService lessonService, ChapterRepo chapterRepo, QuizRepo quizRepo, AnswerRepo answerRepo) {
        this.lessonService = lessonService;
        this.chapterRepo = chapterRepo;
        this.quizRepo = quizRepo;
        this.answerRepo = answerRepo;
    }

    public Quiz createQuizeFromQuizForm(QuizForm quizForm){
        Quiz quiz = new Quiz();
        System.out.println("in form service quizform.answers: "+quizForm.getAnswers());
        for (Answer answer: quizForm.getAnswers()) {
            answer.setQuiz(quiz);
        }
        quiz.setId(quizForm.getId());
        quiz.setTitle(quizForm.getTitle());
        quiz.setQuestion(quizForm.getQuestion());
        quiz.setAnswers(quizForm.getAnswers());
        return quiz;
    }

    public void addQuizToChapter(Quiz quiz, Chapter chapter) {
        List<Lesson> listOfLessons = chapterRepo.findChapterById(chapter.getId()).getListOfLessons();
        StructuredUtil.incrementConsecutiveNumber(quiz,listOfLessons);
        quiz.setChapter(chapter);
        listOfLessons.add(quiz);
        List<Answer> answers = quiz.getAnswers();
        answerRepo.saveAll(answers);
        quizRepo.save(quiz);
    }

    public Quiz findQuizeById(Long id) {
        return quizRepo.findQuizById(id);
    }

    public void editQuizLessonInChapter(Quiz quiz, Chapter chapter) {
        List<Lesson> listOfLessons = chapter.getListOfLessons();
        Quiz oldQuiz = (Quiz) listOfLessons.stream()
                .filter(lesson ->
                        lesson.getId().equals(quiz.getId())
                )
                .findFirst()
                .orElse(null);

        System.out.println(oldQuiz);

        oldQuiz.setAnswers(quiz.getAnswers());
        oldQuiz.setQuestion(quiz.getQuestion());
        oldQuiz.setTitle(quiz.getTitle());
        List<Answer> answers = quiz.getAnswers();
        answerRepo.deleteAnswersByQuizId(quiz.getId());
        answerRepo.saveAll(answers);
        saveQuiz(oldQuiz);
       /* List<Lesson> listOfChapters = chapter.getListOfLessons();
        Theory oldTheory = (Theory) listOfChapters.stream()
                .filter(lesson ->
                        lesson.getId().equals(theory.getId())
                )
                .findFirst()
                .orElse(null);
        oldTheory.setContent(theory.getContent());
        oldTheory.setTitle(theory.getTitle());
        saveTheory(oldTheory);*/
    }

    public void saveQuiz(Quiz quiz) {
        quizRepo.save(quiz);
    }
}
