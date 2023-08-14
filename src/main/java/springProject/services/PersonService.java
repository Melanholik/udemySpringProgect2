package springProject.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springProject.models.Person;
import springProject.repositories.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Transactional
    public void add(Person person) {
        personRepository.save(person);
    }

    public Optional<Person> getById(int id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isEmpty()) {
            return person;
        }
        Hibernate.initialize(person.get().getList());
        return personRepository.findById(id);
    }

    @Transactional
    public void deleteById(int id) {
        personRepository.deleteById(id);
    }

    @Transactional
    public void update(Person person) {
        personRepository.save(person);
    }
}
