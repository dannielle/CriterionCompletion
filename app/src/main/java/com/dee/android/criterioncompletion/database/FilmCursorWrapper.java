package com.dee.android.criterioncompletion.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.dee.android.criterioncompletion.Film;
import com.dee.android.criterioncompletion.database.DbSchema.FilmTable.Cols;

import java.util.UUID;

public class FilmCursorWrapper extends CursorWrapper {

    public FilmCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Film getFilm() {
        String uuidString = getString(getColumnIndex(Cols.UUID));
        String title = getString(getColumnIndex(Cols.TITLE));
        String year = getString(getColumnIndex(Cols.YEAR));
        String director = getString(getColumnIndex(Cols.DIRECTOR));
        String country = getString(getColumnIndex(Cols.COUNTRY));
        int isFavorite = getInt(getColumnIndex(Cols.IS_FAVORITE));
        int hasWatched = getInt(getColumnIndex(Cols.HAS_WATCHED));
        float rating = getFloat(getColumnIndex(Cols.RATING));

        Film film = new Film(UUID.fromString(uuidString));
        film.setTitle(title);
        film.setYear(year);
        film.setDirector(director);
        film.setCountry(country);
        film.setFavorite(isFavorite == 1);
        film.setHasWatched(hasWatched == 1);
        film.setRating(rating);

        return film;
    }
}
