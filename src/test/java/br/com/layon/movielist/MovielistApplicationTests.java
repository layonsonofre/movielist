package br.com.layon.movielist;

import br.com.layon.movielist.dto.MovieListRE;
import br.com.layon.movielist.entity.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovielistApplicationTests {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	void checkRegisters() {
		String[] titles = {"Can't Stop the Music", "Megaforce", "Rambo: Last Blood"};
		for (String title : titles) {
			ResponseEntity<MovieListRE> res = this.testRestTemplate.exchange("/api/title?title=" + title, HttpMethod.GET, null, MovieListRE.class);
			assertNotNull(res.getBody());
			List<Movie> movies = res.getBody().getMovieListRE().get("movies");
			for (Movie m : movies) {
				assertEquals(m.getTitle(), title);
			}
		}
	}
}
