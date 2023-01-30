package com.tokioshool.filmotokio.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tokioshool.filmotokio.repository.FilmRepository;

@Component
public class JobCompletionNotificationListerner implements JobExecutionListener {
	
	private Logger logger = LoggerFactory.getLogger(JobCompletionNotificationListerner.class);
	
	@Autowired
	private FilmRepository filmRepository;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.info("Before JobCompletetionNotificationListener: Cantidad de películas por migrar: " + filmRepository.findByMigrate(false).size());
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		logger.info("After JobCompletetionNotificationListener: Cantidad de películas por migrar: " + filmRepository.findByMigrate(false).size());
	}
}
