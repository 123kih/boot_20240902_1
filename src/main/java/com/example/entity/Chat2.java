package com.example.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.*;

@Entity
@Data
@Table(name = "chat2")
public class Chat2 {
    @Id
    
    //데이터베이스가 자동으로 AUTO_INCREMENT를 하여 기본키를 생성해준다.
    //기본 키 생성을 데이터베이스에 위임하는 전략 (주로 MySQL, PostgreSQL, SQL Server에서 사용)
    // AUTO_INCREMENT처럼 데이터베이스에 값을 저장하고 나서야 기본 키 값을 구할 수 있을 때 사용
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    int no;

    String recv;

    String send;

    String msg;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    Date regdate;
}
