package com.tomorrow.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.tomorrow.dto.CommuteDto;
import com.tomorrow.dto.NoticeDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "commute")
@Getter
@Setter
@ToString
public class Commute {

	/*
	 * 출근 퇴근 자동으로 입력되는 컬럼 일반 date 컬럼으로 변경
	 * 
	 * 이유 : 테이블이 생성될 때 자동으로 입력되는 컬럼이기 때문에 출근 테이블과 퇴근테이블을 따로 만들기엔 부담이 있어서 date컬럼으로
	 * 만들었음 나중에 입력할때 출근 insert 시 localdatetime.now()로 입력하고 퇴근은 update로 (컨트롤러에서
	 * 날짜수정해서 localdatetime.now()로) 입력하면 될 것 같습니다.
	 */

	// TODO : 로컬데이트.나우 오늘날짜 가져오고 findByworking이 없으면 출근버튼을 보여주고
	// 있으면 퇴근 버튼을 보여준다.

	@Id
	@Column(name = "commute_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; // 출퇴근 식별번호

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(nullable = false)
	private LocalDateTime working; // 출근날짜시간

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(nullable = false)
	private LocalDateTime leaving; // 퇴근날짜시간

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_id")
	private Shop shop; // 매장코드 FK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member; // 회원 식별번호 FK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pay_list_id")
	private PayList payList; // 급여일지 식별번호 FK

	
	public static Commute crateCommute(CommuteDto commuteDto, Member member, Shop shop) {
	
		Commute commute = new Commute();
		
		commute.setShop(shop);
		commute.setMember(member);
		commute.setWorking(commuteDto.getWorking());
		
		return commute;
		
	}
	
	
}
