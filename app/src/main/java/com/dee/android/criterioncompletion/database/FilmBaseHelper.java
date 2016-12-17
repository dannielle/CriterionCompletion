package com.dee.android.criterioncompletion.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dee.android.criterioncompletion.Film;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;

public class FilmBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "all_films.db";
    private static final int VERSION = 1;
    private final Context mContext;

    public FilmBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DbSchema.FilmTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                DbSchema.FilmTable.Cols.UUID + ", " +
                DbSchema.FilmTable.Cols.TITLE + ", " +
                DbSchema.FilmTable.Cols.YEAR + ", " +
                DbSchema.FilmTable.Cols.DIRECTOR + ", " +
                DbSchema.FilmTable.Cols.COUNTRY + ", " +
                DbSchema.FilmTable.Cols.IS_FAVORITE + ", " +
                DbSchema.FilmTable.Cols.HAS_WATCHED +
                ")"
        );

        String next[] = {};

        try {
            CSVReader reader = new CSVReader(new InputStreamReader(mContext.getAssets().open("full_criterion_collection_list.csv")));

            for(;;) {
                next = reader.readNext();
                if (next != null) {
                    Film film = new Film();
                    film.setTitle(next[0]);
                    film.setDirector(next[1]);
                    film.setYear(next[2]);
                    film.setCountry(next[3]);
                    addFilm(film, db);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addFilm(Film f, SQLiteDatabase db) {
        ContentValues cv = getContentValues(f);
        db.insert(DbSchema.FilmTable.NAME, null, cv);
    }

    private static ContentValues getContentValues(Film film) {
        ContentValues cv = new ContentValues();
        cv.put(DbSchema.FilmTable.Cols.UUID, film.getId().toString());
        cv.put(DbSchema.FilmTable.Cols.TITLE, film.getTitle());
        cv.put(DbSchema.FilmTable.Cols.YEAR, film.getYear());
        cv.put(DbSchema.FilmTable.Cols.DIRECTOR, film.getDirector());
        cv.put(DbSchema.FilmTable.Cols.COUNTRY, film.getCountry());
        cv.put(DbSchema.FilmTable.Cols.IS_FAVORITE, film.isFavorite() ? 1 : 0);
        cv.put(DbSchema.FilmTable.Cols.HAS_WATCHED, film.hasWatched() ? 1 : 0);
        return cv;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void updateFilm(Film film, SQLiteDatabase db) {
        String uuidString = film.getId().toString();
        ContentValues values = getContentValues(film);

        db.update(DbSchema.FilmTable.NAME, values,
                DbSchema.FilmTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }
}