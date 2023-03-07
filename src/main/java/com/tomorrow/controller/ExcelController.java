package com.tomorrow.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ExcelController {
	@RequestMapping("/free/excelDown")
	public void excelDownload(HttpServletResponse response) throws IOException{
		XSSFWorkbook wb=null;
		Sheet sheet=null;
		Row row=null;
		Cell cell=null; 
		wb = new XSSFWorkbook();
		sheet = wb.createSheet("mysheet이름");
		
		// row(행) 생성
		row = sheet.createRow(0); //0번째 행
		cell=row.createCell(0);
		cell.setCellValue("0");
		cell=row.createCell(1);
		cell.setCellValue("가가가");
		cell=row.createCell(2);
		cell.setCellValue("나나나");
		
		row = sheet.createRow(1); //1번째 행
		cell=row.createCell(0);
		cell.setCellValue("1");
		cell=row.createCell(1);
		cell.setCellValue("AAA");
		cell=row.createCell(2);
		cell.setCellValue("BBB");
		
		row = sheet.createRow(2);  //2번째 행
		cell=row.createCell(0);
		cell.setCellValue("2");
		cell=row.createCell(1);
		cell.setCellValue("aaa");
		cell=row.createCell(2);
		cell.setCellValue("bbb");
		
		// 컨텐츠 타입과 파일명 지정
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=example.xlsx");  //파일이름지정.
		//response OutputStream에 엑셀 작성
		wb.write(response.getOutputStream());
	}
	
	
}