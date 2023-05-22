package com.thomasvitale.orderservice;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;

@SpringBootApplication
@ConfigurationPropertiesScan
public class OrderServiceApplication {

	public static void main(String[] args) {
		Hooks.enableAutomaticContextPropagation();
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}

@ConfigurationProperties(prefix = "order")
record OrderProperties(URI bookService){}

@RestController
@RequestMapping("/orders")
class OrderController {

	private static final Logger log = LoggerFactory.getLogger(OrderController.class);

	private final BookClient bookClient;

	OrderController(BookClient bookClient) {
		this.bookClient = bookClient;
	}

	@PostMapping
	Mono<Order> orderBook(@RequestBody OrderRequest orderRequest) {
		return Mono.empty();
	}

}

interface BookClient {}

record Book(Long id, String title){}

record Order(Long bookId, String title, boolean approved){}

record OrderRequest(Long bookId, int quantity){}
