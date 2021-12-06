package com.kh.switchswitch.inquiry.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.switchswitch.inquiry.model.dto.Inquiry;
import com.kh.switchswitch.inquiry.model.service.InquiryService;
import com.kh.switchswitch.member.model.dto.MemberAccount;
import com.kh.switchswitch.notice.model.dto.Notice;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("inquiry")
public class InquiryController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final InquiryService inquiryService;
	
	@GetMapping("inquiry-form")
	public void inquiryForm() {}

	@PostMapping("upload")
	public String uploadInquiry(Inquiry inquiry, @AuthenticationPrincipal MemberAccount member ) {
		inquiry.setUserId(member.getMemberNick());
		inquiryService.insertInquiry(inquiry);
		return "redirect:/inquiry/inquiry-detail?inquiryIdx="+inquiry.getInquiryIdx();
	}

	  @GetMapping("inquiry-list2") 
	  public String inquiryList(Model model, @RequestParam(required = true, defaultValue = "1") int page) {
		  model.addAllAttributes(inquiryService.selectInquiryList(page));
			return "inquiry/inquiry-list2";
	}

	
	  @GetMapping("inquiry-detail")
	public void inquiryDetail(int inquiryIdx, Model model) {
		Map<String,Object> commandMap = inquiryService.selectInquiryByIdx(inquiryIdx);
		model.addAttribute("datas", commandMap);
	}
		@GetMapping("inquiry-modify")
		public void inquiryModify(Model model, int inquiryIdx) {
			Map<String,Object> commandMap = inquiryService.selectInquiryByIdx(inquiryIdx);
			model.addAttribute("datas", commandMap);
		}
		

		@PostMapping("modify")
		public String modifyinquiry(Inquiry inquiry, int inquiryIdx) {
			inquiry.setInquiryIdx(inquiryIdx);
			inquiryService.modifyInquiry(inquiry);
			return "redirect:/inquiry/inquiry-detail?inquiryIdx="+inquiry.getInquiryIdx();
		}

		@PostMapping("delete")
		public String deleteInquiry(int inquiryIdx) {
			inquiryService.deleteInquiry(inquiryIdx);
			return "redirect:/";
		}

}
