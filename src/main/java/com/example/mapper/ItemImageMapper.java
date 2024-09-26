package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.entity.ItemImage;
@Mapper
public interface ItemImageMapper {

        @Update({
                "UPDATE Itemimage SET filename=#{filename}, filesize=#{filesize} , filedata=#{filedata} , filetype=#{filetype} ",
                " WHERE no=#{no} AND itemno=#{itemno}"
        })
        public int updateItemImageOne(ItemImage obj);


        @Delete({
                "DELETE FROM Itemimage WHERE no=${no}"
        })
        public int deleteItemImageOne(int no);

        @Select({
                "SELECT no FROM Itemimage WHERE itemno = #{itemno}"
        })
        public List<ItemImage> selectItemImageNo(int itemno);

        @Insert({"INSERT INTO Itemimage(filename, filetype , filesize, filedata, itemno)",
                " VALUES(#{filename}, #{filetype} , #{filesize}, #{filedata}, #{itemno})"})
        public int insertItemImageOne(ItemImage obj);

        @Select({ "SELECT filename, filetype ,filesize, filedata FROM" , 
                " Itemimage Where no= #{no}"})
        public ItemImage selecItemImage(int no);
}
