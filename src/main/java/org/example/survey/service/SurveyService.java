package org.example.survey.service;

import org.example.survey.dto.SurveyCreateDTO;
import org.springframework.stereotype.Service;

@Service
public interface SurveyService {
    void createSurvey(SurveyCreateDTO dto);
}
