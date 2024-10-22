package com.example.entity;

import java.util.Date;

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

@Entity
@Data
@Table(name = "order2step")
public class Order2Step {
    
    @Id
    int code;

    @ManyToOne
    @JoinColumn(name="orderno", referencedColumnName = "no")
    @JsonProperty(access = Access.WRITE_ONLY)
    Order2 orderno;

    //0=>취소 , 1=>등록 , 2=>결제완료 , 3=>배달완료
    int orderstep;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    Date regdate;
}
