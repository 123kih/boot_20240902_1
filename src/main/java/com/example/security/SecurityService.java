package com.example.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.entity.Member1;
import com.example.repository.Member1Repository;

@Service
public class SecurityService implements UserDetailsService{

    @Autowired
    Member1Repository member1Repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        //아이디를 이용해 DB에서 꺼냄( 아이디 , 암호 , 권한)
        Member1 obj = member1Repository.findById(username).orElse(null);
        if(obj != null){
            String[] strRole = {"ROLE_" + obj.getRole() }; //ROLE_CUSTOMER , ROLE_ADMIN
            Collection<GrantedAuthority> roles = AuthorityUtils.createAuthorityList(strRole); 
            return new User(obj.getId(), obj.getPw() , roles);
        }
        

        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }
    
    
}
