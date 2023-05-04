package com.thomasvitale.orderservice;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import reactor.core.publisher.Mono;

@SpringBootApplication
@ConfigurationPropertiesScan
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Bean
	BookClient bookClient(WebClient.Builder builder, OrderProperties orderProperties) {
		WebClient client = builder.baseUrl(orderProperties.bookService().toString()).build();
		HttpServiceProxyFactory factory = HttpServiceProxyFactory
				.builder(WebClientAdapter.forClient(client)).build();
		return factory.createClient(BookClient.class);
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


interface BookClient {
}

record Book(Long id, String title){}

record Order(Long bookId, String title, boolean approved){}

record OrderRequest(Long bookId, int quantity){}
