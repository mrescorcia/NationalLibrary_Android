package com.example.nationallibrary;

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

import org.json.JSONArray;
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
        SearchBook("");
    }

    private List<Book> SearchBook(String isbn13){

        _listBooks = new ArrayList<>();

        String url = "https://api.itbook.store/1.0/books/" + "9781484211830";

        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonBook = new JSONObject(response);

                    //JSONArray jsonPDF = jsonBook.getJSONArray("books");

                    _listBooks.add(new Book(
                            jsonBook.getString("title"),
                            jsonBook.getString("subtitle"),
                            jsonBook.getString("authors"),
                            jsonBook.getString("publisher"),
                            jsonBook.getString("isbn10"),
                            jsonBook.getString("isbn13"),
                            jsonBook.getString("pages"),
                            jsonBook.getString("year"),
                            jsonBook.getString("rating"),
                            jsonBook.getString("desc"),
                            jsonBook.getString("price"),
                            jsonBook.getString("image"),
                            jsonBook.getString("url")));


                    ListAdapter listAdapter = new ListAdapter(_listBooks, BookDetailsActivity.this);
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

}