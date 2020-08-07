package ua.com.cyberneophyte.jumpic.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.cyberneophyte.jumpic.domain.Answer;
import ua.com.cyberneophyte.jumpic.domain.Chapter;
import ua.com.cyberneophyte.jumpic.domain.Lesson;
import ua.com.cyberneophyte.jumpic.domain.Quiz;
import ua.com.cyberneophyte.jumpic.forms.QuizForm;
import ua.com.cyberneophyte.jumpic.repos.AnswerRepo;
import ua.com.cyberneophyte.jumpic.repos.ChapterRepo;
import ua.com.cyberneophyte.jumpic.repos.QuizRepo;

import java.util.List;

@Service
@Transactional
public class QuizService {
    private final ChapterRepo chapterRepo;
    private final QuizRepo quizRepo;
    private final AnswerRepo answerRepo;


    public QuizService(ChapterRepo chapterRepo, QuizRepo quizRepo, AnswerRepo answerRepo) {
        this.chapterRepo = chapterRepo;
        this.quizRepo = quizRepo;
        this.answerRepo = answerRepo;
    }

    public Quiz createQuizFromQuizForm(QuizForm quizForm) {
        Quiz quiz = new Quiz();
        for (Answer answer : quizForm.getAnswers()) {
            answer.setQuiz(quiz);
        }
        quiz.setId(quizForm.getId());
        quiz.setTitle(quizForm.getTitle());
        quiz.setQuestion(quizForm.getQuestion());
        quiz.setAnswers(quizForm.getAnswers());
        quiz.setPoints(quizForm.getPoints());
        return quiz;
    }

    public QuizForm createQuizFormFromLessonId(Long lessonId) {
        QuizForm quizForm = new QuizForm();
        Quiz quiz = findQuizById(lessonId);
        quizForm.setId(quiz.getId());
        quizForm.setAnswers(quiz.getAnswers());
        quizForm.setQuestion(quiz.getQuestion());
        quizForm.setTitle(quiz.getTitle());
        quizForm.setPoints(quiz.getPoints());
        return quizForm;
    }

    public void addQuizToChapter(Quiz quiz, Chapter chapter) {
        List<Lesson> listOfLessons = chapterRepo.findChapterById(chapter.getId()).getListOfLessons();
        StructuredUtil.incrementConsecutiveNumber(quiz, listOfLessons);
        quiz.setChapter(chapter);
        listOfLessons.add(quiz);
        List<Answer> answers = quiz.getAnswers();
        answerRepo.saveAll(answers);
        quizRepo.save(quiz);
    }

    public Quiz findQuizById(Long id) {
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
        List<Answer> oldQuizAnswers = oldQuiz.getAnswers();
        oldQuizAnswers.clear();
        oldQuizAnswers.addAll(quiz.getAnswers());
        oldQuiz.setQuestion(quiz.getQuestion());
        oldQuiz.setTitle(quiz.getTitle());
        List<Answer> answers = quiz.getAnswers();
        answerRepo.saveAll(answers);
        saveQuiz(oldQuiz);
    }

    public void saveQuiz(Quiz quiz) {
        quizRepo.save(quiz);
    }
}
