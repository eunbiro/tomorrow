package com.tomorrow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.tomorrow.entity.ShareTipComment;

public interface ShareTipCommentRepository extends JpaRepository<ShareTipComment, Long>, QuerydslPredicateExecutor<ShareTipComment>, ShareTipRepositoryCustom  {

	@Query(value = "select * from share_tip_comment s where s.tip_id = :tipId order by tip_cmt_id Asc", nativeQuery = true)
	List<ShareTipComment> findByTipIdOrderByIdAsc(@Param("tipId") Long tipId);
	
	List<ShareTipComment> findByCreatedBy(String createdBy);
	
	List<ShareTipComment> findById(String Id);
}
