package com.tomorrow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tomorrow.entity.ShareTipImg;

public interface ShareTipImgRepository extends JpaRepository<ShareTipImg, Long> {

	@Query(value = "select * from share_tip_img s where s.tip_id = :tipId order by tip_img_id Asc", nativeQuery = true)
	List<ShareTipImg> findByTipIdOrderByIdAsc(@Param("tipId") Long tipId);
}
