package com.example.token;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter{

    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired 
    JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(
                HttpServletRequest request, 
                HttpServletResponse response,
                FilterChain filterChain) throws ServletException, IOException {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String,Object> map =new HashMap<>();
        try{

            //0. 설정된 필터만 동작하는 확인용
            System.out.println("=============filter===============");
            System.out.println(request.getRequestURI());
            System.out.println("=============filter===============");

            //1.토근 꺼내기
            String token = request.getHeader("token");
            if(token == null){
                map.put("status", 0);
                map.put("result", "토큰 키 값이 없습니다.");
                String json = objectMapper.writeValueAsString(map);
                response.getWriter().write(json);
                return ;
            }

            if(token.length() <= 0 ){
                map.put("status", 0);
                map.put("result", "토큰 값이 없습니다.");
                String json = objectMapper.writeValueAsString(map);
                response.getWriter().write(json);
                return ;
            }

            Claims claims = jwtUtil.validate(token);
            String tokenID =(String) claims.get("ID");

            //컨트롤러에서 사용하기 위해 세션에 ID 저장
            request.getSession().setAttribute("ID", tokenID);

            //doFilter가 실행되어야 controller로 진입할 수있음.
            filterChain.doFilter(request, response);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            map.put("status", 0);
            map.put("result", "유효한 토큰이 아닙니다..");
            String json = objectMapper.writeValueAsString(map);
            response.getWriter().write(json);
        }
    }
    
}
