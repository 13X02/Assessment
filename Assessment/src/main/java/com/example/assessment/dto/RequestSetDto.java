package com.example.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestSetDto {

    private String setName;



    private String domain;


    private List<RequestQuestionDto> questions;
}
