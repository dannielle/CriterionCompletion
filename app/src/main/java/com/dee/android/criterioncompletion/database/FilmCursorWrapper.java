package com.dee.android.criterioncompletion.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.dee.android.criterioncompletion.Film;

import java.util.UUID;

public class FilmCursorWrapper extends CursorWrapper {

    public FilmCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Film getFilm() {
        String uuidString = getString(getColumnIndex(DbSchema.FilmTable.Cols.UUID));
        String title = getString(getColumnIndex(DbSchema.FilmTable.Cols.TITLE));
        String year = getString(getColumnIndex(DbSchema.FilmTable.Cols.YEAR));
        String director = getString(getColumnIndex(DbSchema.FilmTable.Cols.DIRECTOR));
        String country = getString(getColumnIndex(DbSchema.FilmTable.Cols.COUNTRY));
        int isFavorite = getInt(getColumnIndex(DbSchema.FilmTable.Cols.IS_FAVORITE));
        int hasWatched = getInt(getColumnIndex(DbSchema.FilmTable.Cols.HAS_WATCHED));

        Film film = new Film(UUID.fromString(uuidString));
        film.setTitle(title);
        film.setYear(year);
        film.setDirector(director);
        film.setCountry(country);
        film.setFavorite(isFavorite == 1);
        film.setHasWatched(hasWatched == 1);

        return film;
    }
}
