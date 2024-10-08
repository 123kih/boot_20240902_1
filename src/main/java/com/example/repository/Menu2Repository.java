package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Menu2;

@Repository
public interface Menu2Repository extends JpaRepository<Menu2,Integer>{
    
}
