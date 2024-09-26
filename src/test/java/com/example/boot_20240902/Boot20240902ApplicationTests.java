package com.example.boot_20240902;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.entity.Board;
import com.example.entity.Member;
import com.example.mapper.BoardMapper;
import com.example.mapper.MemberMapper;
import java.util.List;

@SpringBootTest
class Boot20240902ApplicationTests {

	@Autowired
	BoardMapper bMapper;

	//회원가입 매퍼 테스트용
	@Autowired
	MemberMapper memberMapper;

	@Test 
	public void selectBoardTest(){
		List<Board> list=bMapper.selectBoardList();
		System.out.println(list.toString());
	}
	
	@Test
	void insertBoardTest(){
		Board obj= new Board();
		obj.setTitle("제목1");
		obj.setContent("내용1");
		obj.setWriter("작성자1");

		bMapper.insertBoardOne(obj);
	}

	@Test
	void memberInsertTest() {
		Member obj = new Member();
		obj.setUserid("b2");
		obj.setUserpw("b2");
		obj.setUserage(13);

		memberMapper.insertMemberOne(obj);
	}

}
