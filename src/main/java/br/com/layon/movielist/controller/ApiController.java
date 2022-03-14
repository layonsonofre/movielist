package br.com.layon.movielist.controller;


import br.com.layon.movielist.dto.MovieListRE;
import br.com.layon.movielist.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class ApiController {

    @Autowired
    private MovieService movieService;

    @GetMapping("awards")
    public ResponseEntity<Map> getAwards() {
        return this.movieService.getAwards();
    }

    @GetMapping("title")
    public ResponseEntity<MovieListRE> getMovie(@RequestParam(name = "title") final String title) {
        return this.movieService.findByTitle(title);
    }
}
