package com.example.nationallibrary.util;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
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

        _btnSearchBook.setOnClickListener(view -> {

            try {
                if (checkBookName()){
                    _currentPage = 1;
                    _listBooks = SearchBooks();
                    checkListBook();
                    _page.setText(String.valueOf(_currentPage));
                    next.start();
                }

            }catch (Exception e){
                Log.d("Exception", e.getMessage());
            }


        });

        _nextPageTextView.setOnClickListener(view -> {
            _currentPage = nextPage(_currentPage);
            _listBooks = SearchBooks();
            _page.setText(String.valueOf(_currentPage));
            next.start();
        });

        _backPageTextView.setOnClickListener(view -> {
            _currentPage = backPage(_currentPage);
            _listBooks = SearchBooks();
            _page.setText(String.valueOf(_currentPage));
            back.start();
        });


        _txtTitle.setOnKeyListener((view, i, keyEvent) -> {

            try {

                if (keyEvent.getAction()!=KeyEvent.ACTION_DOWN)
                    return true;

                if(i==66){
                    if (checkBookName()){
                        _currentPage = 1;
                        _listBooks = SearchBooks();
                        checkListBook();
                        _page.setText(String.valueOf(_currentPage));
                    }
                }
            }catch (Exception e){
                Log.d("Exception", e.getMessage());
            }
            return false;
        });

        _page.setOnKeyListener((view, i, keyEvent) -> {

            try {
                if(i==66){

                    if (keyEvent.getAction()!=KeyEvent.ACTION_DOWN)
                        return true;

                    if (checkBookName()){
                        _currentPage = Integer.parseInt(_page.getText().toString());
                        _listBooks = SearchBooks();
                        checkListBook();
                    }

                }
            }catch (Exception e){
                Log.d("Exception", e.getMessage());
            }

            return false;
        });

    }

    private boolean checkBookName(){
        boolean nameIsOk = true;

        _infoTextView.setText("");
        _currentTitle = _txtTitle.getText().toString();

        if(!(_txtTitle.getText().length()>1)) {
            _infoTextView.setText(R.string.noBookName);
            nameIsOk = false;
        }

        //_page.setText(String.valueOf(_currentPage));
        return nameIsOk;
    }

    private void checkListBook(){

        try {

            if(_listBooks!=null){
                showPageManager();
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.
                        INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(_txtTitle.getWindowToken(), 0);
            }
            else{

                _infoTextView.setText(R.string.noResults);

            }

        }catch (Exception e){
            Log.d("Exception", e.getMessage());
        }

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

        List<Book> listBooksOut = new ArrayList<>();
        String url = "";
        try {
            if(!(_currentTitle.isEmpty()&&_currentPage==0)){

                url = String.format("https://api.itbook.store/1.0/search/%s/%s", _currentTitle, _currentPage);

            }
        }catch (Exception e){
            Log.d("Exception", "MainActivity > SearchBooks > Exception:" + e.getMessage());
        }



        StringRequest postRequest = new StringRequest(Request.Method.GET, url, response -> {

            try {
                JSONObject jsonObject = new JSONObject(response);

                int totalBooks = Integer.parseInt(jsonObject.getString("total"));

                int maxPages = (totalBooks/10) > 99 ? 100 : (totalBooks/10)+1;
                _maxPages.setText(String.valueOf(maxPages));

                JSONArray jsonObjectBooks = jsonObject.getJSONArray("books");

                for (int i = 0; i<jsonObjectBooks.length(); i++){
                    JSONObject book = jsonObjectBooks.getJSONObject(i);
                    listBooksOut.add(new Book(
                            book.getString("image").isEmpty()?"":book.getString("image"),
                            book.getString("title").isEmpty()?"":book.getString("title"),
                            book.getString("subtitle").isEmpty()?"":book.getString("subtitle"),
                            book.getString("isbn13").isEmpty()?"":book.getString("isbn13"),
                            book.getString("price").isEmpty()?"":book.getString("price"),
                            book.getString("url").isEmpty()?"":book.getString("url")
                    ));
                }

                ListAdapter listAdapter = new ListAdapter(listBooksOut, MainActivity.this);
                RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(listAdapter);



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            Log.e("Error", error.getMessage());
            _infoTextView.setText(R.string.pleaseCheckInternetConnection);
        });

        Volley.newRequestQueue(this).add(postRequest);

        return listBooksOut;
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