package com.yaym.read.data.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yaym.read.data.models.IndustryIdentifier;

import java.lang.reflect.Type;
import java.util.List;

public class IndustryIdListConverter {

        private static final Gson gson = new Gson();

    @TypeConverter
    public static List<IndustryIdentifier> stringToIndustryIdentifiersList(String data) {
        Type listType = new TypeToken<List<IndustryIdentifier>>() {}.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String IndustryIdentifiersListToString(List<IndustryIdentifier> someObjects) {
        return gson.toJson(someObjects);
    }
}
