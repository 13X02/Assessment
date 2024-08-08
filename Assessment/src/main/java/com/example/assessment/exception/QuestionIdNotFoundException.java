package com.example.assessment.exception;

public class QuestionIdNotFoundException extends RuntimeException {
    public QuestionIdNotFoundException(Integer questionId) {
        super("Question ID not found: " + questionId);
    }
}