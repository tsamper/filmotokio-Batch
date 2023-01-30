package com.tokioshool.filmotokio.reader;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import com.tokioshool.filmotokio.domain.Film;
import com.tokioshool.filmotokio.repository.FilmRepository;

public class FilmItemReader implements ItemReader<Film> {
	
	private static final Logger logger = LoggerFactory.getLogger(FilmItemReader.class);
	
	@Autowired
	private FilmRepository filmRepository;
	
	private Iterator<Film> filmIterator;
	
	@BeforeStep
	public void before(StepExecution stepExecution) {
		filmIterator=filmRepository.findAll().iterator();
		logger.info("Método before de FilmItemREader");
	}

	@Override
	public Film read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		Film film = null;
		if(filmIterator !=null && filmIterator.hasNext()) {
			film = filmIterator.next();
		}
		return film;
	}
}
