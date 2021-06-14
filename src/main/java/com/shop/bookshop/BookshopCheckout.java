package com.shop.bookshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shop.bookshop.model.Book;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Year;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class BookshopCheckout {

    private static  final Logger LOGGER =  Logger.getLogger(BookshopCheckout.class.getCanonicalName());

    public double runCheckOut(String path) throws URISyntaxException, IOException {

        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        Book[] list = null;
        List<Book> books = null;
        URL resource = BookshopCheckout.class.getClassLoader().getResource(path);
        if(resource!=null) {
            list = mapper.readValue(Paths.get(resource.toURI()).toFile(), Book[].class);
            books = Arrays.asList(list);
        }
        double finalAmt = 0;
        double bookPrice = 0;
        if (books!=null && !books.isEmpty()) {
            for (Book bk : books) {
                bookPrice = bk.getPrice();
                Year bookYear = bk.getYear();
                if (bookYear !=null && bookYear.isAfter(Year.of(2000))) bookPrice *= 0.9; // 10% discount
                finalAmt += bookPrice;
            }
            if (finalAmt > 30) finalAmt *= 0.95; // 5% discount
        }
        return finalAmt;

    }


}
