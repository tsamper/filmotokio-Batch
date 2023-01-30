package com.tokioshool.filmotokio.processor;

import java.time.LocalDate;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.tokioshool.filmotokio.domain.Film;
import com.tokioshool.filmotokio.service.FilmService;

public class FilmItemProcessor implements ItemProcessor<Film, Film> {

	@Autowired
	private FilmService filmService;
	
	@Override
	public Film process(Film film) throws Exception {
		if(film.isMigrate()) {
			return null;
		}
		film.setDateMigrate(LocalDate.now());
		film.setMigrate(true);
		filmService.update(film, film.getId());
		return film;
	}

}
