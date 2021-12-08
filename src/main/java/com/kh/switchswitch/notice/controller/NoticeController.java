package com.kh.switchswitch.notice.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.switchswitch.member.model.dto.MemberAccount;
import com.kh.switchswitch.notice.model.dto.Notice;
import com.kh.switchswitch.notice.model.service.NoticeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("notice")
public class NoticeController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final NoticeService noticeService;
	@GetMapping("notice-form")
	public void noticeForm() {}
	
	@PostMapping("upload")
	public String uploadNotice(Notice notice,  @AuthenticationPrincipal MemberAccount member ) {
		notice.setUserId(member.getCode());
		noticeService.insertNotice(notice);
		return "redirect:/notice/notice-list2";
	}
	
	  @GetMapping("notice-list2") 
	  public String noticeList(Model model, @RequestParam(required = true, defaultValue = "1") int page) {
		  model.addAllAttributes(noticeService.selectNoticeList(page));
			return "notice/notice-list2";
	}
	  @GetMapping("notice-detail")
	public void noticeDetail(int noticeIdx, Model model) {
		Map<String,Object> commandMap = noticeService.selectNoticeByIdx(noticeIdx);
		model.addAttribute("datas", commandMap);
	}
		@GetMapping("notice-modify")
		public void noticeModify(Notice notice,Model model, Integer noticeIdx,@AuthenticationPrincipal MemberAccount member) {
			Map<String,Object> commandMap = noticeService.selectNoticeByIdx(noticeIdx);
			notice.setUserId(member.getCode());
			model.addAttribute("datas", commandMap);
		}
		

		@PostMapping("modify")
		public String modifyNotice(Notice notice, Integer noticeIdx,@AuthenticationPrincipal MemberAccount member) {
			notice.setUserId(member.getCode());
			notice.setNoticeIdx(noticeIdx);
			noticeService.modifyNotice(notice);
			return "redirect:/notice/notice-detail?noticeIdx="+notice.getNoticeIdx();
		}

		@GetMapping("delete")
		public String deleteNotice(Notice notice, @RequestParam Integer noticeIdx,@AuthenticationPrincipal MemberAccount member, RedirectAttributes redirectAttrs) {

				notice.setUserId(member.getCode());
				noticeService.deleteNotice(noticeIdx);
				redirectAttrs.addFlashAttribute("message", "게시글 삭제가 완료되었습니다."); //수정필요
			return "redirect:/notice/notice-list2"; 
		}

		
}
