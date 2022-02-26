package com.example.nationallibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

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

public class MainActivity extends AppCompatActivity {

    List<ListBooks> _listBooks;
    Button btnSearchBook;
    EditText txtTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtTitle = findViewById(R.id.txtBookName);
        btnSearchBook = findViewById(R.id.btnSearchBooks);
        btnSearchBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = txtTitle.getText().toString();

                SearchBooks(title);


            }
        });

    }

    private List<ListBooks> SearchBooks(String titleIn ){

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
                        _listBooks.add(new ListBooks(
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

}