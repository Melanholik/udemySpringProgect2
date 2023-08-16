package springProject.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public List<Book> getPage() {
        return bookRepository.findAll();
    }

    public List<Book> getPage(int page, int booksPerPage) {
        return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public List<Book> getSortedPage(int page, int booksPerPage) {
        return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("releaseYear"))).getContent();
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

    public List<Book> getSortedAll() {
        return bookRepository.findAll(Sort.by("releaseYear"));
    }

    public List<Book> getByNameStart(String neededStr) {
        return bookRepository.findByNameStartingWith(neededStr);
    }
}
