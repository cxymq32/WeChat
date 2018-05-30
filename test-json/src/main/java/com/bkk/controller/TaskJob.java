package com.bkk.controller;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.bkk.common.base.MyRedis;

@Controller
public class TaskJob {
	private static Logger log = Logger.getLogger(TaskJob.class);

	@Scheduled(cron = "*/5 * * * * ?")
	public void deleteAllTempClob() {
		log.info("---->>deleteAllTempClob删除数据库记录数:" + MyRedis.getAllKeys().size());
	}
}
