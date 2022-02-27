package com.example.nationallibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListDetailsAdapter extends RecyclerView.Adapter<ListDetailsAdapter.ViewHolder> {
    private List<Book> booksList;
    private LayoutInflater mLayoutInflater;
    private Context context;

    public ListDetailsAdapter(List<Book> booksList, Context context){
        this.booksList = booksList;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getItemCount() { return booksList.size(); }

    @Override
    public ListDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mLayoutInflater.inflate(R.layout.detail_book, null);
        return new ListDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListDetailsAdapter.ViewHolder holder, final int position){
        holder.bindData(booksList.get(position));
    }

    public void setItems(List<Book> items){ booksList = items; }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView bookImageView;
        TextView titleTextView, subtitleTextView, authorsTextView, publisherTextView, isbn10TextView,
                isbn13TextView, pagesTextView, yearTextView, ratingTextView, descTextView, priceTextView,
                imageTextView, urlTextView;

        ViewHolder(View itemView){
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


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*isbn13TextView = v.findViewById(R.id.isbn13TextView);
                    String text = (String) isbn13TextView.getText();

                     */
                }
            });

        }

        void bindData(final Book book){
            //bookImageView.setImageURI(book.getImage());
            titleTextView.setText(book.getTitle());
            subtitleTextView.setText(book.getSubtitle());
            authorsTextView.setText("authors: " + book.getAuthors());
            publisherTextView.setText("publisher: " + book.getPublisher());
            isbn10TextView.setText("isbn10: " + book.getIsbn10());
            isbn13TextView.setText("isbn13: " + book.getIsbn13());

            pagesTextView.setText("pages: " + book.getPages());
            yearTextView.setText("year: " + book.getYear());
            ratingTextView.setText("rating: " + book.getRating());
            descTextView.setText(book.getDesc());
            priceTextView.setText("price: " + book.getPrice());

            //imageTextView.setText(book.getImage());
            urlTextView.setText("url: " + book.getUrl());
        }

    }
}
