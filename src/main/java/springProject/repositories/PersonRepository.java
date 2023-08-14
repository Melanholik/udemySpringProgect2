package springProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springProject.models.Person;

import java.util.Optional;


@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Optional<Object> findByName(String name);
}
