package com.example.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "order2")
public class Order2 {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int no;

    @ManyToOne
    @JoinColumn(name = "customerid" , referencedColumnName = "id")
    @JsonProperty(access = Access.WRITE_ONLY)
    Member1 customerid;

    @ManyToOne
    @JoinColumn(name = "menu" , referencedColumnName = "no")
    @JsonProperty(access = Access.WRITE_ONLY)
    Menu2 menu;
    
    int qty;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    Date regdate;



}
