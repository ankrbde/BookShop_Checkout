package com.shop.bookshop;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookshopCheckoutTests {


	private BookshopCheckout bookShpChkOutApp = new BookshopCheckout();

	@Test
	public void testNumberFound_true()  { // Verify happy flow if the ourput of not zero based on input data
		String path = "CheckoutList1.json";
		double finalPrice = bookShpChkOutApp.runCheckOut(path);
		Assertions.assertTrue(finalPrice != 0);
	}

}