package com.tomorrow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tomorrow.entity.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

	Manager findByUserId(String userId);

}
