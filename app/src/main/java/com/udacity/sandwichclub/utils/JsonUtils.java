package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        final String NAME = "name";
        final String MAIN_NAME = "mainName";
        final String ALSO_KNOWN_AS = "alsoKnownAs";
        final String PLACE_OF_ORIGIN = "placeOfOrigin";
        final String DESCRIPTION = "description";
        final String IMAGE = "image";
        final String INGREDIENTS = "ingredients";

        JSONObject sandwichJson = new JSONObject(json);
        Sandwich sandwich = new Sandwich();

        JSONObject sandwichNames = sandwichJson.getJSONObject(NAME);
        sandwich.setMainName(sandwichNames.getString(MAIN_NAME));

        JSONArray alsoKnownAsArray = sandwichNames.getJSONArray(ALSO_KNOWN_AS);
        List<String> alsoKnowsAsList = new ArrayList<>(alsoKnownAsArray.length());

        for (int i = 0; i < alsoKnownAsArray.length(); i++) {
            alsoKnowsAsList.add(alsoKnownAsArray.getString(i));
        }
        sandwich.setAlsoKnownAs(alsoKnowsAsList);

        sandwich.setPlaceOfOrigin(sandwichJson.getString(PLACE_OF_ORIGIN));
        sandwich.setDescription(sandwichJson.getString(DESCRIPTION));
        sandwich.setImage(sandwichJson.getString(IMAGE));

        JSONArray ingredientsArray = sandwichJson.getJSONArray(INGREDIENTS);
        List<String> ingredientsList = new ArrayList<>(ingredientsArray.length());

        for (int i = 0; i < ingredientsArray.length(); i++) {
            ingredientsList.add(ingredientsArray.getString(i));
        }
        sandwich.setIngredients(ingredientsList);

        return sandwich;
    }
}
