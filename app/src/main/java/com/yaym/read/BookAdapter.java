package com.yaym.read;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yaym.read.databinding.BookListItemBinding;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {

    private List<Book> booksList;
    private final BookAdapterListener bookAdapterListener;

    public BookAdapter(List<Book> booksList, BookAdapterListener bookAdapterListener) {
        this.booksList = booksList;
        this.bookAdapterListener = bookAdapterListener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        BookListItemBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.book_list_item, parent, false);
        return new MyViewHolder(binding);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // Store the binding
        private final BookListItemBinding binding;

        private MyViewHolder(final BookListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.binding.setBook(booksList.get(position));
        holder.binding.bookThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookAdapterListener != null) {
                    bookAdapterListener.onBookClicked(booksList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    // Helper method to set the actual book list into the recyclerview on the activity
    public void setBookInfoList(List<Book> booksList) {
        this.booksList = booksList;
    }

    public interface BookAdapterListener {
        void onBookClicked(Book book);
    }
}