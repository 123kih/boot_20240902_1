package com.example.entity;

import java.util.Date;

import org.hibernate.annotations.Immutable;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "order2_view")
@Immutable //테이블이 VIEW 일 경우 정의할때 어노테이션 사용
@Data
public class Oreder2View {
    
    @Id
    int no;

    //외래키 필요없음
    String customerid;

    int qty; 

    Date regdate;

    String name;

    int price;

    String username;

    String phone;

}
