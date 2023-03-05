package com.tomorrow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.tomorrow.entity.Hire;

public interface HireRepository extends JpaRepository<Hire, Long>,
QuerydslPredicateExecutor<Hire>{
    
}
