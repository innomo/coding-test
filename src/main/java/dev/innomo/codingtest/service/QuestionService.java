package dev.innomo.codingtest.service;

import dev.innomo.codingtest.model.Question;
import dev.innomo.codingtest.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.PageRanges;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class QuestionService implements IQuestionService{

    private QuestionRepository questionRepository;

    @Override
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestion() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public List<String> getAllSubjects() {
        return questionRepository.findDistinctSubject();
    }

    @Override
    public Question updateQuestion(Long id, Question question) throws ChangeSetPersister.NotFoundException {
        Optional<Question> theQuestion = this.getQuestionById(id);
        if(theQuestion.isPresent()){
            Question q = theQuestion.get();
            q.setQuestion(question.getQuestion());
            q.setChoices(question.getChoices());
            q.setCorrectAnswers(question.getCorrectAnswers());
            questionRepository.save(q);
        }else {
            throw new ChangeSetPersister.NotFoundException();
        }
        return null;
    }

    @Override
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    @Override
    public List<Question> getGetQuestionsForUser(Integer numOfQuestions, String subject) {
        Pageable pageable = PageRequest.of(0,numOfQuestions);
        return questionRepository.findBySubject(subject, pageable).getContent();
    }
}
