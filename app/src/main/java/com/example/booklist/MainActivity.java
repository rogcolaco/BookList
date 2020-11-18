package com.example.booklist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.booklist.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Data source adapter
    private List<Book> bookList;

    //adpter do ListView
    private BookListAdapter bookListAdapter;

    //View Binding
    private ActivityMainBinding activityMainBinding;

    public static final String EXTRA_BOOK = "EXTRA_BOOK";

    private final int NEW_BOOK_REQUEST_CODE = 0;
    private final int EDIT_BOOK_REQUEST_CODE = 1;

    //book a ser deletado
    private Book oldBook;

    //action para visualização
    public static final String ACTION_VIEW_BOOK = "ACTION_VIEW_BOOK";


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
        bookListAdapter = new BookListAdapter(this, R.layout.book_layout, bookList);

        //setando o bookList com o bookList Adapter
        activityMainBinding.bookListLv.setAdapter(bookListAdapter);

        //Registrar booklistview com menu de contexto
        registerForContextMenu(activityMainBinding.bookListLv);

        activityMainBinding.bookListLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = bookList.get(position);

                Intent viewBookIntent = new Intent(MainActivity.this, NewBookActivity.class);
                viewBookIntent.putExtra(EXTRA_BOOK,book);
                viewBookIntent.setAction(ACTION_VIEW_BOOK);
                startActivity(viewBookIntent);
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.newBookMi){
            Intent newBookIntent = new Intent(this, NewBookActivity.class);
            startActivityForResult(newBookIntent, NEW_BOOK_REQUEST_CODE);
            return true;
        }
        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu_main, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Book book = bookList.get(info.position);
        switch (item.getItemId()){
            case R.id.editBookMi:
                Intent editBookIntent = new Intent(this, NewBookActivity.class);
                editBookIntent.putExtra(EXTRA_BOOK,book);
                oldBook = book;
                startActivityForResult(editBookIntent, EDIT_BOOK_REQUEST_CODE);
                return true;
            case R.id.deleteBookMi:
                bookListAdapter.remove(book);
                Toast.makeText(this, "Livro removido com sucesso", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_BOOK_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            Book newBook = data.getParcelableExtra(MainActivity.EXTRA_BOOK);
            bookList.add(newBook);
            bookListAdapter.notifyDataSetChanged();
        }
        else {
            if (requestCode == EDIT_BOOK_REQUEST_CODE && resultCode == RESULT_OK && data != null){
                Book editedBook = data.getParcelableExtra(MainActivity.EXTRA_BOOK);
                bookListAdapter.remove(oldBook);
                bookListAdapter.add(editedBook);
            }
        }
    }
}