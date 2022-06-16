package jwtpostgressspring.controllers;


import jwtpostgressspring.models.Actor;
import jwtpostgressspring.models.Director;
import jwtpostgressspring.payload.request.AddDirectorRequest;
import jwtpostgressspring.payload.response.MessageResponse;
import jwtpostgressspring.repository.DirectorRepository;
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
@RequestMapping("/api/directors")
public class DirectorController {

    Logger logger = LoggerFactory.getLogger(DirectorController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DirectorRepository directorRepository;

    PasswordEncoder encoder;

    @GetMapping("/")
    public List<Director> getAllDirectors() {
        return directorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Director> getDirector(@PathVariable Long id) {
        return directorRepository.findById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addDirector(@Valid @RequestBody AddDirectorRequest addDirectorRequest) {

        Director director = new Director(addDirectorRequest.getName(), addDirectorRequest.getSurname(), addDirectorRequest.getDescription(), addDirectorRequest.getImage());

        directorRepository.save(director);

        return ResponseEntity.ok(new MessageResponse("Director added successfully!"));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteDirector(@PathVariable Long id) {
        directorRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Director deleted successfully!"));
    }
}
