package jwtpostgressspring.repository;

import jwtpostgressspring.models.Actor;
import jwtpostgressspring.models.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    @Override
    List<Actor> findAll();
}
