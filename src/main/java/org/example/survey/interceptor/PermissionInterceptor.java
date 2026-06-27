package org.example.survey.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.annotations.Result;
import org.example.survey.annotation.RequirePermission;
import org.example.survey.entity.JsonResult;
import org.example.survey.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Component
public class PermissionInterceptor implements HandlerInterceptor {
    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private ObjectMapper objectMapper;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是 Controller 方法，直接放行
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        Method method = handlerMethod.getMethod();

        // 获取方法上的自定义注解
        RequirePermission requirePermission = method.getAnnotation(RequirePermission.class);

        if (requirePermission == null) {
            return true;
        }

        // 获取注解里面的值
        String[] requiredPermissions = requirePermission.value();

        // 从request当中获取userId
        Long userId = (Long) request.getAttribute("userId");
        if  (userId == null) {
            writeError(response, 401, "未登录，请先登录");
            return false;
        }

        // 查询当前用户所有的权限
        List<String> userPermissions = permissionMapper.selectPermissionsByUserId(userId);

        // 判断用户是否拥有所需权限
        boolean hasPermission = Arrays.stream(requiredPermissions)
                .anyMatch(userPermissions::contains);

        if (!hasPermission) {
            writeError(response, 403, "权限不足，无法访问");
            return false;
        }

        // 有权限，放行
        return true;
    }

    private void writeError(HttpServletResponse response, Integer code, String msg) throws Exception {
        response.setStatus(code);
        response.setContentType("application/json;charset=UTF-8");

        JsonResult result = JsonResult.error(code, msg);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
