package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Oreder2View;

@Repository
public interface Order2ViewRepository extends JpaRepository<Oreder2View , Integer>{
    
}
