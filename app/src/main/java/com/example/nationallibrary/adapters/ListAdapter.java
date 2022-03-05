package com.example.nationallibrary.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nationallibrary.R;
import com.example.nationallibrary.models.Book;
import com.example.nationallibrary.util.BookDetailsActivity;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    private List<Book> booksList;
    private LayoutInflater mLayoutInflater;
    private Context context;

    public ListAdapter(List<Book> booksList, Context context){
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

    public void setItems(List<Book> items){ booksList = items; }

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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isbn13TextView = v.findViewById(R.id.isbn13TextView);
                    String isbn13 = (String) isbn13TextView.getText();

                    Intent intent = new Intent(context, BookDetailsActivity.class);
                    intent.putExtra("isbn13", isbn13);
                    context.startActivity(intent);

                }
            });

        }

        void bindData(final Book book){

            bookImageView = book.getImage(book.getUrlImage(), bookImageView);
            titleTextView.setText(book.getTitle());
            subtitleTextView.setText(book.getSubtitle());
            isbn13TextView.setText(book.getIsbn13());
            priceTextView.setText(book.getPrice());
            urlTextView.setText(book.getUrl());

        }

    }

}
