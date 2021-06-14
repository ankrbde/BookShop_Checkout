package com.shop.bookshop.service;

import com.shop.bookshop.model.Book;

import java.time.Year;
import java.util.List;

public class CheckoutService {
    private BookPriceService bookPriceService;

    public CheckoutService(BookPriceService bookPriceService) {
        this.bookPriceService = bookPriceService;
    }

    public double checkOutPrice(List<Book> books) {
        double finalAmt = 0;
        double bookPrice = 0;
        if (books!=null && !books.isEmpty()) {
            for (Book bk : books) {
                bookPrice = bk.getPrice();
                Year bookYear = bk.getYear();
                bookPrice = bookPriceService.getBookPrice(bookPrice, bookYear);
                finalAmt += bookPrice;
            }
            if (finalAmt > 30) finalAmt *= 0.95; // 5% discount
        }
        return finalAmt;
    }

}
