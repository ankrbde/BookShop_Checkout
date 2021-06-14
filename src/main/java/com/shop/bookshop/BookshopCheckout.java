package com.shop.bookshop;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.shop.bookshop.exception.CheckoutException;
import com.shop.bookshop.model.Book;
import com.shop.bookshop.service.BookListService;
import com.shop.bookshop.service.BookPriceService;
import com.shop.bookshop.service.CheckoutService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookshopCheckout {

    private static  final Logger LOGGER =  Logger.getLogger(BookshopCheckout.class.getCanonicalName());

    public double runCheckOut(String path) throws CheckoutException {
        List<Book> books = null;
        BookListService bookListService = new BookListService();
        BookPriceService bookPriceService = new BookPriceService();
        CheckoutService checkoutService = new CheckoutService(bookPriceService);
        double finalPrice = 0;

        try{
            books = bookListService.getBookList(path, books);
            finalPrice = checkoutService.checkOutPrice(books);
        } catch (JsonParseException e) {
            LOGGER.log(Level.FINE,"Failed to parse JSON");
            throw new CheckoutException("Failed to parse JSON");
        } catch (JsonMappingException e) {
            LOGGER.log(Level.FINE,"Failed to map JSON");
            throw new CheckoutException("Failed to map JSON");
        } catch (URISyntaxException e) {
            LOGGER.log(Level.FINE,"Failed URI Syntax");
            throw new CheckoutException("Failed URI Syntax");
        } catch (IOException e) {
            LOGGER.log(Level.FINE,"Failed to read JSON");
            throw new CheckoutException("Failed URI Syntax");
        }
        LOGGER.log(Level.FINE,"Final Price after Checkout is " + finalPrice);
        return finalPrice;
    }


}
