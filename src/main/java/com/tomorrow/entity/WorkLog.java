package com.tomorrow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tomorrow.dto.WorkLogDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "work_log")
@Getter
@Setter
@ToString
public class WorkLog extends BaseEntity  {

	@Id
	@Column(name = "log_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;						//  근무일지 식별번호
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;					// 회원 식별번호 FK
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_id")
	private Shop shop;						// 매장코드 FK
	
	@Column(nullable = false, columnDefinition = "LONGTEXT")
	private String logCont;					// 전달내용
	
	public static WorkLog createWorkLog(WorkLogDto workLogDto, Member member, Shop shop) {
		
		WorkLog workLog = new WorkLog();
		
		workLog.setShop(shop);
		workLog.setMember(member);
		workLog.setLogCont(workLogDto.getLogCont());
		return workLog;
	}
	
	public void updateWorkLog(WorkLogDto workLogDto, Member member, Shop shop) {
		
		this.shop = shop;
		this.member = member;
		this.logCont = workLogDto.getLogCont();
	}
}
