package com.example.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="item1image")
public class Item1Image {
    @Id
    int no;

    String filename;
    String filetype;
    long filesize;

    byte[] filedata;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "itemno" , referencedColumnName = "no")
    Item1 itemno;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp 
    Date regdate;
    
}
