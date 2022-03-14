package br.com.layon.movielist.dto;

import br.com.layon.movielist.entity.Movie;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class MovieListRE {
    Map<String, List<Movie>> movieListRE;
}
