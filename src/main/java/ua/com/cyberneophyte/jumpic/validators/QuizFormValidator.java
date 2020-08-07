package ua.com.cyberneophyte.jumpic.validators;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.com.cyberneophyte.jumpic.domain.Answer;
import ua.com.cyberneophyte.jumpic.forms.QuizForm;

import java.util.List;

@Service
public class QuizFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return QuizForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        QuizForm quizForm = (QuizForm) o;
        List<Answer> answers = quizForm.getAnswers();
        if (answers.isEmpty()) {
            errors.rejectValue("answers", "answer.list.empty");
        }
        Answer correctAnswer = answers.stream()
                .filter(answer -> answer.getIsCorrect())
                .findFirst()
                .orElse(null);
        if (correctAnswer == null) {
            errors.rejectValue("answers", "no.correct.answers");
        }
        if (answers.size() > 10) {
            errors.rejectValue("answers", "quiz.too.much.answers");
        }
        for (int i = 0; i < answers.size(); i++) {
            Answer answer = answers.get(i);
            if (answer.getAnswerText().length() < 1 || answer.getAnswerText().length() > 64) {
                errors.rejectValue("answers[" + i + "].answerText", "quiz.answer.text.out.of.range");
            }
        }
    }
}
