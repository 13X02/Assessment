package com.example.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ResponseSetDto {
    private Integer setId;
    private String setName;

    private String createdBy;
    private Date createdAt;
    private Date modifiedAt;

    private String domain;

    private String status;

    private List<ResponseQuestionDto> questions;
}
