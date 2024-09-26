package com.example.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Board1 {
    
    @Id //기본키
    int no;

    @Column(name="title")
    String title;

    String content;

    @ManyToOne
    @JoinColumn(name="writer" , referencedColumnName="id")
    Member1 writer;
    
    int hit;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp 
    Date regdate;

    @ManyToOne
    @JoinColumn(name = "itemno" , referencedColumnName= "no")
    Item1 itemno;
}
