package com.example.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
public class Item1 {
    
    @Id //기본키
    int no;

    String name;

    String content;

    int price;

    int quantity;

    //외래키 ( n(Item1) : 1(Member1))
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "sid" , referencedColumnName = "id")
    Member1 sid;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp 
    Date regdate;

    //테이블에 컬럼이 없으면
    @Transient //java에서 사용하기 위한 변수
    String imageurl;
}
