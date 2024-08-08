package com.example.assessment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SetNameNotFoundException.class)
    public ResponseEntity<String> handleSetNameNotFoundException(SetNameNotFoundException ex) {

        String message =  " "+ ex.getMessage();
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
    @ExceptionHandler(SetIdNotFoundException.class)
    public ResponseEntity<String> handleSetIdNotFoundException(SetIdNotFoundException ex) {

        String message = " " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
    @ExceptionHandler(QuestionIdNotFoundException.class)
    public ResponseEntity<String> handleQuestionIdNotFoundException(QuestionIdNotFoundException ex) {

        String message = " " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }


}
