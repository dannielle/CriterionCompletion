package com.dee.android.criterioncompletion.database;

public class DbSchema {

    public static final class FilmTable {
        public static final String NAME = "films";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String YEAR = "year";
            public static final String DIRECTOR = "director";
            public static final String COUNTRY = "country";
            public static final String IS_FAVORITE = "is_favorite";
            public static final String HAS_WATCHED = "has_watched";
        }
    }
}
