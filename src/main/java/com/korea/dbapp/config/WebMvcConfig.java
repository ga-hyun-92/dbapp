package com.korea.dbapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//IoC에 등록!!!!!!!!!!!
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // 인터셉터가 언제 동작할지 설정해줌
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/post/**").excludePathPatterns("/post/");
    }
}
