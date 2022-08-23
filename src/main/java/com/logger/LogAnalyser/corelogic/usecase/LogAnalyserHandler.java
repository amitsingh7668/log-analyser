package com.logger.LogAnalyser.corelogic.usecase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		Map<LogEvent, LogEvent> mp = new HashMap<LogEvent, LogEvent>();
		List<LogEvent> loglist = new ArrayList<LogEvent>();
		List<AlertEntity> alertList = new ArrayList<AlertEntity>();
		List<AlertEntity> alertListPerm = new ArrayList<AlertEntity>();

		LOGGER.info("Parsing the events and persisting the alerts. This may take a while...");
		try {
			// Note :- Override file as per request
			LineIterator li = FileUtils.lineIterator(new ClassPathResource("samples/" + "logfile2.txt").getFile());

			String line = null;
			while (li.hasNext()) {
				LogEvent event = new ObjectMapper().readValue(li.nextLine(), LogEvent.class);
				loglist.add(event);
			}

			LOGGER.info("Fetching all logs from the file and putting together into the list ");
			for (LogEvent logl1 : loglist) {
				String id = logl1.getId();
				if (!mp.containsKey(id)) {
					for (LogEvent logl2 : loglist) {
						if (logl2 != logl1 && logl2.getId().equals(logl1.getId())) {
							int eve = (int) Math.abs(logl2.getTimestamp() - logl1.getTimestamp());
							if (eve > longevent)
								alertList.add(
										new AlertEntity(logl1.getId(), eve, logl1.getType(), logl1.getHost(), true));

						}
					}

				}
				if (alertList.size() == 100) {
					alertListPerm.addAll(alertList);
					if (dbCall) {
						LOGGER.info("Record pushed into Db count :- " +alertListPerm.size());
						alertRepository.saveAll(alertList);
					}
					alertList.clear();
				}
			}
			if (dbCall) {
				alertRepository.saveAll(alertList);
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
		return sr;

	}

}