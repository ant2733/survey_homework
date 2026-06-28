package org.example.survey.controller;

import org.example.survey.annotation.RequirePermission;
import org.example.survey.dto.SurveyCreateDTO;
import org.example.survey.entity.JsonResult;
import org.example.survey.service.SurveyService;
import org.example.survey.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/survey")
public class SurveyController {
    @Autowired
    private SurveyService surveyService;

    @Autowired
    private UserService userService;

    @RequirePermission("survey:create")
    @PostMapping
    public JsonResult create(@RequestBody SurveyCreateDTO dto) {
        surveyService.createSurvey(dto);
        return JsonResult.success("问卷创建成功，题目是" + dto.getTitle());
    }
}
