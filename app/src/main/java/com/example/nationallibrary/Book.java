package com.example.nationallibrary;

import android.widget.ImageView;

import java.util.List;

public class Book {

    public String title;
    public String subtitle;
    public String authors;
    public String publisher;
    public String isbn10;
    public String isbn13;
    public String pages;
    public String year;
    public String rating;
    public String desc;
    public String price;
    public String image;
    public String url;
    public List<String> pdf;



    public Book(String image, String title, String subtitle, String isbn13, String price, String url) {
        this.image = image;
        this.title = title;
        this.subtitle = subtitle;
        this.isbn13 = isbn13;
        this.price = price;
        this.url = url;
    }

    public Book(String title, String subtitle, String authors, String publisher, String isbn10,
                String isbn13, String pages, String year, String rating, String desc, String price,
                String image, String url, List<String> pdf) {
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
        this.publisher = publisher;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.pages = pages;
        this.year = year;
        this.rating = rating;
        this.desc = desc;
        this.price = price;
        this.image = image;
        this.url = url;
        this.pdf = pdf;
    }

    public Book(String title, String subtitle, String authors, String publisher, String isbn10,
                String isbn13, String pages, String year, String rating, String desc, String price, String image, String url) {
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
        this.publisher = publisher;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.pages = pages;
        this.year = year;
        this.rating = rating;
        this.desc = desc;
        this.price = price;
        this.image = image;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ImageView getImage() {
        return null;
    }

    public void setImage(String urlImage) {
        this.image = null;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getPdf() {
        return pdf;
    }

    public void setPdf(List<String> pdf) {
        this.pdf = pdf;
    }
}
