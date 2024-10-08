package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.MemberAddress1;

//설계하기
@Service
public interface MemberAddress1Service {
    
    //주소 변경
    public int updateAddress(MemberAddress1 obj);

    //주소 보관
    public int insertAddress (MemberAddress1 obj);

    //주소 삭제
    public int deleteAddress(long no , String memberid);

    //대표주소 설정
    public int updateAddressTop1(long no , String memberid);

    //회원별 주소조회
    public List<MemberAddress1> selectAddressList(String memberid);

    //회원별 주소 일괄삭제
    public int deleteBatchAddress(String memberid);

}
