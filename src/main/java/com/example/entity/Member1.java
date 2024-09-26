package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

import java.util.*;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

//JPA는 entity에만 작성하고 Mapper를 이용한 쿼리문을 따로 작성하지 않음
//데이터베이스에있는 구조와 똑같이 생성해야함(쿼리문을 작성하지 않을거기 때문)
@Data
@Entity
@Table(name = "member1")
public class Member1 {
    
    @Id //기본키 선언
    @Column(name="id")
    String id;

    private String pw; //컬럼명 선언 생략 가능

    @Transient //테이블 없어도됨 . 임시용
    private String newpw;

    private String name; 

    private String phone; 

    private int age; 
    
    private String role;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp 
    private Date regdate; 

    @OneToMany(mappedBy = "sid")
    List<Item1> items= new ArrayList<>();
}
