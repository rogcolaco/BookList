package com.example.booklist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.booklist.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Data source adapter
    private List<Book> bookList;

    //adpter do ListView
    private ArrayAdapter<String> bookListAdapter;

    //View Binding
    private ActivityMainBinding activityMainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // infla layout com view binding e setar
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        //Instanciar o bookList
        bookList = new ArrayList();
        initializeBookList();

        //Instanciar bookList
        bookListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, bookList);

        //setando o bookList com o bookList Adapter
        activityMainBinding.bookListLv.setAdapter(bookListAdapter);
    }

    // Método que popula o bookList para teste
    private void initializeBookList() {
        for (int i = 0; i < 10; i++) {
            bookList.add(
                    new Book(
                            "Titulo" + i,
                            "ISBN" + i,
                            "Autor" + i,
                            "Editora" + i,
                            i,
                            i
                    )
            );
        }
    }
}