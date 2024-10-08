package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Category2;

@Repository
public interface Category2Repository extends JpaRepository<Category2, Integer>{
    
}
