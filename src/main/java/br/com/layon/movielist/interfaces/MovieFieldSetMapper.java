package br.com.layon.movielist.interfaces;

import br.com.layon.movielist.entity.Movie;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class MovieFieldSetMapper implements FieldSetMapper<Movie> {
    @Override
    public Movie mapFieldSet(FieldSet fieldSet) {
        Movie m = new Movie();
        m.setYear(fieldSet.readInt("year"));
        m.setTitle(fieldSet.readString("title"));
        m.setProducers(fieldSet.readString("producers"));
        m.setStudios(fieldSet.readString("studios"));
        m.setWinner(!fieldSet.readString("winner").isEmpty());
        return m;
    }
}
