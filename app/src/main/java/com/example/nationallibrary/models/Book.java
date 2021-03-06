package com.example.nationallibrary.models;

import android.util.Log;
import android.widget.ImageView;

import com.example.nationallibrary.R;
import com.squareup.picasso.Picasso;

public class Book {

    private String title;
    private String subtitle;
    private String authors;
    private String publisher;
    private String isbn10;
    private String isbn13;
    private String pages;
    private String year;
    private String rating;
    private String desc;
    private String price;
    private String urlImage;
    private String url;



    public Book(String urlImage, String title, String subtitle, String isbn13, String price, String url) {
        this.urlImage = urlImage;
        this.title = title;
        this.subtitle = subtitle;
        this.isbn13 = isbn13;
        this.price = price;
        this.url = url;
    }

    public Book(String title, String subtitle, String authors, String publisher, String isbn10,
                String isbn13, String pages, String year, String rating, String desc, String price,
                String urlImage, String url) {
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
        this.urlImage = urlImage;
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

    public ImageView getImage(String urlImage, ImageView bookImageView)
    {

        try {
            Picasso.get()
                    .load(urlImage)
                    .fit()
                    .centerCrop()
                    .error(R.mipmap.book)
                    .into(bookImageView);
        }catch (Exception e){
            Log.d("ErrorImageUrl", "Error Image Url");
        }

        return bookImageView;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
