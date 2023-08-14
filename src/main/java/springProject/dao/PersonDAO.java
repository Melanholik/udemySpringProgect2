package springProject.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import springProject.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO implements ObjectDAO<Person> {
    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Person> get() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    @Override
    public Optional<Person> getById(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    @Override
    public void update(Person person) {
        jdbcTemplate.update("UPDATE person SET name = ?, birthday_year = ? WHERE id = ?", person.getName(),
                person.getBirthdayYear(), person.getId());
    }

    @Override
    public void add(Person person) {
        jdbcTemplate.update("INSERT INTO person(name, birthday_year) VALUES (?, ?)",
                person.getName(), person.getBirthdayYear());
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }

    public Optional<Person> getByName(String name) {
        return jdbcTemplate.query("SELECT * FROM person WHERE name = ?", new Object[]{name},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
}
