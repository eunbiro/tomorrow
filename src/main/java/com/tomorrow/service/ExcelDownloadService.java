package com.tomorrow.service;

import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tomorrow.dto.MemShopMappingDto;

@Service
public class ExcelDownloadService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	// 엑셀 다운로드 구현
	public XSSFWorkbook excelDownloadXSSF(List<MemShopMappingDto> memberList, String kind) throws Exception {
		// 엑셀 다운 시작
		XSSFWorkbook workbook = new XSSFWorkbook();

		// 엑셀 시트명 생성
		Sheet sheet = workbook.createSheet(kind);

		// 행, 열
		Row row = null;
		Cell cell = null;

		// 헤더명
		String[] headerKey = { "이름", "전화번호", "근무시간", "시급", "상태" };

		// 테이블 헤더 스타일 적용
		CellStyle headerStyle = CellStyleSetting(workbook, "header");
		// 테이블 데이터 스타일 적용
		CellStyle dataStyle = CellStyleSetting(workbook, "data");

		row = sheet.createRow(0);
		for (int i = 0; i < headerKey.length; i++) { // 헤더 구성
			cell = row.createCell(i);
			cell.setCellValue(headerKey[i]);
			cell.setCellStyle(headerStyle);
		}

		for (int i = 0; i < memberList.size(); i++) {
			row = sheet.createRow(i + 1);
			int cellIdx = 0;

			MemShopMappingDto mappingDto = memberList.get(i);

			cell = row.createCell(cellIdx++);
			cell.setCellValue(mappingDto.getMemberFormDto().getUserNm());
			cell.setCellStyle(dataStyle);

			cell = row.createCell(cellIdx++);
			cell.setCellValue(mappingDto.getMemberFormDto().getPNum());
			cell.setCellStyle(dataStyle);

			cell = row.createCell(cellIdx++);
			cell.setCellValue(mappingDto.getPartTime());
			cell.setCellStyle(dataStyle);

			cell = row.createCell(cellIdx++);
			cell.setCellValue(mappingDto.getTimePay());
			cell.setCellStyle(dataStyle);

			cell = row.createCell(cellIdx++);
			cell.setCellValue(mappingDto.getWorkStatus());
			cell.setCellStyle(dataStyle);
		}

		// 셀 넓이 자동 조정
		for (int i = 0; i < headerKey.length; i++) {
			sheet.autoSizeColumn(i);
			sheet.setColumnWidth(i, sheet.getColumnWidth(i));
		}
		return workbook;
	}

	// 각 셀의 스타일 세팅
	public CellStyle CellStyleSetting(XSSFWorkbook workbook, String kind) {
		// 테이블 스타일
		CellStyle cellStyle = workbook.createCellStyle();

		// 가는 경계선
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);

		if (kind.equals("header")) {
			// 배경색 회색
			cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		}

		// 데이터는 가운데 정렬
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 중앙 정렬

		// 폰트 설정
		Font fontOfGothic = workbook.createFont();
		fontOfGothic.setFontName("맑은 고딕");
		cellStyle.setFont(fontOfGothic);

		return cellStyle;
	}

}
