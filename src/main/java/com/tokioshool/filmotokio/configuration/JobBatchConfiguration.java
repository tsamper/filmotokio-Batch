package com.tokioshool.filmotokio.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.tokioshool.filmotokio.domain.Film;
import com.tokioshool.filmotokio.listener.FilmWriteListener;
import com.tokioshool.filmotokio.listener.JobCompletionNotificationListerner;
import com.tokioshool.filmotokio.processor.FilmItemProcessor;
import com.tokioshool.filmotokio.reader.FilmItemReader;

@Configuration
@EnableBatchProcessing
public class JobBatchConfiguration {

	@Value("${database.url}")
	private String databaseUrl;
	@Value("${database.username}")
	private String databaseUsername;
	@Value("${database.password}")
	private String databasePassword;
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	private final Logger logger = LoggerFactory.getLogger(JobBatchConfiguration.class);
	
	@Bean
	public FilmItemProcessor processor() {
		return new FilmItemProcessor();
	}
	
	@Bean 
	public FilmItemReader reader() {
		return new FilmItemReader();
	}
	
	@Bean 
	public Job job(JobCompletionNotificationListerner jobListener, Step stepFile) {
		return jobBuilderFactory.get("importFilmsJob")
				.listener(jobListener)
				.flow(stepFile)
				.end()
				.build();
	}
	
	@Bean 
	public FlatFileItemWriter<Film> writer(){
		logger.info("preparing writer");
		return new FlatFileItemWriterBuilder<Film>()
				.name("FilmItemWriter")
				.resource(new FileSystemResource("C:\\Users\\PcCom1\\SpringBatchFilmo\\SpringBatch.csv"))
				.append(true)
				.delimited().delimiter(", ")
				.names(new String[]{"id","title","year","duration","synopsis","poster","migrate","dateMigrate"})
				.build();
	}
	
	@Bean 
	public Step stepFile(FilmItemReader reader,
					FlatFileItemWriter<Film> writer,
					FilmItemProcessor processor,
					FilmWriteListener filmListener) {
		return stepBuilderFactory.get("step")
				.<Film, Film> chunk(10)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.listener(filmListener)
				.build();
	}
}
