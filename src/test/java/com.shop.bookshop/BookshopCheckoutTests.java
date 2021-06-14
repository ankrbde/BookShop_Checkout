package com.shop.bookshop;

import com.shop.bookshop.exception.CheckoutException;
import com.shop.bookshop.model.Book;
import com.shop.bookshop.service.BookPriceService;
import com.shop.bookshop.service.CheckoutService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookshopCheckoutTests {


	private BookshopCheckout bookShpChkOutApp = new BookshopCheckout();
	@InjectMocks
	private CheckoutService checkoutService;
	@Mock
	private BookPriceService bookPriceService;

	//Test to verify if the logic written in BookshopCheckout works and returns non zero price based on input list of books
	@Test
	public void testNumberFound_true() throws CheckoutException { // Verify happy flow if the output of not zero based on input data
		String path = "CheckoutList1.json";
		double finalPrice = bookShpChkOutApp.runCheckOut(path);
		Assertions.assertTrue(finalPrice != 0);
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


	/* ****Below are the tests to verify and check negative scenarios****  */

	// On passing of File which does not exists, application will throw exception as expected
	@Test
	public void testIncorrectPathForBookList() throws CheckoutException {
		assertThrows(CheckoutException.class, () -> {
			String path = "NoSuchFile.json";
			double price = bookShpChkOutApp.runCheckOut(path);
		});
	}

	//Json file with missing comma : JSONMappingException
	@Test
	public void testBookshopeCheckoutWithParseExp() throws CheckoutException {
		assertThrows(CheckoutException.class, () -> {
			String path = "MissingComma.json";
			double price = bookShpChkOutApp.runCheckOut(path);

		});
	}

	//Json file with addition attribute "Author" for a book from book list : UnrecognizedPropertyException
	@Test
	public void testBookshopeCheckoutWithAdditinalAtr()  {
		assertThrows(CheckoutException.class, () -> {
			String path = "AdditionalProperty.json";
			double price = bookShpChkOutApp.runCheckOut(path);

		});
	}

	//For a book, if price is not in JSON property, throws exception
	@Test
	public void testBookshopeCheckout_BookMissingProoerty()  {
		assertThrows(CheckoutException.class, () -> {
			String path = "MissingMainProperty.json";
			double price = bookShpChkOutApp.runCheckOut(path);

		});
	}


	//CheckoutException as while reading the json resource comes out as null - Year value is incorrect
	@Test
	public void testBookshopeCheckout_InvalidYear()  {
		assertThrows(CheckoutException.class, () -> {
			String path = "IncorrectYear.json";
			double price = bookShpChkOutApp.runCheckOut(path);

		});
	}

	//On passing empty
	@Test
	public void testEmptyBookList() { // On passing of Empty List price value should be zero
		assertThrows(CheckoutException.class, () -> {
			String path = "EmptyBookList.json";
			double price = bookShpChkOutApp.runCheckOut(path);

		});
	}

	//Test - Expected exception - custom exception - CheckoutException
	//JSONMappingException - Unexpected character for Price
	@Test
	public void testBookshopeCheckout_PricenotDouble() {
		assertThrows(CheckoutException.class, () -> {
			String path = "IncorrectPrice.json";
			double price = bookShpChkOutApp.runCheckOut(path);

		});
	}

	/*
	Test using mocking framework - Mockito
	This will mock service files and test checOutPrice method from CheckoutService
	 Test to Mock the service to verify
	 */
	@Test
	public void testCheckoutServ() {
		List<Book> books = new ArrayList<>();
		books.add(new Book("Tes1.json", Year.of(2010), 13.14));
		when(bookPriceService.getBookPrice(13.14, Year.of(2010))).thenReturn(10.0);
		double prc = checkoutService.checkOutPrice(books);
		assertEquals(10.0,prc);
	}


}