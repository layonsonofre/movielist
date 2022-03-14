package br.com.layon.movielist.repository;

import br.com.layon.movielist.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {
    @Query("select t from Movie t where upper(t.title) = upper(:title)")
    List<Movie> findByTitle(@Param("title") String title);
}
