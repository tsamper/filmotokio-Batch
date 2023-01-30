package com.tokioshool.filmotokio.listener;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;
import com.tokioshool.filmotokio.domain.Film;

@Component
public class FilmWriteListener implements ItemWriteListener<Film> {

	private Logger logger = LoggerFactory.getLogger(FilmWriteListener.class);
	
	@Override
	public void beforeWrite(List<? extends Film> items) {
	}

	@Override
	public void afterWrite(List<? extends Film> items) {
		items.stream().forEach(film -> logger.info("Fecha de migración: " + film.getDateMigrate()));
	}

	@Override
	public void onWriteError(Exception exception, List<? extends Film> items) {
		exception.printStackTrace();
	}
}
