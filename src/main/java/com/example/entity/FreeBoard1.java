package com.example.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="freeboard1")
public class FreeBoard1 {
    
    @Id
    int no;
    String title;
    String content;
    String writer;
    int hit;
    String filename;
    String filetype;
    long filesize;
    byte[] filedata;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp 
    Date regdate;
    

}
