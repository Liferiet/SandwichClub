package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        if (!alsoKnownAsList.isEmpty()) {
            TextView alsoKnownAsTextView = findViewById(R.id.detail_also_known_tv);
            TextView labelAlsoKnowsAsTextView = findViewById(R.id.label_detail_also_known_tv);
            for (int i = 0; i < alsoKnownAsList.size() - 1; i++) {
                alsoKnownAsTextView.append(alsoKnownAsList.get(i) + ", ");
            }
            alsoKnownAsTextView.append(alsoKnownAsList.get(alsoKnownAsList.size() - 1));

            alsoKnownAsTextView.setVisibility(TextView.VISIBLE);
            labelAlsoKnowsAsTextView.setVisibility(TextView.VISIBLE);
        }

        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (!"".equals(placeOfOrigin)) {
            TextView placeOfOriginTextView = findViewById(R.id.detail_origin_tv);
            TextView labelPlaceOfOriginTextView = findViewById(R.id.label_detail_origin_tv);
            placeOfOriginTextView.setText(placeOfOrigin);

            placeOfOriginTextView.setVisibility(TextView.VISIBLE);
            labelPlaceOfOriginTextView.setVisibility(TextView.VISIBLE);
        }

        String description = sandwich.getDescription();
        if (!"".equals(description)) {
            TextView descriptionTextView = findViewById(R.id.detail_description_tv);
            TextView labelDescriptionTextView = findViewById(R.id.label_detail_description_tv);
            descriptionTextView.setText(description);

            descriptionTextView.setVisibility(TextView.VISIBLE);
            labelDescriptionTextView.setVisibility(TextView.VISIBLE);
        }

        List<String> ingredientsList = sandwich.getIngredients();
        if (!ingredientsList.isEmpty()) {
            TextView ingredientsTextView = findViewById(R.id.detail_ingredients_tv);
            TextView labelIngredientsTextView = findViewById(R.id.label_detail_ingredients_tv);
            for (int i = 0; i < ingredientsList.size() - 1; i++) {
                ingredientsTextView.append(ingredientsList.get(i) + ", ");
            }
            ingredientsTextView.append(ingredientsList.get(ingredientsList.size() - 1));

            ingredientsTextView.setVisibility(TextView.VISIBLE);
            labelIngredientsTextView.setVisibility(TextView.VISIBLE);
        }



    }
}
