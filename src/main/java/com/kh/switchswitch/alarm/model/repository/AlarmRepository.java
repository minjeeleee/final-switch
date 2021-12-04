package com.kh.switchswitch.alarm.model.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.switchswitch.alarm.model.dto.Alarm;

@Mapper
public interface AlarmRepository {

	@Select("select * from alarm where receiver_idx=#{receiverIdx} and sysdate < send_date + 7 order by is_read desc, alarm_idx desc")
	List<Alarm> selectAlarmList(Integer receiverIdx);

	@Insert("insert into alarm values(sc_alarm_idx.nextval, #{senderIdx}, #{receiverIdx}, #{alarmType}, 0, #{reqIdx})")
	void insertAlarm(Alarm alarm);

	@Update("update alarm set is_read=#{isRead} where alarm_idx=#{alarmIdx}")
	void updateAlarmIsRead(Alarm alarm);

	@Select("select sc_alarm_idx.currval from dual")
	Integer selectCurrScAlarmIdx();
	
	@Select("select * from alarm where is_read = 0 and sysdate < send_date + 7 order by is_read desc, alarm_idx desc")
	List<Alarm> selectUnreadAlarmList();
	
	@Select("select * from alarm where sysdate < send_date + 7 order by is_read desc, alarm_idx desc")
	List<Alarm> selectRecentAlarmList();

}
