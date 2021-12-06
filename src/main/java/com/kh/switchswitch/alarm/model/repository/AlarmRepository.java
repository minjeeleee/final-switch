package com.kh.switchswitch.alarm.model.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.switchswitch.alarm.model.dto.Alarm;

@Mapper
public interface AlarmRepository {

	@Select("select * from alarm where receiver_idx=#{receiverIdx} and sysdate < send_date + 7 order by is_read, alarm_idx desc")
	List<Alarm> selectAlarmListWithReceiverIdx(Integer receiverIdx);

	@Insert("insert into alarm values(sc_alarm_idx.nextval, #{senderIdx}, #{receiverIdx}, #{alarmType}, 0, #{reqIdx}, sysdate)")
	void insertAlarm(Alarm alarm);

	@Update("update alarm set is_read=#{isRead} where alarm_idx=#{alarmIdx}")
	void updateAlarmIsRead(Alarm alarm);

	@Select("select sc_alarm_idx.currval from dual")
	Integer selectCurrScAlarmIdx();

}
