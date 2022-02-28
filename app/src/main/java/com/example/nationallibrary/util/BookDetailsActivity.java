package com.example.nationallibrary.util;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nationallibrary.adapters.ListDetailsAdapter;
import com.example.nationallibrary.R;
import com.example.nationallibrary.models.Book;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookDetailsActivity extends AppCompatActivity {

    //Book _book;
    List<Book> _listBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        //searchBook("");
        getIncomingIntent();
    }

    private List<Book> searchBook(String isbn13){

        _listBooks = new ArrayList<>();

        String url = "https://api.itbook.store/1.0/books/" + isbn13;

        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonBook = new JSONObject(response);

                    //JSONArray jsonPDF = jsonBook.getJSONArray("books");

                    _listBooks.add(new Book(
                            jsonBook.getString("title")==null?"":jsonBook.getString("title"),
                            jsonBook.getString("subtitle")==null?"":jsonBook.getString("subtitle"),
                            jsonBook.getString("authors")==null?"":jsonBook.getString("authors"),
                            jsonBook.getString("publisher")==null?"":jsonBook.getString("publisher"),
                            jsonBook.getString("isbn10")==null?"":jsonBook.getString("isbn10"),
                            jsonBook.getString("isbn13")==null?"":jsonBook.getString("isbn13"),
                            jsonBook.getString("pages")==null?"":jsonBook.getString("pages"),
                            jsonBook.getString("year")==null?"":jsonBook.getString("year"),
                            jsonBook.getString("rating")==null?"":jsonBook.getString("rating"),
                            jsonBook.getString("desc")==null?"":jsonBook.getString("desc"),
                            jsonBook.getString("price")==null?"":jsonBook.getString("price"),
                            jsonBook.getString("image")==null?"":jsonBook.getString("image"),
                            jsonBook.getString("url")==null?"":jsonBook.getString("url")));


                    ListDetailsAdapter listAdapter = new ListDetailsAdapter(_listBooks, BookDetailsActivity.this);
                    RecyclerView detailRecyclerView = findViewById(R.id.listDetailsRecyclerView);
                    detailRecyclerView.setHasFixedSize(true);
                    detailRecyclerView.setLayoutManager(new LinearLayoutManager(BookDetailsActivity.this));
                    detailRecyclerView.setAdapter(listAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        });

        Volley.newRequestQueue(this).add(postRequest);

        return _listBooks;
    }

    private void getIncomingIntent(){

        if(getIntent().hasExtra("isbn13")){
            searchBook(getIntent().getStringExtra("isbn13"));
        }
    }

}