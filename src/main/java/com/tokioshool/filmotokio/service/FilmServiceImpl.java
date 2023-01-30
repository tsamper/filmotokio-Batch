package com.tokioshool.filmotokio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.tokioshool.filmotokio.domain.Film;

@Service
public class FilmServiceImpl implements FilmService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void update(Film film, long id) {
		jdbcTemplate.update("UPDATE films SET migrate = ?, date_migrate = ? WHERE id = ?", new Object[] {film.isMigrate(), film.getDateMigrate(), id});
	}
}
