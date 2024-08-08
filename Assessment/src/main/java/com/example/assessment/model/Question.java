package com.example.assessment.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer questionId;
    private String questionText;



    @OneToMany
    @JoinColumn(name = "question_id")
    @Cascade(value = CascadeType.ALL)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<OptionModel> answers;
}
