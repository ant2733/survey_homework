package org.example.survey.mapper;

import org.example.survey.dto.SurveyCreateDTO;

public interface SurveyMapper {
    int createSurveyUseTitle(SurveyCreateDTO dto);
}
