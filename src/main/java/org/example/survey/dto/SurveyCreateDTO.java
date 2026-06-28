package org.example.survey.dto;

import lombok.Data;

@Data
public class SurveyCreateDTO {
    private String title;
    private String description;
    private String userName;
    private Long creatorId;
}
