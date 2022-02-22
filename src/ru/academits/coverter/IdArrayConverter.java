package ru.academits.coverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class IdArrayConverter {
    private final Gson gson = new GsonBuilder().create();

    public int[] convertFormJson(String contactJson) {
        return gson.fromJson(contactJson, int[].class);
    }
}
