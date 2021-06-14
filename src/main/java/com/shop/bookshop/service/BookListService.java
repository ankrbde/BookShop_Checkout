package com.shop.bookshop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shop.bookshop.BookshopCheckout;
import com.shop.bookshop.exception.CheckoutException;
import com.shop.bookshop.model.Book;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class BookListService {

    private static  final Logger LOGGER =  Logger.getLogger(BookListService.class.getCanonicalName());

    public List<Book> getBookList(String path, List<Book> books) throws URISyntaxException, IOException, CheckoutException {
        return readFromJson(path);
    }

    private List<Book> readFromJson(String path) throws URISyntaxException, IOException, CheckoutException {
        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        Book[] list = null;
        URL resource = BookshopCheckout.class.getClassLoader().getResource(path);
        if(resource!=null) {
            list = mapper.readValue(Paths.get(resource.toURI()).toFile(), Book[].class);
            if (Arrays.asList(list)!=null && !Arrays.asList(list).isEmpty()){
                return Arrays.asList(list);
            }else{
                throw new CheckoutException("Book list is empty");
            }
        }else{
            throw new CheckoutException("Resource(Book List) is null");
        }
    }
}
