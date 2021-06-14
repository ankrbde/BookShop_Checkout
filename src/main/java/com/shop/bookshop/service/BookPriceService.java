package com.shop.bookshop.service;

import java.time.Year;

public class BookPriceService {

    public double getBookPrice(double bookPrice, Year bookYear) {
        if (bookYear !=null && bookYear.isAfter(Year.of(2000))) bookPrice *= 0.9; // 10% discount
        return bookPrice;
    }
}
