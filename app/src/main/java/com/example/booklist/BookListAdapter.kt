package com.example.booklist

import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.booklist.databinding.BookLayoutBinding

class BookListAdapter(private val activity: MainActivity, resource: Int, bookList: List<Book>) : ArrayAdapter<Book>(activity, resource, bookList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val book = getItem(position)
        if (convertView == null) {
            val bookLayoutBinding = BookLayoutBinding.inflate(activity.layoutInflater)
            view = bookLayoutBinding.root
            val bookHolder = BookHolder(
                    bookLayoutBinding.titleTv,
                    bookLayoutBinding.firstAuthorTv,
                    bookLayoutBinding.publishCompTv
            )
            view.tag = bookHolder
        }
        (view?.tag as BookHolder).titleTv.text = book?.title
        (view.tag as BookHolder).firstAuthorTv.text = book?.firstAuthor
        (view.tag as BookHolder).publishCompTv.text = book?.publishingCompany

        return view
    }

    private data class BookHolder(val titleTv: TextView,
                                  val firstAuthorTv: TextView,
                                  val publishCompTv: TextView)
}