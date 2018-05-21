package com.aerodynamic.design.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.aerodynamic.design.controller.AdminController;

@Configuration
@EnableScheduling
public class SchedulingConfig {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Scheduled(fixedRate = 60000) // 每60秒执行一次
    public void getToken() {
		AdminController.checkContext();
    }

}
