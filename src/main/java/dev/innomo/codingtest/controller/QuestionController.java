package dev.innomo.codingtest.controller;

import dev.innomo.codingtest.model.Question;
import dev.innomo.codingtest.service.IQuestionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/questions")
public class QuestionController {
    private IQuestionService questionService;

    @PostMapping
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody Question question) {
        Question createdQuestion = questionService.createQuestion(question);
        //return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
        return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions(){
        List<Question> allQuestion = questionService.getAllQuestion();
        //return ResponseEntity.ok(allQuestion);
        return new ResponseEntity<>(allQuestion,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        Optional<Question> questionById = questionService.getQuestionById(id);
        if(questionById.isPresent()){
//            return new ResponseEntity<>(questionById.get(),HttpStatus.OK);
            return ResponseEntity.ok(questionById.get());
        }else{
            throw new ChangeSetPersister.NotFoundException();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id,@RequestBody Question question) throws ChangeSetPersister.NotFoundException {
        Question updatedQuestion = questionService.updateQuestion(id,question);
        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteQuestion(@PathVariable Long id){
        questionService.deleteQuestion(id);
        System.out.println("Question deleted " + id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/subjects")
    public ResponseEntity<List<String>> getAllSubjects(){
        List<String> subjects = questionService.getAllSubjects();
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/user-questions")
    public ResponseEntity<List<Question>> getQuestionsForUser(@RequestParam Integer noOfQuestions, @RequestParam String subject){
        List<Question> allQuestions = questionService.getGetQuestionsForUser(noOfQuestions, subject);
        List<Question> mutableQuations = new ArrayList<>(allQuestions);
        Collections.shuffle(mutableQuations);

        int availableQuestion = Math.min(noOfQuestions, mutableQuations.size());
        List<Question> randomQuestions = mutableQuations.subList(0,availableQuestion);
        return ResponseEntity.ok(randomQuestions);
    }

}
