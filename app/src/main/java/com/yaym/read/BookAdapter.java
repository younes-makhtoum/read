package com.yaym.read;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {

    private List<Book> booksList;
    private int rawLayout;
    private Context mContext;
    private ItemClickListener clickListener;

    public BookAdapter(List<Book> booksList, int rawLayout, Context context) {
        this.booksList = booksList;
        this.rawLayout = rawLayout;
        this.mContext = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleTextView, yearTextView, authorTextView, descriptionView;
        //private ItemClickListener clickListener;
        private MyViewHolder(View view) {
            super(view);
            titleTextView = (TextView) view.findViewById(R.id.book_title);
            yearTextView = (TextView) view.findViewById(R.id.book_year);
            authorTextView = (TextView) view.findViewById(R.id.book_authors);
            descriptionView = (TextView) view.findViewById(R.id.book_text_snippet);
            // Attach a click listener to the entire row view
            view.setOnClickListener(this);
        }
        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Book currentBook = booksList.get(position);

        holder.titleTextView.setText(convertString(currentBook.getTitle()));
        holder.yearTextView.setText(currentBook.getYear());

        if (currentBook.getAuthors().size() > 0) {
            StringBuilder authorsBuilder = new StringBuilder();
            boolean first = true;
            for (int i = 0; i < currentBook.getAuthors().size(); i++) {
                if (first) {
                    first = false;
                } else {
                    authorsBuilder.append(System.getProperty("line.separator"));
                }
                authorsBuilder.append(currentBook.getAuthors().get(i));
            }
            holder.authorTextView.setText(authorsBuilder.toString());
        }
        else {
            holder.authorTextView.setVisibility(View.GONE);
        }

        if (!currentBook.getTextSnippet().isEmpty()) {
            // Display the text_snippet of the current book in that TextView
            holder.descriptionView.setText(convertString(currentBook.getTextSnippet()));
        }
        else {
            holder.descriptionView.setVisibility(View.GONE);
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
}