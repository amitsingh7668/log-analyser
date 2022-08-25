package com.logger.LogAnalyser.corelogic.usecase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.catalina.Executor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logger.LogAnalyser.corelogic.gateway.AlertEntity;
import com.logger.LogAnalyser.corelogic.gateway.AlertRepository;
import com.logger.LogAnalyser.corelogic.models.LogEvent;
import com.logger.LogAnalyser.corelogic.models.SuccessResponse;

@Component
public class LogAnalyserHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(LogAnalyserHandler.class);

	@Value("${longevent}")
	int longevent;

	@Autowired
	AlertRepository alertRepository;

	public SuccessResponse handle(boolean dbCall) throws InterruptedException, IOException {

		SuccessResponse sr = new SuccessResponse();
		List<LogEvent> loglist = new ArrayList<LogEvent>();
		Set<AlertEntity> alertListPerm = new HashSet<AlertEntity>();

		LOGGER.info("Parsing the events and persisting the alerts. This may take a while...");
		try {
			// Note :- Override file as per request
			LineIterator li = FileUtils.lineIterator(new ClassPathResource("samples/" + "logfile2.txt").getFile());

			while (li.hasNext()) {
				LogEvent event = new ObjectMapper().readValue(li.nextLine(), LogEvent.class);
				loglist.add(event);
			}

			LOGGER.info("Fetching all logs from the file and putting together into the list ");
			ExecutorService es = Executors.newFixedThreadPool(10);
			List<Future<AlertEntity>> futureList = new ArrayList<Future<AlertEntity>>();
			for (LogEvent logl1 : loglist) {
				futureList.add(es.submit(new Callable<AlertEntity>() {
					@Override
					public AlertEntity call() throws Exception {
						return extractService(dbCall, loglist, logl1);
					}
				}));

			}

			es.shutdown();
			es.awaitTermination(10, TimeUnit.SECONDS);
			if (dbCall) {
				for (Future<AlertEntity> f : futureList) {
					if (f.get().getId() != null) {
						alertRepository.save(f.get());
						alertListPerm.add(f.get());
					}
				}
			}
			else
			{
				for (Future<AlertEntity> f : futureList) {
					if (f.get().getId() != null) {
						alertListPerm.add(f.get());
					}
				}
			}
			sr.setData(alertListPerm);
			sr.setDate(new Date());
			sr.setHttpstatus(HttpStatus.OK);
			sr.setStatus("SUCCESS");
		} catch (Exception ex) {
			sr.setData(null);
			sr.setDate(new Date());
			sr.setHttpstatus(HttpStatus.BAD_REQUEST);
			sr.setStatus("ERROR");
			sr.setError(ex.getMessage());
			LOGGER.error(ex.getLocalizedMessage());
			LOGGER.error(ex.toString());
		}
		LOGGER.info("Total alert raised :- "+alertListPerm.size());
		return sr;

	}

	private AlertEntity extractService(boolean dbCall, List<LogEvent> loglist, LogEvent logl1) {
		LOGGER.info("Into Invoker ..");
		LOGGER.info("Reference log from :- " + logl1.toString());
		AlertEntity al = new AlertEntity();
		for (LogEvent logl2 : loglist) {
			if (logl2 != logl1 && logl2.getId().equals(logl1.getId())) {
				int eve = (int) Math.abs(logl2.getTimestamp() - logl1.getTimestamp());
				if (eve > longevent) {
					al = new AlertEntity(logl1.getId(), eve, logl1.getType(), logl1.getHost(), true);
				}
			}
		}
		LOGGER.warn(" ******* Alert log for id ******  " + al.toString());
		return al;
	}

}