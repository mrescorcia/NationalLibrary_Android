package com.example.nationallibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    private List<ListBooks> booksList;
    private LayoutInflater mLayoutInflater;
    private Context context;

    public ListAdapter(List<ListBooks> booksList, Context context){
        this.booksList = booksList;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getItemCount() { return booksList.size(); }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mLayoutInflater.inflate(R.layout.list_books, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position){
        holder.bindData(booksList.get(position));
    }

    public void setItems(List<ListBooks> items){ booksList = items; }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView bookImageView;
        TextView titleTextView, subtitleTextView, isbn13TextView, priceTextView, urlTextView;

        ViewHolder(View itemView){
            super(itemView);
            bookImageView = itemView.findViewById(R.id.bookImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            subtitleTextView = itemView.findViewById(R.id.subtitleTextView);
            isbn13TextView = itemView.findViewById(R.id.isbn13TextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            urlTextView = itemView.findViewById(R.id.urlTextView);

        }

        void bindData(final ListBooks book){
            titleTextView.setText(book.getTitle());
            subtitleTextView.setText(book.getSubtitle());
            isbn13TextView.setText(book.getIsbn13());
            priceTextView.setText(book.getPrice());
            urlTextView.setText(book.getUrl());
        }

    }

}
