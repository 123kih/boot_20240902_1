package com.example.token;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//JWTFilter가 전체 페이지에 적용 되는데 특정 페이지만 적용하기 위해 설정
@Configuration
public class JWTFilterConfig {
    
    // JWTFilter는 아래 설정된 3개의 url에 대해서만 필터가 동작됨
    @Bean
    public FilterRegistrationBean<JWTFilter> filterRegistrationBean(JWTFilter jwtFilter){

        FilterRegistrationBean<JWTFilter> filterReg = new FilterRegistrationBean<>();
        filterReg.setFilter(jwtFilter);

        //아래의 3개만 필터 작동
        filterReg.addUrlPatterns("/api/member1/update.json" ,
                                "/api/member1/updatepw.json" ,
                                "/api/member1/delete.json");
        return filterReg;
    }

    //JWTFilter1 
}
