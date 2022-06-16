package jwtpostgressspring.repository;

import jwtpostgressspring.models.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
    @Override
    List<Director> findAll();
}
