package com.example.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "freeboardreply1")
public class FreeBoardReply1 {
    
    @Id
    @Column(name = "no") //설정하지 않으면 변수명이 컬럼명이 된다.
    int no;

    String content;

    String writer;

    @ManyToOne //외래키 n (freeboardreply1) : 1 (freeboard1) 관계
    @JoinColumn(name ="bno", referencedColumnName = "no")
    // @JsonIgnore
    @JsonProperty(access = Access.WRITE_ONLY) //추가할때는 사용 가능 , 조회할때는 표시
    FreeBoard1 bno;

    //bno.setNo()

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    Date regdate;
}
