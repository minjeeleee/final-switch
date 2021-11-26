package com.kh.switchswitch.admin.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.switchswitch.admin.model.dto.Menu;
import com.kh.switchswitch.admin.model.service.AdminService;
import com.kh.switchswitch.common.validator.ValidatorResult;
import com.kh.switchswitch.member.model.dto.Member;
import com.kh.switchswitch.member.model.dto.MemberAccount;
import com.kh.switchswitch.member.model.service.MemberService;
import com.kh.switchswitch.mypage.validator.ModifyForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminController {
	
	private final AdminService adminService;
	
	@GetMapping("main")
	public void main() {}
	
	@GetMapping("real-time-cards")
	public void realTimeCards(Model model) {
		Map<String,Object> cardList = adminService.selectRealTimeCards();
		model.addAttribute("cardList",cardList);
	}
	
	@GetMapping("all-cards")
	public void allCards() {
	}
	
	@RequestMapping("card-delete")
	public String cardDelete(int cardIdx) {
		adminService.deleteCard(cardIdx);
		return "admin/real-time-cards";
	}
	
	@GetMapping("all-members")
	public void allMembers(Model model) {
		List<Member> memberList = adminService.selectMemberAllList();
		model.addAttribute("memberList",memberList);
	}
	
	@GetMapping("changeMemberStatus")
	public String changeMemberStatus(@RequestParam String code, @RequestParam int memberIdx) {
		adminService.updateMemberCode(code,memberIdx);
		return "redirect:/admin/all-members";
	}
	
	@GetMapping("black-list-members")
	public void blackListMembers(Model model) {
		List<Member> memberList = adminService.selectMemberBlackList();
		model.addAttribute("memberList",memberList);
	}
	
	@GetMapping("removeMemberBlack")
	public String removeMemberBlack(@RequestParam String code, @RequestParam int memberIdx) {
		adminService.updateMemberCode(code,memberIdx);
		return "redirect:/admin/black-list-members";
	}
	
	@GetMapping("refunds-history")
	public void refundsHistory() {}
	
	@GetMapping("member-profile")
	public void memberProfile(@RequestParam int memberIdx, Model model) {
		Map<String, Object> memberInfo = adminService.searchDetailMemberProfile(memberIdx);
		model.addAttribute("memberInfo",memberInfo);
	}
	
	@GetMapping("member-profile-edit")
	public void memberProfileEdit(@RequestParam int memberIdx, Model model) {
		Map<String, Object> memberInfo = adminService.searchDetailMemberProfile(memberIdx);
		model.addAttribute("memberInfo",memberInfo);
		model.addAttribute(new ModifyForm()).addAttribute("error", new ValidatorResult().getError());
	}
	
	@PostMapping("member-profile-edit-success")
	public String memberProfileEditSuccess(@Validated ModifyForm form,
										Model model,
										@RequestParam int memberIdx,
										Errors errors,
										RedirectAttributes redirectAttr) {
		System.out.println(form);
		ValidatorResult vr = new ValidatorResult();
		model.addAttribute("error", vr.getError());
		if(errors.hasErrors()) {
			vr.addErrors(errors);
			return "redirect:/admin/member-profile-edit?memberIdx="+memberIdx;
		}
		//adminService.updateMemberInfo(form.convertToMember(),memberIdx);
		return "redirect:/admin/member-profile?memberIdx="+memberIdx;
	}
	
	@GetMapping("nick-check")
	@ResponseBody
	public String nickCheck(String nickName) {
		
		if(nickName.equals(adminService.checkNickName(nickName))) {
			return "available";
		}else {
			return "disable";
		}
	}
	
	@GetMapping("page-setting")
	public void pageSetting(Model model) {
		//주메뉴
		Map<String,List<Menu>> childMenuList = new LinkedHashMap<String, List<Menu>>();
		List<Menu> parentMenu = adminService.selectMenuList();
		model.addAttribute("menuList",adminService.selectMenuList());
		for (Menu menu : parentMenu) {
			String parent = menu.getUrlName();
			childMenuList.put(parent, adminService.selectChildMenu(parent));
		}
		model.addAttribute("childMenuList",childMenuList);
		
		//사이드메뉴
		Map<String,List<Menu>> childSideMenuList = new LinkedHashMap<String, List<Menu>>();
		List<Menu> sideParentMenu = adminService.selectSideMenu();
		model.addAttribute("sideMenuList",sideParentMenu);
		for (Menu menu : sideParentMenu) {
			String parent = menu.getUrlName();
			childSideMenuList.put(parent,adminService.selectChildMenu(parent));
		}
		model.addAttribute("childSideMenuList",childSideMenuList);
		
		//목록 및 페이지 추가 팝업 view
		model.addAttribute("menuAllList", adminService.selectMenuAllList());
		model.addAttribute("parentsMenuList",adminService.selectParentsMenuListByMenu());
		model.addAttribute("parentsSideMenuList",adminService.selectParentsMenuListBySideMenu());
		model.addAttribute("codeList",adminService.selectCodeList());
	}
	
	@PostMapping("add-menu")
	public String addPage(Menu menu) {
			if(menu.getParent().equals("없음")) {
				menu.setDepth(1);
				adminService.insertMenu(menu);
			}else {
				menu.setDepth(2);
				adminService.insertMenu(menu);
			}
		return "redirect:/admin/page-setting";
	}
	
	@GetMapping("delete-menu")
	public String deleteMenu(int urlIdx) {
		adminService.deleteMenu(urlIdx);
		return "redirect:/admin/page-setting";
	}
	
}
