package com.example.suzette;

// RecipeActivity.java

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity {

    // Array of sample text
    private String[] sampleRecipes = {
            "Caesar Salad",
            "Omelette",
            "Mac & Cheese",
            "Sesame Chicken",
            "Churros",
    };
  
    private String[] sampleDescriptions = {
            "Ingredients: Lettuce, Croutons, Parmesan Cheese, Caesar Dressing, Olive Oil, Lemon Juice, Garlic, Mustard, Salt, Pepper",
            "Ingredients: Eggs, Butter, Salt, Pepper",
            "Ingredients: Pasta, Milk, Cheese, Butter, Flour, Salt, Pepper, Paprika, Garlic Powder ",
            "Ingredients: Chicken Breasts, Cornstarch, Eggs, Salt, Pepper, Soy Sauce, Brown Sugar, Sesame Oil, Minced Garlic Apple Cider Vinegar, Vegetable Oil",
            "Ingredients: Sugar, Salt, Butter, Flour, Cinnamon, Flour, Baking Soda",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_activity);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        for (int i = 0; i < sampleRecipes.length; i++) {
            ItemFragment fragment = ItemFragment.newInstance(sampleRecipes[i], sampleDescriptions[i]);
            fragmentTransaction.add(R.id.container, fragment);
        }

        fragmentTransaction.commit();
    }

    protected void onStart() {
        super.onStart();
    }
    protected void onRestart(){
        super.onRestart();
    }
    protected void onResume() {
        super.onResume();
    }
    protected void onPause() {
        super.onPause();
    }
    protected void onStop() {
        super.onStop();
    }
    protected void onDestroy() {
        super.onDestroy();
    }
    public void startRecipeActivity(View v) {
        Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
        startActivity(intent);
    }
    public void startCookingActivity(View v) {
        Intent intent = new Intent(MainActivity.this, CookingActivity.class);
        startActivity(intent);
        //setContentView(R.layout.activity_cooking_activity);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        for (int i = 0; i < sampleRecipes.length; i++) {
            ItemFragment fragment = ItemFragment.newInstance(sampleRecipes[i], sampleDescriptions[i]);
            fragmentTransaction.add(R.id.container, fragment);
        }

        fragmentTransaction.commit();
    }


}