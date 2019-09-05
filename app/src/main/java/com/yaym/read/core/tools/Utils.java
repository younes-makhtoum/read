package com.yaym.read.core.tools;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yaym.read.R;
import com.yaym.read.data.models.IndustryIdentifier;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static void configureRecyclerView(RecyclerView recyclerView, GridLayoutManager gridLayoutManager, SpacesItemDecoration decoration) {

        recyclerView.setLayoutManager(gridLayoutManager);
        // Enable performance optimizations (significantly smoother scrolling),
        // by setting the following parameters on the RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        // Add space between grid items in the RecyclerView,
        recyclerView.addItemDecoration(decoration);
    }

    public static String changeBookCoverThumbnail(String thumbnailUrl) {
        return thumbnailUrl.replace("zoom=1", "zoom=2");
    }

    public static String convertStringsArrayListToString(ArrayList<String> stringArrayList, String separator, Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        int position = 1;
        for(String s : stringArrayList) {
            if(position < stringArrayList.size()) {
                stringBuilder.append(s);
                        if (separator.equals(context.getResources().getString(R.string.new_line_separator))) {
                            stringBuilder.append(System.getProperty("line.separator"))
                                    .append(System.getProperty("line.separator"));
                        } else if (separator.equals(context.getResources().getString(R.string.comma_separator))) {
                            stringBuilder.append(context.getResources().getString(R.string.comma_space));
                        }
            } else {
                stringBuilder.append(s);
            }
            position++;
        }
        return stringBuilder.toString();
    }

    public static String convertIndustryIdentifiersArrayListToString(List<IndustryIdentifier> industryIdentifierArrayList) {
        StringBuilder stringBuilder = new StringBuilder();
        int position = 1;
        for(IndustryIdentifier industryIdentifier : industryIdentifierArrayList) {
            if(position < industryIdentifierArrayList.size()) {
                stringBuilder
                        .append(industryIdentifier.getType())
                        .append(" : ")
                        .append(industryIdentifier.getIdentifier())
                        .append(System.getProperty("line.separator"))
                        .append(System.getProperty("line.separator"));
            } else {
                stringBuilder
                        .append(industryIdentifier.getType())
                        .append(" : ")
                        .append(industryIdentifier.getIdentifier());
            }
            position++;
        }
        return stringBuilder.toString();
    }
}