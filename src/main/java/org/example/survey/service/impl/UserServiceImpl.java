package org.example.survey.service.impl;

import org.example.survey.common.RedisConstant;
import org.example.survey.dto.LoginDTO;
import org.example.survey.entity.JsonResult;
import org.example.survey.entity.User;
import org.example.survey.mapper.PermissionMapper;
import org.example.survey.mapper.UserMapper;
import org.example.survey.service.UserService;
import org.example.survey.util.JwtUtil;
import org.example.survey.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private PermissionMapper permissionMapper;


    @Override
    public JsonResult register(User user) {
        int row = userMapper.addUser(user);
        if (row == 1) {
            return JsonResult.success("用户注册成功");
        }
        else {
            return JsonResult.error(400, "用户注册失败");
        }
    }

    @Override
    public List<String> selectPermissionsByUserId2(Long userId) {
        List<String> permissions = permissionMapper.selectPermissionsByUserId(userId);
        return permissions;
    }

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        User user = userMapper.selectByUserName(loginDTO.getUserName());

        if (user == null || !user.getPassWord().equals(loginDTO.getPassWord())) {
            throw new RuntimeException("用户名或者密码错误");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUserName());

        // 将token存入Redis
        stringRedisTemplate.opsForValue().set(
                RedisConstant.LOGIN_TOKEN_KEY + token,
                String.valueOf(user.getId()),
                7200,
                TimeUnit.SECONDS
        );      // key: login:token:用户的token   value: 用户ID

        return new LoginVO(token, user.getId(), user.getUserName());
    }

}
