package com.example.entity;

import java.util.Date;

import lombok.Data;

@Data
public class Item {
    int no;
    String name;
    String content;
    int price;
    int qty;
    Date regdate;

}
