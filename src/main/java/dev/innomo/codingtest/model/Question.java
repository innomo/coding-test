package dev.innomo.codingtest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String question;
    @NotBlank
    private String subject;
    @NotBlank
    private String questionType;
    //Mappig other tables when We are not using our own entity
    @NotBlank
    @ElementCollection
    private List<String> choices;
    //Mappig other tables when We are not using our own entity
    @NotBlank
    @ElementCollection
    private List<String> correctAnswers;
}
