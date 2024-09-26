package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Board {
    private int no = 0;
    private String title = null;
    private String content = null;
    private String writer = null;
    private int hit = 1; 
    private Date regdate = null;
}
