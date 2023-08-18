package springProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springProject.models.Book;
import springProject.models.BookOrderBy;
import springProject.models.Person;
import springProject.services.BookService;
import springProject.services.PersonService;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final PersonService personService;

    @Autowired

    public BookController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping()
    public String all(@RequestParam(name = "page", defaultValue = "0") int page,
                      @RequestParam(name = "books_per_page", defaultValue = "0") int booksPerPage,
                      @RequestParam(name = "sort_by_year", defaultValue = "false") boolean isNeedSortByYear,
                      Model model) {
        if (booksPerPage == 0) {
            if (isNeedSortByYear) {
                model.addAttribute("books", bookService.getAll(BookOrderBy.RELEASE_YEAR));
            } else {
                model.addAttribute("books", bookService.getAll(BookOrderBy.NOT_SORT));
            }
        } else {
            if (isNeedSortByYear) {
                model.addAttribute("books", bookService.getAll(page, booksPerPage, BookOrderBy.NOT_SORT));
            } else {
                model.addAttribute("books", bookService.getAll(page, booksPerPage, BookOrderBy.RELEASE_YEAR));
            }
        }
        return "/book/allBook";
    }

    @GetMapping("/search")
    public String findMyStr(@RequestParam(name = "str", defaultValue = "") String neededStr, Model model) {
        if (Objects.equals(neededStr, "")) {
            model.addAttribute("books", null);
        } else {
            model.addAttribute("books", bookService.getByNameStart(neededStr));
        }
        return "/book/search";
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
        bookService.add(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String getByIdForEdit(@PathVariable int id, Model model) {
        Optional<Book> book = bookService.getById(id);
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
        bookService.editWithoutPerson(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        Optional<Book> book = bookService.getById(id);
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
            model.addAttribute("people", personService.findAll());
        }
        return "/book/book";
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable int id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    @PatchMapping("/addPerson/{id}")
    public String addPerson(@PathVariable int id, @ModelAttribute("personId") Integer personId) {
        bookService.addPerson(id, personId);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/deletePerson/{id}")
    public String deletePerson(@PathVariable int id) {
        bookService.deletePerson(id);
        return "redirect:/books/" + id;
    }
}
