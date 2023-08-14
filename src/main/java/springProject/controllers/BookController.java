package springProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springProject.dao.ObjectDAO;
import springProject.models.Book;
import springProject.models.Person;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final ObjectDAO<Book> bookDAO;
    private final ObjectDAO<Person> personDAO;

    @Autowired
    public BookController(ObjectDAO<Book> bookDAO, ObjectDAO<Person> personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String all(Model model) {
        model.addAttribute("books", bookDAO.get());
        return "/book/allBook";
    }

    @GetMapping("/new")
    public String creat(@ModelAttribute("book") Book book) {
        return "/book/new";
    }

    @PostMapping()
    public String add(@ModelAttribute("book") @Valid Book book,
                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/book/new";
        }
        bookDAO.add(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String getByIdForEdit(@PathVariable int id, Model model) {
        Optional<Book> book = bookDAO.getById(id);
        if (book.isEmpty()) {
            model.addAttribute("id", id);
            return "/book/notFound";
        }
        model.addAttribute("book", book.get());
        return "/book/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/book/edit";
        }
        bookDAO.update(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        Optional<Book> book = bookDAO.getById(id);
        if (book.isEmpty()) {
            model.addAttribute("id", id);
            return "/book/notFound";
        }
        model.addAttribute("book", book.get());
        Person person = book.get().getPerson();
        if (person != null) {
            model.addAttribute("isTaken", true);
            model.addAttribute("person", person);
        } else {
            model.addAttribute("isTaken", false);
            model.addAttribute("people", personDAO.get());
        }
        return "/book/book";
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable int id) {
        bookDAO.deleteById(id);
        return "redirect:/books";
    }
}
