package com.example.suzette;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    //private List<itemData> getInitData() {
      //  List<itemData> list = new ArrayList<>();
        //return list;
    //}
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
    public void startCookingActivity(View v) {
        Intent intent = new Intent(RecipeActivity.this, CookingActivity.class);
        startActivity(intent);
    }
    public void startMainActivity(View v) {
        Intent intent = new Intent(RecipeActivity.this, MainActivity.class);
        startActivity(intent);
    }



}
