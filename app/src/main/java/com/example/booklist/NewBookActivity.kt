package com.example.booklist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.booklist.databinding.ActivityNewBookBinding

class NewBookActivity : AppCompatActivity() {
    //View Binding
    private lateinit var activityNewBookBinding: ActivityNewBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Inflar layout com view Binding
        activityNewBookBinding = ActivityNewBookBinding.inflate(layoutInflater)
        setContentView(activityNewBookBinding.root)

        val book = intent.getParcelableExtra<Book>(MainActivity.EXTRA_BOOK)
        if (book !=null ) {
            //MODO EDICAO
            supportActionBar?.subtitle = "Edit Book"
            with(activityNewBookBinding){
                titleEt.setText(book.title)
                isbnEt.setText(book.isbn)
                firstAuthorEt.setText(book.firstAuthor)
                publishCompEt.setText(book.publishingCompany)
                editionEt.setText(book.edition.toString())
                pagesEt.setText(book.pages.toString())
            }

        } else {
            supportActionBar?.subtitle = "New Book"
        }
    }

    fun onClick (view: View){
        if (view.id == R.id.saveBt){
            val book = Book (
                    activityNewBookBinding.titleEt.text.toString(),
                    activityNewBookBinding.isbnEt.text.toString(),
                    activityNewBookBinding.firstAuthorEt.text.toString(),
                    activityNewBookBinding.publishCompEt.text.toString(),
                    activityNewBookBinding.editionEt.text.toString().toInt(),
                    activityNewBookBinding.pagesEt.text.toString().toInt()
            )
            val resultIntent = Intent()
            resultIntent.putExtra(MainActivity.EXTRA_BOOK,book)
            setResult(RESULT_OK,resultIntent)

            finish()
        }
    }
}