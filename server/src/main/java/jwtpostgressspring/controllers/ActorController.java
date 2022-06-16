package jwtpostgressspring.controllers;


import jwtpostgressspring.models.Actor;
import jwtpostgressspring.models.User;
import jwtpostgressspring.payload.request.AddActorRequest;
import jwtpostgressspring.payload.response.MessageResponse;
import jwtpostgressspring.repository.ActorRepository;
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
@RequestMapping("/api/actors")
public class ActorController {

    Logger logger = LoggerFactory.getLogger(ActorController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ActorRepository actorRepository;

    PasswordEncoder encoder;

    @GetMapping("/")
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Actor> getActor(@PathVariable Long id) {
        return actorRepository.findById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addActor(@Valid @RequestBody AddActorRequest addActorRequest) {

        Actor actor = new Actor(addActorRequest.getName(), addActorRequest.getSurname(), addActorRequest.getDescription(), addActorRequest.getImage());

        actorRepository.save(actor);

        return ResponseEntity.ok(new MessageResponse("Actor added successfully!"));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteActor(@PathVariable Long id) {
        actorRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Actor deleted successfully!"));
    }

}
