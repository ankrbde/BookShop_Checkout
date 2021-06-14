package com.shop.bookshop.model;

import java.time.Year;

public class Book {

    private String title;
    private Year year;
    private double price;

    public Book(String title, Year year, double price) {
        this.title = title;
        this.year = year;
        this.price = price;
    }

    public Book() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
