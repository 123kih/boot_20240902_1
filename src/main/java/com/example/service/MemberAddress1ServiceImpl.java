package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.MemberAddress1;
import com.example.repository.MemberAddress1Repository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberAddress1ServiceImpl implements MemberAddress1Service{

    final MemberAddress1Repository mAddress1Repository;

    //추가
    @Override
    public int insertAddress(MemberAddress1 obj) {
        try{
            mAddress1Repository.save(obj);
            return 1;
        }catch(Exception e){
            System.err.println(e.getMessage()); //개발자 DEBUG용
            return -1;
        }
    }

    //삭제
    @Override
    public int deleteAddress(long no, String memberid) {
        
        try{
            mAddress1Repository.deleteById(no);
            return 1;
        }catch(Exception e){
            System.err.println(e.getMessage());
            return -1;
        }
    }

    @Override
    public int deleteBatchAddress(String memberid) {
    
        
        return 0;
    }

    //회원별 주소목록
    @Override
    public List<MemberAddress1> selectAddressList(String memberid) {
        try{
            List<MemberAddress1> list = mAddress1Repository.findByMemberid_id(memberid);
            return list;

        }catch(Exception e){
            System.err.println(e.getMessage()); //개발자 DEBUG용
            return null;
        }
    }

    @Override
    public int updateAddress(MemberAddress1 obj) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int updateAddressTop1(long no, String memberid) {
        // TODO Auto-generated method stub
        return 0;
    }
    
}
