package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//시큐리티 환경 설정

@Configuration //환경설정 파일
@EnableWebSecurity //시큐리티 활성화
public class SecurityConfig {
    
    @Bean // 서버가 구동되면 자동호출 되면서 객체도 자동으로 생성
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // 권한설정, 로그인페이지 , 로그아웃 ...
        http
            .csrf((csrf) -> csrf
                .ignoringRequestMatchers("/api/**")
            )
            .httpBasic(AbstractHttpConfigurer::disable)
            
            //권한 설정
            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/admin","/admin/*").hasRole("ADMIN")
                .requestMatchers("/seller","/sellser/*").hasRole("SELLER")
                .requestMatchers("/customer","/customer/*").hasRole("CUSTOMER")
                .anyRequest().permitAll()
            )

            //접근불가 페이지
            .exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedPage("/403page.do"))
            
            //로그인
            .formLogin(formLogin -> formLogin
                .loginPage("/login.do")
                .loginProcessingUrl("/loginaction.do")
                .usernameParameter("id")
                .passwordParameter("pw")
                .successHandler(new LoginHandler())
            )
                // .defaultSuccessUrl("/home.do")) 로그인 후 권한에 관계없이 모두 동일한 페이지로 이동

            //로그아웃
            .logout((logout) -> logout
                .logoutUrl("/logout.do")
                .logoutSuccessHandler(new LogoutHandler())
                // .logoutSuccessUrl("/home.do")
                .invalidateHttpSession(true)
            )

            
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
            );

        return http.build();
    }

    //회원가입시 사용했던 암호화 방법( BCrypt )
    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }
}
