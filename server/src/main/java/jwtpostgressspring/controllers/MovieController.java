package jwtpostgressspring.controllers;


import jwtpostgressspring.models.Actor;
import jwtpostgressspring.models.Movie;
import jwtpostgressspring.payload.request.AddMovieRequest;
import jwtpostgressspring.payload.response.MessageResponse;
import jwtpostgressspring.repository.MovieRepository;
import jwtpostgressspring.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieRepository directorRepository;

    PasswordEncoder encoder;

    @GetMapping("/")
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Movie> getMovie(@PathVariable Long id) {
        return movieRepository.findById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMovie(@Valid @RequestBody AddMovieRequest addMovieRequest) {
        Movie movie = new Movie(addMovieRequest.getTitle(), addMovieRequest.getDescription(), addMovieRequest.getImage(), addMovieRequest.getDuration(), addMovieRequest.getRelease());
        movieRepository.save(movie);
        return ResponseEntity.ok(new MessageResponse("Movie added successfully!"));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        movieRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Movie deleted successfully!"));
    }

}
