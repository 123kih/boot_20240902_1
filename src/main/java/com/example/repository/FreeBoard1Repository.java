package com.example.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.FreeBoard1;
import com.example.entity.FreeBoard1Projection;

import java.util.*;
import java.util.List;


@Repository
public interface FreeBoard1Repository extends JpaRepository<FreeBoard1 , Integer>{

    //검색어 + 페이지네이션
    //SELECt no ,title, writer, hit, regdate FROM freeboard1 
    //WHERE title CONCAT('%','검색어','%') LIMIT 1,10

    List<FreeBoard1Projection> findByTitleContainingOrderByNoDesc(String title);
    List<FreeBoard1Projection> findByTitleContainingOrderByNoDesc(String title, Pageable pageable) ;

    //SELECT COUNT(*) FROM freeboard1 WHERE title CONCAT('%','검색어','%') 
    Long countByTitleContainingOrderByNoDesc(String title);

    //이전글 ex) 현재글번호 34 => 34보다 작으면서 가장 큰 숫자 33
    //SELECT MAX(no) FROM freeboard1 WHERE no < 34
    //35 34 33 .. 30 ... 1
    FreeBoard1Projection findTop1ByNoLessThanOrderByNoDesc(int no);

    //다음글 ex)현재글번호 34 => 34보다 크면서 가장 작은 숫자 35
    //SELECT MIN(no) FROM freeboard1 WHERE no > 34
    FreeBoard1Projection findTop1ByNoGreaterThanOrderByNoAsc(int no);

    //다음글
    @Query(value = "SELECT MIN(no) no FROM freeboard1 WHERE no > :no " , nativeQuery=true)
    public long selectQueryNext(@Param("no") int no);

    //조회수 1증가
    // 삭제한다던가 변경하기 위해서는 @Transactional
    //@Modifying(clearAutomatically = true) 사용 해야함.
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE freeboard1 SET hit = hit + 1 WHERE no = :no",nativeQuery = true)
    public int updateQueryHit(@Param("no") int no);

    //한개조회
    FreeBoard1Projection findByNo(int no);
}

