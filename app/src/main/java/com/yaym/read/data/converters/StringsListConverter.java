package com.yaym.read.data.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class StringsListConverter {

    private static final Gson gson = new Gson();

    @TypeConverter
    public static ArrayList<String> stringToList(String data) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String ListToString(ArrayList<String> list) {
        return gson.toJson(list);
    }
}