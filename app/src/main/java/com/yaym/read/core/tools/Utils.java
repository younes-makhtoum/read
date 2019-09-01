package com.yaym.read.core.tools;

import android.content.Context;

import com.yaym.read.R;
import com.yaym.read.data.models.IndustryIdentifier;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class Utils {

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