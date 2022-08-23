package com.logger.LogAnalyser.adapter.restcontroller;

import java.io.IOException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logger.LogAnalyser.corelogic.gateway.AlertEntity;
import com.logger.LogAnalyser.corelogic.gateway.AlertRepository;
import com.logger.LogAnalyser.corelogic.models.SuccessResponse;
import com.logger.LogAnalyser.corelogic.usecase.LogAnalyserHandler;

@RestController
@RequestMapping("/v1")
public class LoggerController {
	
	
	@Autowired
	LogAnalyserHandler logAnalyserHandler;
	
	@Autowired
	AlertRepository alertRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggerController.class);
	
	@GetMapping(value = { "/alerts" })
	public SuccessResponse AllAlerts() throws InterruptedException, IOException {
		LOGGER.info("Controller Layer - Fetching all the alert from the logs... ");
		return logAnalyserHandler.handle(true);
	}
}
