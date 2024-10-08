package com.example.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.*;

@Data
@Entity
@Table(name = "restaurant2")
public class Restaurant2 {
    
    @Id
    @Column(name = "cr_number")
    String crNumber; //cr_number

    String name;

    String phone;

    String password;

    String address;
            
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    Date regdate;
}