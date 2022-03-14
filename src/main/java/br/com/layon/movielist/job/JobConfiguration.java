package br.com.layon.movielist.job;

import br.com.layon.movielist.entity.Movie;
import br.com.layon.movielist.interfaces.MovieFieldSetMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JobConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Bean
    public FlatFileItemReader<Movie> movieItemReader() {
        FlatFileItemReader<Movie> reader = new FlatFileItemReader<>();
        reader.setLinesToSkip(1);
        reader.setResource(new ClassPathResource("/data/movielist.csv"));

        DefaultLineMapper<Movie> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(";");
        tokenizer.setNames("year", "title", "studios", "producers", "winner");

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new MovieFieldSetMapper());
        lineMapper.afterPropertiesSet();
        reader.setLineMapper(lineMapper);

        return reader;
    }

//    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public JdbcBatchItemWriter<Movie> movieItemWriter() {
        JdbcBatchItemWriter<Movie> itemWriter = new JdbcBatchItemWriter<>();

        itemWriter.setDataSource(this.dataSource);
        itemWriter.setSql("insert into movielist (year, title, studios, producers, winner) values (:year, :title, :studios, :producers, :winner)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
        itemWriter.afterPropertiesSet();

        return itemWriter;
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
                .<Movie, Movie>chunk(10)
                .reader(movieItemReader())
                .writer(movieItemWriter())
                .build();
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .start(step())
                .build();
    }
}
