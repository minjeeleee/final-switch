package com.kh.switchswitch.common.schedule;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@EnableScheduling
public class Schedule {
	
	@Scheduled(cron = "0 0 0/1 * * *")
	public void scheduling() {
		
	}

}
