package org.example.survey.service.impl;

import org.example.survey.dto.SurveyCreateDTO;
import org.example.survey.mapper.SurveyMapper;
import org.example.survey.mapper.UserMapper;
import org.example.survey.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurveyServiceImpl implements SurveyService {
    @Autowired
    private SurveyMapper surveyMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void createSurvey(SurveyCreateDTO dto) {
        if (dto == null) {
            throw new RuntimeException("请输入问卷题目和描述");
        }
        dto.setCreatorId(userMapper.getUserId(dto.getUserName()));
        surveyMapper.createSurveyUseTitle(dto);
    }
}
