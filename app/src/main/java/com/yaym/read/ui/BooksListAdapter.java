package com.yaym.read.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yaym.read.R;
import com.yaym.read.core.tools.Utils;
import com.yaym.read.data.models.Book;
import com.yaym.read.databinding.BookListItemBinding;
import com.yaym.read.ui.detail.DetailActivity;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

public class BooksListAdapter extends RecyclerView.Adapter<BooksListAdapter.MyViewHolder> {

    // Tag for log messages
    private static final String LOG_TAG = BooksListAdapter.class.getName();

    private List<Book> booksList;
    @Inject
    Context context;

    public BooksListAdapter(Context context) {
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

        String thumbnailUrl = booksList.get(position).getVolumeInfo().getImageLinks().getThumbnail();
        // Load book thumbnail in imageview when available
        if  (thumbnailUrl != null) {
            RequestOptions options = new RequestOptions()
                    .override(200, 600)
                    .placeholder(R.drawable.ic_book_placeholder)
                    .error(R.drawable.ic_book_placeholder);
            Glide.with(context)
                    .load(Utils.changeBookCoverThumbnail(thumbnailUrl))
                    .apply(options)
                    .into(holder.binding.bookThumbnail);
        }
        // Implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(view -> {
            // open another activity on item click
            Intent intent = new Intent(context, DetailActivity.class);
            // put book object in the Intent
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
}