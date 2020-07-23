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
        QuizForm quizForm  = (QuizForm) o;
        if(quizForm.getAnswers().isEmpty()){
            errors.rejectValue("answer","answer.list.empty");
        }
        Answer correctAnswer = quizForm.getAnswers().stream()
                .filter(answer -> answer.getIsCorrect())
                .findFirst()
                .orElse(null);
        if(correctAnswer == null){
            errors.rejectValue("answers","no.correct.answers");
        }
    }
}
