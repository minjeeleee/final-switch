package com.kh.switchswitch.admin.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.kh.switchswitch.admin.model.dto.Code;
import com.kh.switchswitch.admin.model.dto.Menu;
import com.kh.switchswitch.admin.model.repository.AdminRepository;
import com.kh.switchswitch.card.model.dto.Card;
import com.kh.switchswitch.common.code.ErrorCode;
import com.kh.switchswitch.common.exception.HandlableException;
import com.kh.switchswitch.common.util.FileDTO;
import com.kh.switchswitch.member.model.dto.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	@Autowired
	private final AdminRepository adminRepository;
	private final PasswordEncoder passwordEncoder;

	public Map<String, Object> selectRealTimeCards() {
		List<Card> cardList = adminRepository.selectRealTimeCards();
		List<FileDTO> cardImgList = adminRepository.selectCardImgList();
		return Map.of("cardList", cardList, "cardImgList", cardImgList);
	}

	public int deleteCard(int cardIdx) {
		return adminRepository.deleteCard(cardIdx);
	}

	public void insertMenu(Menu menu) {
		adminRepository.insertMenu(menu);
	}

	public List<Menu> selectMenuList() {
		return adminRepository.selectMenuList();
	}

	public List<Menu> selectSideMenu() {
		return adminRepository.selectSideMenu();
	}

	public List<Code> selectCodeList() {
		List<Code> codeList = adminRepository.selectCodeList();
		return codeList;
	}

	public List<Menu> selectParentsMenuListByMenu() {
		List<Menu> parentsMenuList = adminRepository.selectParentsMenuListByMenu();
		return parentsMenuList;
	}

	public List<Menu> selectParentsMenuListBySideMenu() {
		List<Menu> parentsMenuList = adminRepository.selectParentsMenuListBySideMenu();
		return parentsMenuList;
	}

	public void deleteMenu(int urlIdx) {
		adminRepository.deleteMenu(urlIdx);
	}

	public List<Menu> selectChildMenu(String parent) {
		return adminRepository.selectChildMenu(parent);
	}

	public List<Menu> selectMenuAllList() {
		return adminRepository.selectMenuAllList();
	}

	public List<Member> selectMemberAllList() {
		return adminRepository.selectMemberAllList();
	}

	public void updateMemberCode(String code, int memberIdx) {
		code = code.equals("B") ? "D" : "B";
		System.out.println(code);
		adminRepository.updateMemberCode(code, memberIdx);
	}

	public List<Member> selectMemberBlackList() {
		return adminRepository.selectMemberBlackList();
	}

	public Map<String, Object> selectMemberByIdx(int memberIdx) {
		Member memberInfo = adminRepository.selectMemberByIdx(memberIdx);
		if(memberInfo.getFlIdx() != null) {
			FileDTO memberImg = adminRepository.selectFileByMemberIdx(memberInfo.getFlIdx());
			return Map.of("member", memberInfo, "memberImg",memberImg);
		}
		return Map.of("member", memberInfo);
	}

	public void updateMemberInfo(Member convertToMember, Integer memberIdx) {
		convertToCheckNull(convertToMember);
		if (convertToMember.getMemberPass() != null) {
			convertToMember.setMemberPass(passwordEncoder.encode(convertToMember.getMemberPass()));
		}
		
		if(nullCheckForMember(convertToMember)) {
			throw new HandlableException(ErrorCode.FAILED_TO_UPDATE_INFO);
		}
		convertToMember.setMemberIdx(memberIdx);
		
		adminRepository.updateMemberInfo(convertToMember);
	}
	
	public boolean nullCheckForMember(Member member) {
		boolean pass = StringUtils.isEmpty(member.getMemberPass());
		boolean nick = StringUtils.isEmpty(member.getMemberNick());
		boolean tell = StringUtils.isEmpty(member.getMemberTell());
		boolean address = StringUtils.isEmpty(member.getMemberAddress());
		if(pass&&nick&&tell&&address) return true;
		return false;
	}
	
	public Member convertToCheckNull(Member member) {
		if(member.getMemberAddress().equals("[] ")) member.setMemberAddress(null);
		//StringUtils.isEmpty(member.getMemberAddress());
		return member;
	}

	public String checkNickName(String nickName) {
		if (adminRepository.selectMemberByNickName(nickName) == null) {
			return nickName;
		}
		return null;
	}

	public void deleteMember(Integer memberIdx) {
		adminRepository.deleteMember(memberIdx);
	}

	public void deleteMemberProfileImg(Integer flIdx) {
		adminRepository.deleteMemberProfileImg(flIdx);
	}

}
