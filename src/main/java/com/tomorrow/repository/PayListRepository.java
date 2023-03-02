package com.tomorrow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tomorrow.entity.PayList;

public interface PayListRepository extends JpaRepository<PayList, Long> {

	Optional<PayList> findByMapId(Long mapId);

}
