package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com. example.entity.Member;

//자동 import => alt+shift+o
@Mapper
public interface MemberMapper {

    @Select({
        " SELECT userid, userpw, userage ",
        "FROM member ORDER BY userid ASC "
})



public List<Member> selectMemberList();    

@Insert({
            " INSERT INTO member(userid, userpw, userage)",
            " VALUES(#{obj.userid} , #{obj.userpw} , #{obj.userage})"})
    public int insertMemberOne(@Param("obj") Member obj);

}
