package org.example.survey.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SurveyQuestion {
    private Long id;
    private Long surveyId;
    private String questionTitle;
    private Integer questionType;
    private Integer required;
    private Integer sort;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
