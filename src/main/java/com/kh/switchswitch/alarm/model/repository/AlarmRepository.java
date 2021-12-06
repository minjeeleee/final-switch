package com.kh.switchswitch.alarm.model.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.switchswitch.alarm.model.dto.Alarm;

@Mapper
public interface AlarmRepository {

	@Insert("insert into alarm values(sc_alarm_idx.nextval, #{senderIdx}, #{receiverIdx}, #{alarmType}, 0, #{reqIdx}, sysdate)")
	void insertAlarm(Alarm alarm);

	@Update("update alarm set is_read=#{isRead} where alarm_idx=#{alarmIdx}")
	void updateAlarmIsRead(Alarm alarm);

	@Select("select sc_alarm_idx.currval from dual")
	Integer selectCurrScAlarmIdx();

	@Select("select count(*) from alarm")
	Integer selectAlarmCnt();

	@Select("select * from (select rownum rnum, alarm.* from alarm order by is_read, alarm_idx desc) alarm"
			+ " where (rnum between #{startAlarm} and #{lastAlarm}) and receiver_idx=#{receiverIdx} and sysdate < send_date + 7 ")
	List<Alarm> selectAlarmListWithReceiverIdx(Map<String, Integer> map);

}
