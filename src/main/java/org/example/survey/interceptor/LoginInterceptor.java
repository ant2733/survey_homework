package org.example.survey.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.survey.common.RedisConstant;
import org.example.survey.entity.JsonResult;
import org.example.survey.service.UserService;
import org.example.survey.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;      // JSON格式转换

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 解决跨域问题
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // 从请求头中获取Authorization
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            writeError(response, 401, "未登录，请先登录");
            return false;
        }

        // 拿到真正的token
        String token = authorization.substring(7);

        if (!jwtUtil.validateToken(token)) {
            writeError(response, 401, "登录状态已失效，请重新登录");
            return false;
        }

        // 判断Redis中是否存在这个token
        String redisKey = RedisConstant.LOGIN_TOKEN_KEY + token;
        String userId = stringRedisTemplate.opsForValue().get(redisKey);

        if (userId == null) {
            writeError(response, 401, "登录状态已过期，请重新登录");
            return false;
        }

        // 把当前用户id放到request当中，后面权限校验要用
        request.setAttribute("userId", Long.valueOf(userId));

        return true;
    }

    private void writeError(HttpServletResponse response, Integer code, String msg) throws Exception {
        response.setStatus(code);
        response.setContentType("application/json;charset=UTF-8");

        JsonResult result = JsonResult.error(code, msg);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }

}
