package com.dee.android.criterioncompletion;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CriterionCollection {

    private static CriterionCollection sCriterionCollection;

    private List<Film> mFilms;

    public static CriterionCollection get(Context context) {
        if (sCriterionCollection == null) {
            sCriterionCollection = new CriterionCollection(context);
        }
        return sCriterionCollection;
    }

    private CriterionCollection(Context context) {
        mFilms = new ArrayList<>();
        for (int i=0; i < 100; i++) {
            Film film = new Film();
            film.setTitle("Seven Samurai");
            film.setRating(3);
            mFilms.add(film);
        }

    }

    public List<Film> getFilms() {
        return mFilms;
    }

    public Film getFilm(UUID id) {
        for (Film film : mFilms) {
            if (film.getId().equals(id)) {
                return film;
            }
        }
        return null;
    }

    public void fave(Film film) {
        film.setFavorite(true);
    }

    public List<Film> getFavoriteFilms() {
        List<Film> faves = new ArrayList<>();
        for(Film film : mFilms) {
            if (film.isFavorite()){
                faves.add(film);
            }
        }
        return faves;
    }

    public void watched(Film film, boolean b) {
        film.setHasWatched(b);
    }

    public List<Film> getWatchedFilms() {
        List<Film> watched = new ArrayList<>();
        for(Film film : mFilms) {
            if (film.hasWatched()){
                watched.add(film);
            }
        }
        return watched;
    }

    public void rate(Film film, float v) {
        film.setRating(v);
    }
}
