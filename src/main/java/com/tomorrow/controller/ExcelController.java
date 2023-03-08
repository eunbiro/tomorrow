package com.tomorrow.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.tomorrow.entity.MemShopMapping;
import com.tomorrow.entity.Shop;
import com.tomorrow.repository.MemShopMapRepository;


@Controller
public class ExcelController {
	
	@Autowired
	MemShopMapRepository repository;
	
	@GetMapping("/excel")
	public void downloadExcel(HttpServletResponse response) throws IOException {
		
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("직원 정보");
		int rowNo = 0;
		
		Row headerRow = sheet.createRow(rowNo++);
		headerRow.createCell(0).setCellValue("이름");
		headerRow.createCell(1).setCellValue("전화번호");
		headerRow.createCell(2).setCellValue("근무시간");
		headerRow.createCell(3).setCellValue("시급");
		headerRow.createCell(4).setCellValue("상태");
		
		
		List<MemShopMapping> list = repository.findAll();
		for (MemShopMapping memShopMapping : list) {
			Row row = sheet.createRow(rowNo++);
			row.createCell(0).setCellValue(memShopMapping.getMember().getUserNm());
			row.createCell(1).setCellValue(memShopMapping.getMember().getPNum());
			row.createCell(2).setCellValue(memShopMapping.getPartTime());
			row.createCell(3).setCellValue(memShopMapping.getTimePay());
			row.createCell(4).setCellValue(memShopMapping.getWorkStatus());
		}

		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=mappingList.xls");
		
		workbook.write(response.getOutputStream());
		workbook.close();
	}
	
	
}