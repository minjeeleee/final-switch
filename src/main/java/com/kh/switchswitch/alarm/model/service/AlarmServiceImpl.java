package com.kh.switchswitch.alarm.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.switchswitch.alarm.model.dto.Alarm;
import com.kh.switchswitch.alarm.model.repository.AlarmRepository;
import com.kh.switchswitch.common.util.pagination.Paging;
import com.kh.switchswitch.member.model.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {
	
	private final AlarmRepository alarmRepository;
	private final MemberRepository memberRepository;
	
	public List<Object> selectAlarmListWithReceiverIdx(Integer receiverIdx, int page) {
		List<Object> pageAndAlarm = new ArrayList<Object>();
		int cntPerPage = 10;
		Paging pageUtil = Paging.builder()
				.url("/mypage/alarm")
				.total(alarmRepository.selectAlarmCnt())
				.curPage(page)
				.blockCnt(5)
				.cntPerPage(cntPerPage)
				.build();
		pageAndAlarm.add(pageUtil);
		List<Map<String, Object>> alarmList = new ArrayList<Map<String,Object>>();
		List<Alarm> alarms = alarmRepository.selectAlarmListWithReceiverIdx(
				(Map<String, Integer>) Map.of("receiverIdx", receiverIdx,"startAlarm",(page-1)*cntPerPage+1,"lastAlarm",(page-1)*cntPerPage+cntPerPage));
		String msg = "";
		for (Alarm alarm : alarms) {
			switch(alarm.getAlarmType()) {
			case "교환요청":
				msg = memberRepository.selectMemberNickByMemberIdx(alarm.getSenderIdx()) + "님으로부터 교환 요청이 왔습니다.";
				break;
			case "요청거절":
				msg = memberRepository.selectMemberNickByMemberIdx(alarm.getSenderIdx()) + "님이 교환 요청을 거절하였습니다.";
				break;
			case "요청수락":
				msg = memberRepository.selectMemberNickByMemberIdx(alarm.getSenderIdx()) + "님이 교환 요청을 수락했습니다.";
				break;
			case "교환확정":
				msg = memberRepository.selectMemberNickByMemberIdx(alarm.getSenderIdx()) + "님이 교환을 확정했습니다.";
				break;
			case "교환취소요청":
				msg = memberRepository.selectMemberNickByMemberIdx(alarm.getSenderIdx()) + "이 교환취소를 요청하였습니다.";
				break;
			case "교환취소":
				msg = "교환취소가 완료되었습니다.";
				break;
			case "평점요청":
				msg = memberRepository.selectMemberNickByMemberIdx(alarm.getSenderIdx())  + "님과의 교환은 어떠셨나요?<br>"
						+ memberRepository.selectMemberNickByMemberIdx(alarm.getSenderIdx()) + "에 대한 평점을 남겨주세요.";
				break;
			}
			alarmList.add(Map.of("alarm", alarm, "message", msg));
		}
		pageAndAlarm.add(alarmList);
		return pageAndAlarm;
	}

	public void insertAndUpdateAlarmList(List<Alarm> alarmList) {
		if(alarmList.size() != 0) {
			for (Alarm alarm : alarmList) {
				//int null 비교하려면 wrapper class 사용
				if(Integer.valueOf(alarm.getAlarmIdx()) == null) {
					alarmRepository.insertAlarm(alarm);
				} else {
					alarmRepository.updateAlarmIsRead(alarm);
				}
			}
		}
		
	}

	public void updateAlarm(Alarm alarm) {
		alarm.setIsRead(1);
		alarmRepository.updateAlarmIsRead(alarm);
	}

}
