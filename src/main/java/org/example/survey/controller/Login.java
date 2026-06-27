package org.example.survey.controller;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.example.survey.dto.LoginDTO;
import org.example.survey.entity.JsonResult;
import org.example.survey.entity.User;
import org.example.survey.service.UserService;
import org.example.survey.util.JwtUtil;
import org.example.survey.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class Login {
    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public JsonResult login(@RequestBody LoginDTO loginDTO) {
        LoginVO userVO = userService.login(loginDTO);

        if (StringUtils.isEmpty(loginDTO.getUserName()) || StringUtils.isEmpty(loginDTO.getPassWord())) {
            return JsonResult.error(400, "用户名或者密码错误");
        }
        return JsonResult.success(userVO);
    }

    @PostMapping("/register")
    public JsonResult register(@RequestBody User user) {
        if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassWord())) {
            return JsonResult.error(400, "用户名或者密码错误");
        }
        return userService.register(user);
    }

    @GetMapping("/info")
    public JsonResult info(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");

        return JsonResult.success(userId);
    }

    @GetMapping("/role")
    public JsonResult role(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<String> permissions = userService.selectPermissionsByUserId2(userId);
        return JsonResult.success(permissions);
    }
}
