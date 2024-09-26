package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.example.entity.Board;

@Mapper
public interface BoardMapper {


    //조회수 1증가
    public int updateBoardHit(int no);

    //게시글 1개 조회
    public Board selectBoardOne(int no);

    //전체 조회
    public List<Board> selectBoardList();


    //글 쓰기 (insert , update , delete 리턴타입이 int)
    public int insertBoardOne(Board board);

    //글 삭제 (xml에 sql문 작성 안하는 방법)
    @Delete({"DELETE FROM board WHERE no= #{no}"})
    public int deleteBoardOne(int no);

    //글 수정 (xml에 sql문 작성 안하는 방법)
    @Update({"UPDATE board SET title=#{title} , content = #{content} , writer= #{writer}",
                " WHERE no = #{no}"})
    public int updateBoardOne(Board board);
}
