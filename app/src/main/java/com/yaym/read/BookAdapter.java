package com.yaym.read;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yaym.read.databinding.BookListItemBinding;

import java.util.List;

import static com.yaym.read.Constants.LINE_SEPARATOR;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {

    private List<Book> booksList;
    private BookAdapterListener bookAdapterListener;

    public BookAdapter(List<Book> booksList, BookAdapterListener bookAdapterListener) {
        this.booksList = booksList;
        this.bookAdapterListener = bookAdapterListener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        BookListItemBinding itemBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.book_list_item, parent, false);
        return new MyViewHolder(itemBinding);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // Store the binding
        private BookListItemBinding binding;

        private MyViewHolder(final BookListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.binding.bookTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookAdapterListener != null) {
                    bookAdapterListener.onBookClicked(booksList.get(position));
                }
            }
        });


        Book currentBook = booksList.get(position);

        holder.binding.bookTitle.setText(convertString(currentBook.getTitle()));
        holder.binding.bookYear.setText(currentBook.getYear());

        if (currentBook.getAuthors().size() > 0) {
            StringBuilder authorsBuilder = new StringBuilder();
            boolean first = true;
            for (int i = 0; i < currentBook.getAuthors().size(); i++) {
                if (first) {
                    first = false;
                } else {
                    authorsBuilder.append(System.getProperty(LINE_SEPARATOR));
                }
                authorsBuilder.append(currentBook.getAuthors().get(i));
            }
            holder.binding.bookAuthors.setText(authorsBuilder.toString());
        }
        else {
            holder.binding.bookAuthors.setVisibility(View.GONE);
        }

        if (!currentBook.getTextSnippet().isEmpty()) {
            // Display the text_snippet of the current book in that TextView
            holder.binding.bookTextSnippet.setText(convertString(currentBook.getTextSnippet()));
        }
        else {
            holder.binding.bookTextSnippet.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    // Helper method to set the actual book list into the recyclerview on the activity
    public void setBookInfoList(List<Book> booksList) {
        this.booksList = booksList;
    }

    private String convertString(String string) {
        return Html.fromHtml(string,Html.FROM_HTML_MODE_LEGACY).toString();
    }

    public interface BookAdapterListener {
        void onBookClicked(Book book);
    }
}