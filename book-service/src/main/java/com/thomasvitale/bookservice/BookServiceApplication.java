package com.thomasvitale.bookservice;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class BookServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookServiceApplication.class, args);
	}

}

@RestController
@RequestMapping("/books")
class BookController {

	@GetMapping
	List<Book> getBooks() {
		return List.of(
			new Book(1L,"The Hobbit"),
			new Book(2L, "The Lord of the Rings")
		);
	}

}

record Book(Long id, String title){}
