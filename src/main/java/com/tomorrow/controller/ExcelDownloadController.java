package com.tomorrow.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/vi/excel")
@RestController
public class ExcelDownloadController {

	@GetMapping("/RestExcel")
	public void excelDownload(HttpServletResponse response, Principal principal) throws IOException {
		// Header, ContentType 설정, excel 파일명 변경 가능
		response.setHeader("Content-Disposition", "attachment;filename=excelFileName.xlsx");
		response.setContentType("application/octet-stream");
		
		
	}
}
