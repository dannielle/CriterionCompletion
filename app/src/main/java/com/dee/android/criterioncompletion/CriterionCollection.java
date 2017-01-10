package com.dee.android.criterioncompletion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dee.android.criterioncompletion.database.DbSchema;
import com.dee.android.criterioncompletion.database.FilmBaseHelper;
import com.dee.android.criterioncompletion.database.FilmCursorWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CriterionCollection {

    private Context mContext;

    private static CriterionCollection sCriterionCollection;

    private List<Film> mFilms;
    private FilmBaseHelper mFilmBaseHelper;

    SQLiteDatabase mDatabase;
    private String mOrderBy;

    public static CriterionCollection get(Context context) {
        if (sCriterionCollection == null) {
            sCriterionCollection = new CriterionCollection(context);
        }
        return sCriterionCollection;
    }

    private CriterionCollection(Context context) {
        mContext = context.getApplicationContext();
        mFilmBaseHelper = new FilmBaseHelper(mContext);
        mDatabase = mFilmBaseHelper.getWritableDatabase();
        mFilms = getFilms();
    }

    public List<Film> getFilms() {
        List<Film> films = new ArrayList<>();

        FilmCursorWrapper cursor = queryFilms(null, null, mOrderBy);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                films.add(cursor.getFilm());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return films;
    }

    private FilmCursorWrapper queryFilms(String whereClause, String[] whereArgs, String orderByClause) {
        Cursor cursor = mDatabase.query(
                DbSchema.FilmTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                orderByClause
        );
        return new FilmCursorWrapper(cursor);
    }

    public Film getFilm(UUID id) {
        FilmCursorWrapper cursor = queryFilms(
                DbSchema.FilmTable.Cols.UUID + " = ?",
                new String[] { id.toString() },
                null
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getFilm();
        } finally {
            cursor.close();
        }
    }

    public void updateFilmInDb(Film film) {
        mFilmBaseHelper.updateFilm(film, mDatabase);
    }

    public void watched(Film film, boolean b) {
        film.setHasWatched(b);
        updateFilmInDb(film);
    }

    public void rate(Film film, float v) {
        film.setRating(v);
        updateFilmInDb(film);
    }

    public void isFavorite(Film film, boolean b) {
        film.setFavorite(b);
        updateFilmInDb(film);
    }

    public List<Film> getWatchedFilms() {
        List<Film> watchedFilms = new ArrayList<>();

        FilmCursorWrapper cursor = queryFilms(DbSchema.FilmTable.Cols.HAS_WATCHED + "=1", null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                watchedFilms.add(cursor.getFilm());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return watchedFilms;
    }

    public List<Film> getFavoriteFilms() {
        List<Film> watchedFilms = new ArrayList<>();

        FilmCursorWrapper cursor = queryFilms(DbSchema.FilmTable.Cols.IS_FAVORITE + "=1", null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                watchedFilms.add(cursor.getFilm());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return watchedFilms;
    }

    public void setOrder(String order) {
        switch (order) {
            case "title" :
                mOrderBy = DbSchema.FilmTable.Cols.TITLE + " ASC";
                break;
            case "country" :
                mOrderBy = DbSchema.FilmTable.Cols.COUNTRY + " ASC";
                break;
            case "year" :
                mOrderBy = DbSchema.FilmTable.Cols.YEAR + " ASC";
                break;
            default:
                mOrderBy = null;
        }
        mFilms = getFilms();
    }
}
