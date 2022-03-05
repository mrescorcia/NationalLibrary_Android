package com.example.nationallibrary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nationallibrary.R;
import com.example.nationallibrary.models.Book;

import java.util.List;

public class ListDetailsAdapter extends RecyclerView.Adapter<ListDetailsAdapter.ViewHolder> {

    private List<Book> booksList;
    private final LayoutInflater mLayoutInflater;
    private Context context;

    public ListDetailsAdapter(List<Book> booksList, Context context) {
        this.booksList = booksList;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    @Override
    public ListDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.detail_book, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListDetailsAdapter.ViewHolder holder, final int position) {
        holder.bindData(booksList.get(position));
    }

    public void setItems(List<Book> items) {
        booksList = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bookImageView;
        TextView titleTextView, subtitleTextView, authorsTextView, publisherTextView, isbn10TextView,
                isbn13TextView, pagesTextView, yearTextView, ratingTextView, descTextView, priceTextView,
                imageTextView, urlTextView;

        ViewHolder(View itemView) {
            super(itemView);
            bookImageView = itemView.findViewById(R.id.bookImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            subtitleTextView = itemView.findViewById(R.id.subtitleTextView);
            authorsTextView = itemView.findViewById(R.id.authorsTextView);
            publisherTextView = itemView.findViewById(R.id.publisherTextView);
            isbn10TextView = itemView.findViewById(R.id.isbn10TextView);
            isbn13TextView = itemView.findViewById(R.id.isbn13TextView);
            pagesTextView = itemView.findViewById(R.id.pagesTextView);
            yearTextView = itemView.findViewById(R.id.yearTextView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            descTextView = itemView.findViewById(R.id.descTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            imageTextView = itemView.findViewById(R.id.imageTextView);
            urlTextView = itemView.findViewById(R.id.urlTextView);

        }

        void bindData(final Book book) {

            bookImageView = book.getImage(book.getUrlImage(), bookImageView);
            titleTextView.setText(book.getTitle());
            subtitleTextView.setText(book.getSubtitle());
            authorsTextView.setText(book.getAuthors());
            publisherTextView.setText(book.getPublisher());
            isbn10TextView.setText(book.getIsbn10());
            isbn13TextView.setText(book.getIsbn13());

            pagesTextView.setText(book.getPages());
            yearTextView.setText(book.getYear());
            ratingTextView.setText(book.getRating());
            descTextView.setText(book.getDesc());
            priceTextView.setText(book.getPrice());
            urlTextView.setText(book.getUrl());

        }

    }
}
