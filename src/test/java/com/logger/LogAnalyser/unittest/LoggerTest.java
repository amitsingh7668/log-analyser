package com.logger.LogAnalyser.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.*;

import com.logger.LogAnalyser.corelogic.gateway.AlertEntity;
import com.logger.LogAnalyser.corelogic.models.EventAlert;
import com.logger.LogAnalyser.corelogic.usecase.LogAnalyserHandler;

public class LoggerTest {
	
	LogAnalyserHandler li = new LogAnalyserHandler();
	List<AlertEntity> eventlist = new ArrayList<AlertEntity>();
	
	@SuppressWarnings("unchecked")
	@BeforeEach
	public void init() throws InterruptedException, IOException
	{
			eventlist= (List<AlertEntity>)li.handle(false).getData();
			
	}
	
	@Test
	public void longerEventAvaialble() throws InterruptedException, IOException
	{
		AlertEntity expectedEvent = new AlertEntity("6d74BDcdBFba1C91",8,"APPLICATION_LOG","95.21.146.39",true);
		AlertEntity actualEvent = eventlist.stream().filter(g->g.getId().equals("6d74BDcdBFba1C91")).findFirst().get();
		assertEquals(expectedEvent,actualEvent );
	}
	
	@Test
	public void longerEventnotAvaialble() throws InterruptedException, IOException
	{
		AlertEntity expectedEvent = new AlertEntity("6d74B34DcdBFba1C91",7,"APPLICATION_LOG","95.146.39",true);
		AlertEntity actualEvent = eventlist.stream().filter(g->g.getId().equals("6d74BDcdBFba1C91")).findFirst().get();
		assertNotEquals(expectedEvent,actualEvent );
	}
	
	@Test
	public void longerEventSize() throws InterruptedException, IOException
	{
		assertEquals(632,eventlist.size());
	}

}
