package com.tomorrow.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tomorrow.dto.ShareTipListDto;
import com.tomorrow.dto.ShareTipSearchDto;

public interface ShareTipRepositoryCustom {

	Page<ShareTipListDto> getShareTipListPage(ShareTipSearchDto shareTipSearchDto, Pageable pageable);
}
