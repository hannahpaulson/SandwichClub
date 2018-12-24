package com.example.hannahpaulson.sandwichclub.Utils;

import android.util.Log;

import com.example.hannahpaulson.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject sandwich = new JSONObject(json);
        JSONObject name = sandwich.getJSONObject("name");
        return new Sandwich(name.getString("mainName"),
                jsonToList(name.getJSONArray("alsoKnownAs")),
                sandwich.getString("placeOfOrigin"),
                sandwich.getString("description"),
                sandwich.getString("image"),
                jsonToList(sandwich.getJSONArray("ingredients")));
    }

    private static List<String> jsonToList(JSONArray jsonArray) throws JSONException {
        List<String> listData = new ArrayList<>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                listData.add(jsonArray.getString(i));
            }
        }
        return listData;
    }
}
