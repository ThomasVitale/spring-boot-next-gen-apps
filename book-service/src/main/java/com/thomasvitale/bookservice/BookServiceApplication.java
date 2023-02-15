package com.thomasvitale.bookservice;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
	Book getBookById(@PathVariable Long id) {
		log.info("Retrieving book with id: {}", id);
		return bookRepository.findById(id)
				.orElseThrow(() -> new BookNotFoundException(id));
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

class BookNotFoundException extends RuntimeException {
	public BookNotFoundException(Long id) {
		super("Book with id " + id + " not found.");
	}
}

@RestControllerAdvice
class HttpExceptionHandler {

	@ExceptionHandler(BookNotFoundException.class)
	ProblemDetail handleBookNotFoundException(BookNotFoundException ex) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
		problemDetail.setTitle("Book Not Found");
		problemDetail.setType(URI.create("https://example.net/not-found"));
		problemDetail.setProperty("severity", "low");
		return problemDetail;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		var invalidFields = ex.getBindingResult().getAllErrors().stream()
				.map(error -> {
					String name = ((FieldError) error).getField();
					String reason = error.getDefaultMessage();
					return new InvalidField(name, reason);
				})
				.toList();

		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
		problemDetail.setTitle("Input data not valid");
		problemDetail.setType(URI.create("https://example.net/validation-error"));
		problemDetail.setProperty("invalid-fields", invalidFields);
		return problemDetail;
	}

	record InvalidField(String name, String reason) {}

}
