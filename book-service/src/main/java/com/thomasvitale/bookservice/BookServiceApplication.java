package com.thomasvitale.bookservice;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@SpringBootApplication
public class BookServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookServiceApplication.class, args);
	}

}

@RestController
@RequestMapping("/books")
class BookController {
	private static final Logger log = LoggerFactory.getLogger(BookController.class);
	private final BookRepository bookRepository;

	BookController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@GetMapping
	List<Book> getBooks() {
		log.info("Retrieving all books");
		return bookRepository.findAll();
	}

	@GetMapping("{id}")
	Optional<Book> getBookById(@PathVariable Long id) {
		log.info("Retrieving book with id: {}", id);
		return bookRepository.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	Book addBook(@RequestBody @Valid Book book) {
		log.info("Adding new book: {}", book.title());
		return bookRepository.save(book);
	}
}

record Book(@Id Long id, @NotEmpty String title){}

interface BookRepository extends ListCrudRepository<Book,Long>{}
