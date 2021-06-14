package com.shop.bookshop;


import com.shop.bookshop.exception.CheckoutException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class BookshopCheckoutTests {


	private BookshopCheckout bookShpChkOutApp = new BookshopCheckout();

	//Test to verify if the logic written in BookshopCheckout works and returns non zero price based on input list of books
	@Test
	public void testNumberFound_true() throws CheckoutException { // Verify happy flow if the output of not zero based on input data
		String path = "CheckoutList1.json";
		double finalPrice = bookShpChkOutApp.runCheckOut(path);
		Assertions.assertTrue(finalPrice != 0);
	}

	@Test
	public void testEmptyBookList() throws CheckoutException { // On passing of Empty List price value should be zero
		String path = "EmptyBookList.json";
		double price = bookShpChkOutApp.runCheckOut(path);
		Assertions.assertTrue(price == 0);
	}

	//Single Book with year 2010 will be discounted and final price should be less than actual value
	@Test
	public void testSingleBookYearGreaterThan2000() throws CheckoutException {
		String path = "SingleBookWithYear2010.json";
		double price = bookShpChkOutApp.runCheckOut(path);
		Assertions.assertTrue(price < 13.14);
	}

	//Single Book with year less than 2000 will not be discounted and final price should be less than actual value
	@Test
	public void testSingleBookYearLessthan2000() throws CheckoutException {
		String path = "SingleBookYearLessthan2000.json";
		double price = bookShpChkOutApp.runCheckOut(path);
		Assertions.assertTrue(price == 15.20);
	}

	// Test to verify that on checkout of books total price > 30 will be given 5% discount.
	@Test
	public void testBookListTotalPriceGreaterthan30() throws CheckoutException {
		String path = "BookListTotalPriceGreatrthan30.json";
		double price = bookShpChkOutApp.runCheckOut(path);
		// Based on input json file, known total of 3 books is £39.39
		Assertions.assertTrue(price < 39.39);
	}


	// Test to verify that on checkout of books total price > 30 will be given 5% discount.
	@Test
	public void testBookListTotalPriceLessthan30() throws CheckoutException {
		String path = "BookListTotalPriceLessthan30.json";
		double price = bookShpChkOutApp.runCheckOut(path);
		// Based on input json file, known total of 3 books is £23.76
		Assertions.assertTrue(price == 23.76);
	}

	// On passing of File which does not exists, application will handle NPE and return price value zero
	@Test
	public void testIncorrectPathForBookList() throws CheckoutException {
		String path = "NoSuchFile.json";
		double price = bookShpChkOutApp.runCheckOut(path);
		Assertions.assertTrue(price == 0);
	}

	//Json file with missing comma : JSONMappingException
	@Test
	public void testBookshopeCheckoutWithParseExp() throws CheckoutException {
		String path = "MissingComma.json";
		double price = bookShpChkOutApp.runCheckOut(path);
		Assertions.assertTrue(price == 0);
	}

	//Json file with addition attribute "Author" for a book from book list : UnrecognizedPropertyException
	@Test
	public void testBookshopeCheckoutWithAdditinalAtr() throws CheckoutException {
		String path = "AdditionalProperty.json";
		double price = bookShpChkOutApp.runCheckOut(path);
		Assertions.assertTrue(price == 0);
	}

	//For a book, if price is not in JSON property, it will return zero and if ti
	@Test
	public void testBookshopeCheckout_BookMissingProoerty() throws CheckoutException {
		String path = "MissingMainProperty.json";
		double price = bookShpChkOutApp.runCheckOut(path);
		Assertions.assertTrue(price != 0);
	}

	//Year value is incorrect
	@Test
	public void testBookshopeCheckout_InvalidYear() throws CheckoutException {
		String path = "IncorrectYear.json";
		double price = bookShpChkOutApp.runCheckOut(path);
		assertEquals(price, 0); // Exception is handled and returns zero price
	}

	//Test - Expected exception - custom exception - CheckoutException
	//JSONMappingException - Unexpected character for Price
	@Test
	public void testBookshopeCheckout_PricenotDouble() {
		assertThrows(CheckoutException.class, new Executable() {

			@Override
			public void execute() throws Throwable {
				String path = "IncorrectPrice.json";
				double price = bookShpChkOutApp.runCheckOut(path);
				Assertions.assertTrue(price == 0); // Exception is handled and returns zero price
			}
		});
	}


}