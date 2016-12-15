package com.dee.android.criterioncompletion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.UUID;

public class FilmActivity extends AppCompatActivity {

    public static final String EXTRA_FILM_ID = "com.dee.criterioncompletion.film_id";

    public static Intent newIntent(Context packageContext, UUID filmId) {
        Intent intent = new Intent(packageContext, FilmActivity.class);
        intent.putExtra(EXTRA_FILM_ID, filmId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new FilmFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
