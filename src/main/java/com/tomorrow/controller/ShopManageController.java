package com.tomorrow.controller;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tomorrow.dto.CommuteDto;
import com.tomorrow.dto.MemShopMappingDto;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.dto.PayListDto;
import com.tomorrow.dto.ShopDto;
import com.tomorrow.entity.MemShopMapping;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.PayList;
import com.tomorrow.repository.CommuteRepository;
import com.tomorrow.repository.MemShopMapRepository;
import com.tomorrow.repository.PayListRepository;
import com.tomorrow.entity.Commute;
import com.tomorrow.entity.Shop;
import com.tomorrow.service.CommuteService;
import com.tomorrow.service.EmployeeInfoService;
import com.tomorrow.service.MemberService;
import com.tomorrow.service.PayListService;
import com.tomorrow.service.ShopInfoService;
import com.tomorrow.service.ShopService;

import groovyjarjarantlr4.v4.parse.ANTLRParser.finallyClause_return;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
public class ShopManageController {
   private final ShopService shopService;
   private final CommuteService commuteService;
   private final MemberService memberService;
   private final EmployeeInfoService emplInfoService;
   private final MemShopMapRepository mapRepository;
   private final ShopInfoService shopInfoService;
   private final PayListService payListService;
   private final CommuteRepository commuteRepository;
   
   // 사이드바 프로필정보 가져옴
   public Model getSideImg(Model model, Principal principal) {
      MemberFormDto memberFormDto = memberService.getIdImgUrl(principal.getName());
      return model.addAttribute("member", memberFormDto);
   }

   // 직원정보폼 불러오기
   @GetMapping(value = "/manage/employeeInfo")
   public String employeeInfo(Model model, Principal principal) {
      getSideImg(model, principal);

      // USER_ID로 가지고 있는 매장 리스트 뽑기
      List<MemShopMappingDto> myShopList = emplInfoService.getMyShopList(principal.getName());

      model.addAttribute("myShopList", myShopList); // 소유 중인 매장 이름 리스트를 가져옴
      model.addAttribute("shopDto", new ShopDto()); // 매장 전체 정보를 가지고 있는 DTO

      return "manage/employeeInfoForm";
   }

   // 직원정보 불러오기
   @GetMapping(value = "/manage/employeeInfo/{shopId}")
   public String emplInfoDtl(@PathVariable("shopId") Long shopId, Model model, Principal principal) {
         getSideImg(model, principal);
         
         // USER_ID로 가지고 있는 매장 리스트 뽑기
         List<MemShopMappingDto> myShopList = emplInfoService.getMyShopList(principal.getName());
         model.addAttribute("myShopList", myShopList); // 매장 이름 리스트를 가져옴
         
         // 매장 선택한 후 매장 데이터 가져오기
         Shop shop = shopInfoService.findShop(shopId);
         ShopDto shopDto = shopInfoService.getShop(shop);
         model.addAttribute("shopDto", shopDto);
         
         // 직원 리스트 뽑아오기 
         List<MemShopMappingDto> emplList = emplInfoService.getMappingList(shopId);
         model.addAttribute("emplList", emplList);
         model.addAttribute("updateMappingDto", new MemShopMappingDto());
         
      return "manage/employeeInfoForm";
   }
   

   // 매니저 출근관리 화면
   @GetMapping(value = "/commute")
   public String commute(Model model, Principal principal) {

      List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());

      getSideImg(model, principal);
      model.addAttribute("myShopList", myShopList);
      
      model.addAttribute("shopDto", new ShopDto()); // 매장 전체 정보를 가지고 있는 DTO
      
      return "manage/managerCommuteForm";
   }

   // 매니저 급여관리 화면
   @GetMapping(value = "/commute/{shopId}")
   public String getCommuteByShop(@PathVariable("shopId") Long shopId, Model model, Principal principal) {
      
      getSideImg(model, principal);
      
      //매니저 아이디로 소유중인 매장 목록 띄우기
      List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());
      model.addAttribute("myShopList", myShopList);
      
      Shop shop = shopInfoService.findShop(shopId);
      ShopDto shopDto = shopInfoService.getShop(shop);
      model.addAttribute("shopDto", shopDto);

      
      //전체 직원 근태 리스트
      List<Commute> commuteList = commuteService.getCommuteListByShop(shopId);
      model.addAttribute("commuteList", commuteList);
      return "manage/managerCommuteForm";
   }
      
   // 매니저 출근관리 화면
   @GetMapping(value = "/pay")
   public String pay(Model model, Principal principal) {
      getSideImg(model, principal);

      List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());
      model.addAttribute("myShopList", myShopList);
      
      return "manage/managerPayForm";
   }
   
   @GetMapping(value = "/pay/{shopId}/{month}")
   public String getPayListByMonth(@PathVariable("shopId") Long shopId, @PathVariable("month") int month, Model model, Principal principal) {
      
      try {
         getSideImg(model, principal);
         
         //매니저 아이디로 소유중인 매장 목록 띄우기
         List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());
         model.addAttribute("myShopList", myShopList);   //사용자가 가진 매장 리스트
         
         List<PayListDto> payListDto = payListService.getPayListByMonth(shopId, month);         
         model.addAttribute("payListDto", payListDto);
         
      } catch(Exception e) {
         e.printStackTrace();
         return "manage/managerPayForm";
      }
      return "manage/managerPayForm";
   }
   
   // 직원 상태 변경 
      @PostMapping(value = "/manage/emplStatus/{mapId}/update")
      public String updateWorkStatus(@PathVariable("mapId") Long mapId, @Valid MemShopMappingDto statusUpdateDto, BindingResult bindingResult, Model model, Principal principal) {
         
         if (bindingResult.hasErrors()) {
            List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());
            
            getSideImg(model, principal);
            model.addAttribute("myShopList", myShopList);
            return "manage/employeeInfo";
         }

         // 현재 해당 mapId를 가지고 있는 연관매핑, 매장, 멤버 엔티티를 가져옴 
         MemShopMapping memShopMapping = emplInfoService.findMapping(mapId);
         memShopMapping.setWorkStatus(memShopMapping.getWorkStatus());
         memShopMapping.setPartTime(memShopMapping.getPartTime());
         memShopMapping.setTimePay(memShopMapping.getTimePay());
         Shop shop = shopService.findShop(memShopMapping.getShop().getId());
         Member member = emplInfoService.findEmplMember(memShopMapping.getMember().getId());

         try {
            if(memShopMapping.getWorkStatus() == 1) {
               emplInfoService.updateStatus(mapId, statusUpdateDto, member, shop);
            } else if (memShopMapping.getWorkStatus() == 2) {
                  emplInfoService.updateStatus(mapId, statusUpdateDto, member, shop);
            } else if (memShopMapping.getWorkStatus() == 3) {
               emplInfoService.updateStatus(mapId, statusUpdateDto, member, shop);
            }

         } catch (Exception e) {
            model.addAttribute("errorMessage", "상태를 변경하지 못했습니다..");

            return "redirect:/admin/manage/employeeInfo/" + shop.getId();      
         }
         
         return "redirect:/admin/manage/employeeInfo/" + shop.getId();
      }

   // 직원 정보 수정
   @PostMapping(value = "/manage/employeeInfo/{mapId}/update")
   public String updateEmployeeInfo(@PathVariable("mapId") Long mapId, @Valid MemShopMappingDto updateMappingDto, BindingResult bindingResult, Model model, Principal principal ) {
      
      if (bindingResult.hasErrors()) {
         List<MemShopMappingDto> myShopList = shopService.getMyShop(principal.getName());
         
         getSideImg(model, principal);
         model.addAttribute("myShopList", myShopList);
         return "manage/employeeInfo";
      }
      
      MemShopMapping memShopMapping = emplInfoService.findMapping(mapId);
      memShopMapping.setWorkStatus(memShopMapping.getWorkStatus());
      memShopMapping.setPartTime(updateMappingDto.getPartTime());
      memShopMapping.setTimePay(updateMappingDto.getTimePay());
      Shop shop = shopService.findShop(memShopMapping.getShop().getId());
      Member member = emplInfoService.findEmplMember(memShopMapping.getMember().getId());
      
      try {
         emplInfoService.updateEmplInfo(mapId, updateMappingDto, member, shop);
      } catch (Exception e) {
         model.addAttribute("errorMessage", "직원 정보 수정 중 에러가 발생했습니다.");
         
         return "redirect:/admin/manage/employeeInfo/" + shop.getId();
      }
      
      return "redirect:/admin/manage/employeeInfo/" + shop.getId();
   }
   
   // 직원 정보 삭제
   @DeleteMapping(value = "/manage/employeeInfo/{mapId}/delete")
   public @ResponseBody ResponseEntity deleteEmployee(@PathVariable("mapId") Long mapId, Principal principal) {
      MemShopMapping memShopMapping = emplInfoService.findMapping(mapId);
      emplInfoService.deleteEmployee(memShopMapping);
      return new ResponseEntity<Long>(mapId, HttpStatus.OK);
   }
   
   // 엑셀 다운로드
      @GetMapping("/manage/employeeInfo/{shopId}/excelDownload")
      public void downloadExcel(@PathVariable("shopId") Long shopId, HttpServletResponse response, Principal principal) throws IOException {

         Workbook workbook = new HSSFWorkbook();
         Sheet sheet = workbook.createSheet("직원 정보");
         int rowNo = 0;

         Row headerRow = sheet.createRow(rowNo++);
         headerRow.createCell(0).setCellValue("이름");
         headerRow.createCell(1).setCellValue("전화번호");
         headerRow.createCell(2).setCellValue("근무시간");
         headerRow.createCell(3).setCellValue("시급");

         List<MemShopMapping> list = mapRepository.findByShopId(shopId);

         for (MemShopMapping memShopMapping : list) {
            if(memShopMapping.getWorkStatus() == 2) {
               Row row = sheet.createRow(rowNo++);
               row.createCell(0).setCellValue(memShopMapping.getMember().getUserNm());
               row.createCell(1).setCellValue(memShopMapping.getMember().getPNum());
               row.createCell(2).setCellValue(memShopMapping.getPartTime());
               row.createCell(3).setCellValue(memShopMapping.getTimePay());
            }
         }

         response.setContentType("ms-vnd/excel");
         response.setHeader("Content-Disposition", "attachment;filename=employeeList.xls");

         workbook.write(response.getOutputStream());
         workbook.close();
      }
      
      // 엑셀 다운로드
      @GetMapping("/commute/{shopId}/excelDownload")
      public void downloadCommuteListExcel(@PathVariable("shopId") Long shopId, HttpServletResponse response, Principal principal) throws IOException {

         Workbook workbook = new HSSFWorkbook();
         Sheet sheet = workbook.createSheet("직원 출퇴근 정보");
         int rowNo = 0;

         Row headerRow = sheet.createRow(rowNo++);
         headerRow.createCell(0).setCellValue("이름");
         headerRow.createCell(1).setCellValue("출근시간");
         headerRow.createCell(2).setCellValue("퇴근시간");
         headerRow.createCell(3).setCellValue("전화번호");

         List<Commute> list = commuteRepository.findByShopId(shopId);

         for (Commute commute : list) {
            Row row = sheet.createRow(rowNo++);
            row.createCell(0).setCellValue(commute.getMember().getUserNm());
            row.createCell(1).setCellValue(commute.getWorking());
            row.createCell(2).setCellValue(commute.getLeaving());
            row.createCell(3).setCellValue(commute.getMember().getPNum());
         }

         response.setContentType("ms-vnd/excel");
         response.setHeader("Content-Disposition", "attachment;filename=commuteList.xls");

         workbook.write(response.getOutputStream());
         workbook.close();
      }

}