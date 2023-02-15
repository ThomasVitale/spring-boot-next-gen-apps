package com.thomasvitale.bookservice;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.datasource.url=jdbc:tc:postgresql:15:///")
class BookServiceApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void whenGetRequestWithIdThenBookReturned() {
//		var bookToCreate = new Book(null, "Title");
//		Book expectedBook = webTestClient
//				.post()
//				.uri("/books")
//				.bodyValue(bookToCreate)
//				.exchange()
//				.expectStatus().isCreated()
//				.expectBody(Book.class).value(book -> assertThat(book).isNotNull())
//				.returnResult().getResponseBody();
//
//		webTestClient
//				.get()
//				.uri("/books/" + expectedBook.id())
//				.exchange()
//				.expectStatus().is2xxSuccessful()
//				.expectBody(Book.class).value(actualBook -> {
//					assertThat(actualBook).isNotNull();
//					assertThat(actualBook.title()).isEqualTo(expectedBook.title());
//				});
	}

}
