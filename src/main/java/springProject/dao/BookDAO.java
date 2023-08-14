package springProject.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import springProject.models.Book;

import java.util.List;
import java.util.Optional;


@Component
public class BookDAO implements ObjectDAO<Book> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> get() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    @Override
    public Optional<Book> getById(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    @Override
    public void update(Book book) {
        jdbcTemplate.update("UPDATE book SET name = ?, author = ?, release_year = ? WHERE id = ?", book.getName(),
                book.getAuthor(), book.getReleaseYear(), book.getId());
    }

    @Override
    public void add(Book book) {
        jdbcTemplate.update("INSERT INTO book(name, author, release_year) VALUES(?, ?, ?)", book.getName(),
                book.getAuthor(), book.getReleaseYear());
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }


}
