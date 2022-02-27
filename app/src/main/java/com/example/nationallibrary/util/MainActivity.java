package com.example.nationallibrary.util;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nationallibrary.R;
import com.example.nationallibrary.adapters.ListAdapter;
import com.example.nationallibrary.models.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Book> _listBooks;
    Button btnSearchBook;
    EditText txtTitle;

    RecyclerView recyclerView;
    TextView isbn13TextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtTitle = findViewById(R.id.txtBookName);
        btnSearchBook = findViewById(R.id.btnSearchBooks);
        recyclerView = findViewById(R.id.listRecyclerView);

        btnSearchBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String titleToSearch = txtTitle.getText().toString();
                SearchBooks(titleToSearch);
                //Intent intent = new Intent(MainActivity.this, BookDetailsActivity.class);
                //startActivity(intent);


            }
        });

    }

    private List<Book> SearchBooks(String titleIn ){

        _listBooks = new ArrayList<>();

        String url = "https://api.itbook.store/1.0/search/" + titleIn;

        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonObjectBooks = jsonObject.getJSONArray("books");

                    for (int i = 0; i<jsonObjectBooks.length(); i++){
                        JSONObject book = jsonObjectBooks.getJSONObject(i);
                        _listBooks.add(new Book(
                                book.getString("image"),
                                book.getString("title"),
                                book.getString("subtitle"),
                                book.getString("isbn13"),
                                book.getString("price"),
                                book.getString("url")
                        ));
                    }

                    ListAdapter listAdapter = new ListAdapter(_listBooks, MainActivity.this);
                    RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerView.setAdapter(listAdapter);

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

    private void OpenLink(String url){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("url"));
            startActivity(intent);
        } catch (Exception e) {
            Log.d("Error", "Error in the process");
            Toast.makeText(this, "Error in the process", Toast.LENGTH_LONG);
        }

    }

}