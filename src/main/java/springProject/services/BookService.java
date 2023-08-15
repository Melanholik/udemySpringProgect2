package springProject.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springProject.models.Book;
import springProject.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository repository) {
        this.bookRepository = repository;
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Transactional
    public void add(Book book) {
        bookRepository.save(book);
    }

    public Optional<Book> getById(int id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            return book;
        }
        Hibernate.initialize(book.get().getPerson());
        return bookRepository.findById(id);
    }

    @Transactional
    public void editWithoutPerson(Book book) {
        if (book.getId() == 0) {
            bookRepository.save(book);
            return;
        }
        Optional<Book> oldBook = bookRepository.findById(book.getId());
        if (oldBook.isPresent()) {
            Book currentBook = oldBook.get();
            currentBook.setAuthor(book.getAuthor());
            currentBook.setName(book.getName());
            currentBook.setReleaseYear(book.getReleaseYear());
        }


    }

    @Transactional
    public void deleteById(int id) {
        bookRepository.deleteById(id);
    }
}
