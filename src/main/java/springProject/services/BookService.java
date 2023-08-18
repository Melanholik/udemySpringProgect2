package springProject.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springProject.models.Book;
import springProject.models.BookOrderBy;
import springProject.models.Person;
import springProject.repositories.BookRepository;
import springProject.repositories.PersonRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    @Autowired
    public BookService(BookRepository repository, PersonRepository personRepository) {
        this.bookRepository = repository;
        this.personRepository = personRepository;
    }

    public List<Book> getAll(BookOrderBy bookOrderBy) {
        switch (bookOrderBy) {
            case NAME -> {
                return bookRepository.findAll(Sort.by("name"));
            }
            case RELEASE_YEAR -> {
                return bookRepository.findAll(Sort.by("releaseYear"));
            }
            case NOT_SORT -> {
                return bookRepository.findAll();
            }
        }
        return bookRepository.findAll();
    }

    public List<Book> getAll(int page, int booksPerPage, BookOrderBy bookOrderBy) {
        switch (bookOrderBy) {
            case NAME -> {
                return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("name"))).getContent();
            }
            case RELEASE_YEAR -> {
                return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("releaseYear"))).getContent();
            }
            case NOT_SORT -> {
                return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
            }
        }
        return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
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

    public List<Book> getByNameStart(String neededStr) {
        return bookRepository.findByNameStartingWith(neededStr);
    }

    @Transactional
    public void addPerson(int bookId, int personId) {
        Optional<Book> oldBook = bookRepository.findById(bookId);
        if (oldBook.isPresent()) {
            Book objOldBook = oldBook.get();
            Optional<Person> person = personRepository.findById(personId);
            if (person.isPresent()) {
                objOldBook.setPerson(person.get());
                objOldBook.setTakeTame(new Date());
            }
        }
    }

    @Transactional
    public void deletePerson(int bookId) {
        Optional<Book> oldBook = bookRepository.findById(bookId);
        if (oldBook.isPresent()) {
            oldBook.get().setPerson(null);
            oldBook.get().setTakeTame(null);
        }
    }
}
