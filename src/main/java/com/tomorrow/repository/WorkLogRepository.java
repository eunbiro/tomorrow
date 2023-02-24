package com.tomorrow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tomorrow.entity.WorkLog;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {

	List<WorkLog> findByShopIdOrderByIdDesc(Long shopId);
}
