package org.example.survey.entity;

import lombok.Data;

@Data
public class SurveyOption {
    private Long id;
    private Long questionId;
    private String optionContent;
    private Integer sort;
}
