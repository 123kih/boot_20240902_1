package com.example.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.*;

@Entity
@Data
@Table(name="menu2")
public class Menu2 {
    @Id
    int no;

    String name;

    int price;
    
    @ManyToOne
    @JoinColumn(name="restaurant", referencedColumnName = "cr_number")
    @JsonProperty(access = Access.WRITE_ONLY)
    Restaurant2 restaurant;
    
    @ManyToOne
    @JoinColumn(name="cate", referencedColumnName = "code")
    @JsonProperty(access = Access.WRITE_ONLY)
    Category2 cate;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    Date regdate;
}
