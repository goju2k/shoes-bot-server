package com.my.document.generator.app.config;

import com.my.document.generator.app.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${authKey}")
    private String key;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> URL_PATTERNS = Arrays.asList("/**");
        registry.addInterceptor(new AuthInterceptor().setAuthKey(key))
                .addPathPatterns(URL_PATTERNS)
                .excludePathPatterns("/server/*");
    }
}
