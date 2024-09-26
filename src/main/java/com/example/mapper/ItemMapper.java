package com.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.entity.Item;
import java.util.List;

@Mapper
public interface ItemMapper {

    @Insert({" INSERT INTO Item(name,price,qty,content)" ,
            "VALUES (#{name} , #{price} , #{qty} , #{content})"})
    public int insertItemOne(Item obj);

    @Select({" SELECT * FROM Item "})
    public List<Item> selectItemOne();
}
