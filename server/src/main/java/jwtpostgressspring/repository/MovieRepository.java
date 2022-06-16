package jwtpostgressspring.repository;

import jwtpostgressspring.models.Director;
import jwtpostgressspring.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Override
    List<Movie> findAll();
}
