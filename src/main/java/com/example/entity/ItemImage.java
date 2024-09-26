package com.example.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"filedata"})
@NoArgsConstructor
@AllArgsConstructor
public class ItemImage {
    int no;
    String filename;
    String filetype;
    int filesize;
    byte[] filedata;
    int itemno;
    Date regdate;
}
