package com.kh.switchswitch.alarm.model.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.switchswitch.alarm.model.dto.Alarm;

@Mapper
public interface AlarmRepository {

	@Select("select * from alarm where receiver_idx=#{receiverIdx} order by is_read desc, alarm_idx desc")
	List<Alarm> selectAlarmList(String receiverIdx);

	@Insert("insert into alarm values(sc_alarm_idx.nextval, #{senderIdx}, #{receiverIdx}, #{alarmType}, #{isRead}, #{reqIdx})")
	void insertAlarm(Alarm alarm);

	@Update("update alarm set is_read=#{isRead} where alarm_idx=#{alarmIdx}")
	void updateAlarmIsRead(Alarm alarm);

}
