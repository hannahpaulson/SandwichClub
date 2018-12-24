package com.example.hannahpaulson.sandwichclub;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hannahpaulson.sandwichclub.Utils.JsonUtils;
import com.example.hannahpaulson.sandwichclub.model.Sandwich;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.Iterator;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    ImageView ingredientsIv;
    TextView originTv;
    TextView originTvLabel;
    TextView descriptionTv;
    TextView descriptionTvLabel;
    TextView ingredientsTv;
    TextView ingredientsTvLabel;
    TextView akaTv;
    TextView akaTvLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        originTv = findViewById(R.id.origin_tv);
        originTvLabel = findViewById(R.id.origin_tv_label);
        descriptionTv = findViewById(R.id.description_tv);
        descriptionTvLabel = findViewById(R.id.description_tv_label);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        ingredientsTvLabel = findViewById(R.id.ingredients_tv_label);
        akaTv = findViewById(R.id.also_known_tv);
        akaTvLabel = findViewById(R.id.also_known_tv_label);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
            populateUI(sandwich);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        ingredientsIv.setVisibility(sandwich.getImage().isEmpty() ? View.GONE : View.VISIBLE);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
        setVisibilityForViews(originTv, originTvLabel, sandwich.getPlaceOfOrigin());
        setVisibilityForViews(descriptionTv, descriptionTvLabel, sandwich.getDescription());
        addItemsToTextView(akaTv, sandwich.getAlsoKnownAs(), akaTvLabel);
        addItemsToTextView(ingredientsTv, sandwich.getIngredients(), ingredientsTvLabel);
    }

    private void setVisibilityForViews(TextView textView, TextView label, String text) {
        label.setVisibility(text.isEmpty() ? View.GONE : View.VISIBLE);
        textView.setVisibility(text.isEmpty() ? View.GONE : View.VISIBLE);
        textView.setText(text);
    }

    private void addItemsToTextView(TextView textView, List<String> items, TextView labelView) {
        labelView.setVisibility(items.isEmpty() ? View.GONE : View.VISIBLE);
        textView.setVisibility(items.isEmpty() ? View.GONE : View.VISIBLE);
        Iterator<String> it = items.iterator();
        if (it.hasNext()) {
            textView.append(it.next());
        }
        while (it.hasNext()) {
            textView.append(", " + it.next());
        }
    }
}
