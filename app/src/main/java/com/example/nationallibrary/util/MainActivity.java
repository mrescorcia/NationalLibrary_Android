package com.example.nationallibrary.util;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
    String _currentTitle;
    int _currentPage = 1;


    Button _btnSearchBook;
    EditText _txtTitle;
    TextView _nextPageTextView;
    TextView _backPageTextView;
    EditText _page;
    TextView _maxPages;
    TextView _pageLabel;
    TextView _ofLabel;
    TextView _infoTextView;

    RecyclerView _recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        _txtTitle = findViewById(R.id.txtBookName);
        _btnSearchBook = findViewById(R.id.btnSearchBooks);
        _recyclerView = findViewById(R.id.listRecyclerView);

        _page = findViewById(R.id.currentPageEditText);
        _backPageTextView = findViewById(R.id.backPageTextView);
        _nextPageTextView = findViewById(R.id.nextPageTextView);
        _maxPages = findViewById(R.id.pageMaxTextView);
        _pageLabel = findViewById(R.id.pageTextView);
        _ofLabel = findViewById(R.id.ofTextView);
        _infoTextView = findViewById(R.id.infoTextView);

        final MediaPlayer next = MediaPlayer.create(this, R.raw.click);
        final MediaPlayer back = MediaPlayer.create(this, R.raw.click2);

        _btnSearchBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (checkBookName()){
                        _currentPage = 1;
                        SearchBooks();
                        checkListBook();
                        _page.setText(String.valueOf(_currentPage));
                        next.start();
                    }

                }catch (Exception e){
                    Log.d("Exception", e.getMessage());
                }


            }
        });

        _nextPageTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _currentPage = nextPage(_currentPage);
                SearchBooks();
                _page.setText(String.valueOf(_currentPage));
                next.start();
            }
        });

        _backPageTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _currentPage = backPage(_currentPage);
                SearchBooks();
                _page.setText(String.valueOf(_currentPage));
                back.start();
            }
        });


        _txtTitle.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                try {

                    if (keyEvent.getAction()!=KeyEvent.ACTION_DOWN)
                        return true;

                    if(i==66){
                        if (checkBookName()){
                            _currentPage = 1;
                            SearchBooks();
                            checkListBook();
                            _page.setText(String.valueOf(_currentPage));
                        }
                    }
                }catch (Exception e){
                    Log.d("Exception", e.getMessage());
                }
                return false;
            }
        });

        _page.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                try {
                    if(i==66){

                        if (keyEvent.getAction()!=KeyEvent.ACTION_DOWN)
                            return true;

                        if (checkBookName()){
                            _currentPage = Integer.parseInt(_page.getText().toString());
                            SearchBooks();
                            checkListBook();
                        }

                    }
                }catch (Exception e){
                    Log.d("Exception", e.getMessage());
                }

                return false;
            }
        });

    }

    private boolean checkBookName(){
        Boolean nameIsOk = true;

        _infoTextView.setText("");
        _currentTitle = _txtTitle.getText().toString();

        if(_txtTitle.getText().length()>1) nameIsOk = true;
        else {
            _infoTextView.setText("No Book's Name");
            return nameIsOk = false;
        }

        //_page.setText(String.valueOf(_currentPage));
        return nameIsOk;
    }

    private Boolean checkListBook(){

        Boolean listBookOk = true;

        try {

            if(_listBooks!=null){
                showPageManager();
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.
                        INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(_txtTitle.getWindowToken(), 0);
            }
            else{

                _infoTextView.setText("No Results");
                return listBookOk = false;
            }

        }catch (Exception e){
            Log.d("Exception", e.getMessage());
        }
        return listBookOk;
    }

    private void showPageManager() {
        _nextPageTextView.setVisibility(View.VISIBLE);
        _backPageTextView.setVisibility(View.VISIBLE);
        _page.setVisibility(View.VISIBLE);
        _maxPages.setVisibility(View.VISIBLE);
        _pageLabel.setVisibility(View.VISIBLE);
        _ofLabel.setVisibility(View.VISIBLE);
    }

    private List<Book> SearchBooks(){

        _listBooks = new ArrayList<>();
        String url = "";
        try {
            if(!(_currentTitle.isEmpty()&&_currentPage==0)){
                if(_currentPage==0){
                    url = String.format("https://api.itbook.store/1.0/search/%s", _currentTitle);
                }else{
                    url = String.format("https://api.itbook.store/1.0/search/%s/%s", _currentTitle, _currentPage);
                }
            }
        }catch (Exception e){
            Log.d("Exception", "MainActivity > SearchBooks > Exception:" + e.getMessage());
        }



        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    int totalBooks = Integer.parseInt(jsonObject.getString("total"));

                    int maxPages = (totalBooks/10) > 99 ? 100 : (totalBooks/10);
                    _maxPages.setText(String.valueOf(maxPages));

                    JSONArray jsonObjectBooks = jsonObject.getJSONArray("books");

                    for (int i = 0; i<jsonObjectBooks.length(); i++){
                        JSONObject book = jsonObjectBooks.getJSONObject(i);
                        _listBooks.add(new Book(
                                book.getString("image")==null?"":book.getString("image"),
                                book.getString("title")==null?"":book.getString("title"),
                                book.getString("subtitle")==null?"":book.getString("subtitle"),
                                book.getString("isbn13")==null?"":book.getString("isbn13"),
                                book.getString("price")==null?"":book.getString("price"),
                                book.getString("url")==null?"":book.getString("url")
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
                _infoTextView.setText("Please Check INTERNET Connection");
            }
        });

        Volley.newRequestQueue(this).add(postRequest);

        return _listBooks;
    }

    private int nextPage(int currentPage){
        int nextPage=0;
        try {
            if(currentPage < Integer.parseInt(_maxPages.getText().toString())) nextPage = currentPage + 1;
            else nextPage = currentPage;
        }catch (Exception e){
            Log.d("Error", e.getMessage());
        }

        return nextPage;
    }

    private int backPage(int currentPage){
        int backPage=0;
        try {
            if(currentPage>=2) backPage = currentPage - 1;
            else backPage = currentPage;
        }catch (Exception e){
            Log.d("Error", e.getMessage());
        }
        return backPage;
    }

}