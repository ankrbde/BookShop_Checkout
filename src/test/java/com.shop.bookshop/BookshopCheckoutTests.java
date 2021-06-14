package com.shop.bookshop;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;

@ExtendWith(MockitoExtension.class)
public class BookshopCheckoutTests {


	private BookshopCheckout bookShpChkOutApp = new BookshopCheckout();

	//Test to verify if the logic written in BookshopCheckout works and returns non zero price based on input list of books
	@Test
	public void testNumberFound_true() throws IOException, URISyntaxException { // Verify happy flow if the output of not zero based on input data
		String path = "CheckoutList1.json";
		double finalPrice = bookShpChkOutApp.runCheckOut(path);
		Assertions.assertTrue(finalPrice != 0);
	}

}