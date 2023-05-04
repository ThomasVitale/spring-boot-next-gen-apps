package com.thomasvitale.bookservice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

@JsonTest
public class BookJsonTests {

	@Autowired
	private JacksonTester<Book> json;

	@Test
	void testSerialize() throws Exception {
		var book = new Book(394L, "Creative Book Title");
		var jsonContent = json.write(book);
		assertThat(jsonContent).extractingJsonPathNumberValue("@.id")
				.isEqualTo(book.id().intValue());
		assertThat(jsonContent).extractingJsonPathStringValue("@.title")
				.isEqualTo(book.title());
	}

	@Test
	void testDeserialize() throws Exception {
	}

}
