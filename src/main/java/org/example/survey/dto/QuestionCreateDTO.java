package org.example.survey.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionCreateDTO {
    private Long surveyId;
    private String questionTitle;
    private Integer questionType;
    private Integer required;
    private Integer sort;
    private List<String> options;
}
