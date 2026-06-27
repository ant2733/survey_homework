package org.example.survey.service;

import org.example.survey.dto.LoginDTO;
import org.example.survey.entity.JsonResult;
import org.example.survey.entity.User;
import org.example.survey.vo.LoginVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    LoginVO login(LoginDTO loginDTO);
    JsonResult register(User user);
    List<String> selectPermissionsByUserId2(Long userId);
}
