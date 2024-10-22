package com.example.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Category3 {
  // 코드
  @Id
  private Integer code;
  // 카테고리명
  private String name;
  // 등록일
  private Date regdate;
  // 메뉴

}