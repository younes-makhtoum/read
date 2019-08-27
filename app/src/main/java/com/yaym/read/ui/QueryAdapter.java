package com.yaym.read.ui;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.yaym.read.R;
import com.yaym.read.data.models.Book;
import com.yaym.read.databinding.BookListItemBinding;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

public class QueryAdapter extends RecyclerView.Adapter<QueryAdapter.MyViewHolder> {

    // Tag for log messages
    private static final String LOG_TAG = QueryAdapter.class.getName();

    private List<Book> booksList;
    @Inject
    Context context;

    public QueryAdapter(Context context) {
        this.context = context;
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

        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(view -> {
            // open another activity on item click
            Intent intent = new Intent(context, DetailActivity.class);
            Log.v(LOG_TAG, "LOG// clickedBook is : " + booksList.get(position));
            Log.v(LOG_TAG, "LOG// clickedBook's title is : " + booksList.get(position).getVolumeInfo().getTitle());
            // put book object in the Intent
            intent.putExtra("Message", "I am an intent message to pass between activities");
            intent.putExtra("Book", Parcels.wrap(booksList.get(position)));
            // start Intent
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (booksList == null) {
            return 0;
        } else {
            return booksList.size();
        }
    }

    // Helper method to set the actual book list into the RecyclerView on the activity
    public void setBookInfoList(List<Book> booksList) {
        this.booksList = booksList;
    }

    public interface BookAdapterListener {
        void onBookClicked(Book book);
    }
}