package com.example.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "memberaddress1")
public class MemberAddress1 {
    
    @Id
    long no;

    @Column(name = "post_code")
    String postCode;

    String address; 
    String address1;

    @ManyToOne //외래키 n (freeboardreply1) : 1 (freeboard1) 관계
    @JoinColumn(name ="memberid", referencedColumnName = "id")
    // @JsonIgnore
    @JsonProperty(access = Access.WRITE_ONLY) //추가할때는 사용 가능 , 조회할때는 표시
    Member1 memberid;

    //bno.setNo()

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    Date regdate;

    int idx;
}
