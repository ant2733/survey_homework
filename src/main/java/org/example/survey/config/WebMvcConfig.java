package org.example.survey.config;

import org.example.survey.interceptor.LoginInterceptor;
import org.example.survey.interceptor.PermissionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private PermissionInterceptor permissionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

//        拦截 /api/** 下的所有接口
//         但是不拦截：
//         - /api/user/login
//         - /api/user/register


        // 登录拦截器
        registry.addInterceptor(loginInterceptor).addPathPatterns("/api/**")
                .excludePathPatterns("/api/user/login", "/api/user/register");


        // 权限拦截器
        registry.addInterceptor(permissionInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/user/login",
                        "/api/user/register"
                );
    }

}
