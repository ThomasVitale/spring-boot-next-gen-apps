package com.thomasvitale.bookservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

@JsonTest
public class BookJsonTests {

	// @Autowired
	// private JacksonTester<Book> json;

	// @Test
	// void testSerialize() throws Exception {
	// 	var book = new Book(394L, "Creative Book Title");
	// 	var jsonContent = json.write(book);
	// 	assertThat(jsonContent).extractingJsonPathNumberValue("@.id")
	// 			.isEqualTo(book.id().intValue());
	// 	assertThat(jsonContent).extractingJsonPathStringValue("@.title")
	// 			.isEqualTo(book.title());
	// }

	@Test
	void testDeserialize() throws Exception {
		
	}

}
