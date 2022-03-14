package br.com.layon.movielist.service;

import br.com.layon.movielist.dto.AwardInfo;
import br.com.layon.movielist.dto.MovieListRE;
import br.com.layon.movielist.entity.Movie;
import br.com.layon.movielist.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Service
@RequiredArgsConstructor
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;


    public ResponseEntity<Map> getAwards() {
        List<Movie> movies = this.movieRepository.findAll(Sort.by(Sort.Direction.ASC, "year"));

        Map<String, ArrayList<Integer>> producersAwards = new HashMap<>();
        movies.forEach(movie -> {
            if (movie.getWinner()) {
                String[] producers = movie.getProducers().replaceAll(" and ", ", ").split(", ");
                for (String producer : producers) {
                    ArrayList<Integer> current = producersAwards.getOrDefault(producer, new ArrayList<>());
                    current.add(movie.getYear());
                    producersAwards.put(producer, current);
                }
            }
        });

        int minDiff = Integer.MAX_VALUE;
        int maxDiff = Integer.MIN_VALUE;

        for (Map.Entry<String, ArrayList<Integer>> awarded : producersAwards.entrySet()) {
            if (awarded.getValue().size() >= 2) {
                ArrayList<Integer> years = awarded.getValue();
                for (int i = 1; i < years.size(); i++) {
                    int diff = years.get(i) - years.get(i - 1);
                    if (diff < minDiff) {
                        minDiff = diff;
                    }
                    if (diff > maxDiff) {
                        maxDiff = diff;
                    }
                }
            }
        }

        ArrayList<AwardInfo> min = new ArrayList<>();
        ArrayList<AwardInfo> max = new ArrayList<>();

        for (Map.Entry<String, ArrayList<Integer>> awarded : producersAwards.entrySet()) {
            ArrayList<Integer> awards = awarded.getValue();
            for (int i = 1; i < awards.size(); i++) {
                if (minDiff != Integer.MAX_VALUE && awards.get(i) - awards.get(i - 1) == minDiff) {
                    AwardInfo a = new AwardInfo();
                    a.setProducer(awarded.getKey());
                    a.setPreviousWin(awards.get(i - 1));
                    a.setFollowingWin(awards.get(i));
                    a.setInterval(minDiff);
                    min.add(a);
                }

                if (maxDiff != Integer.MIN_VALUE && awards.get(i) - awards.get(i - 1) == maxDiff) {
                    AwardInfo a = new AwardInfo();
                    a.setProducer(awarded.getKey());
                    a.setPreviousWin(awards.get(i - 1));
                    a.setFollowingWin(awards.get(i));
                    a.setInterval(maxDiff);
                    max.add(a);
                }
            }
        }

        Map<String, Object> res = new HashMap<>();
        res.put("max", max);
        res.put("min", min);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    public ResponseEntity<MovieListRE> findByTitle(String title) {
        MovieListRE movieListRE = new MovieListRE();
        Map<String, List<Movie>> res = new HashMap<>();
        List<Movie> movies = this.movieRepository.findByTitle(title);
        res.put("movies", movies);
        movieListRE.setMovieListRE(res);
        return new ResponseEntity<>(movieListRE, HttpStatus.OK);
    }
}
