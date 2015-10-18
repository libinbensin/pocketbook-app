package com.pocketbook.app.database;

import android.provider.BaseColumns;

/**
 * Created by libin on 8/5/14.
 */
public final class ReminderContract {
    private ReminderContract() {

    }

    public abstract static class FavoriteEntry implements BaseColumns {
        public static final String TABLE_NAME = "REMINDERS";
        public static final String COLUMN_TITLE = "TITLE";
        public static final String COLUMN_URL = "URL";
        public static final String COLUMN_PACKAGE_NAME = "PACKAGE_NAME";
        public static final String COLUMN_DATE = "DATE";
    }

    private static final String CREATE_TABLE = "CREATE TABLE";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS";
    private static final String INTEGER_PRIMARY_KEY = "INTEGER PRIMARY KEY";
    public static final String TEXT_TYPE = " TEXT";
    public static final String INTEGER_TYPE = " INTEGER";
    public static final String BLOB_TYPE = " BLOB";
    public static final String COMMA_SEP = ",";
    public static final String SPACE = " ";
    public static final String OPEN_BRACKET = "(";
    public static final String CLOSE_BRACKET = ")";
    public static final String UNIQUE = "UNIQUE";

    public static final String CREATE_FAVORITE_TABLE = CREATE_TABLE + SPACE +
            FavoriteEntry.TABLE_NAME + SPACE + OPEN_BRACKET + FavoriteEntry._ID + SPACE +
            INTEGER_PRIMARY_KEY +
            COMMA_SEP + FavoriteEntry.COLUMN_TITLE + TEXT_TYPE +
            COMMA_SEP + FavoriteEntry.COLUMN_URL + TEXT_TYPE +
            COMMA_SEP + FavoriteEntry.COLUMN_DATE + TEXT_TYPE +
            COMMA_SEP + FavoriteEntry.COLUMN_PACKAGE_NAME + TEXT_TYPE + SPACE +
            CLOSE_BRACKET;

    public static final String DELETE_FAVORITE_TABLE = DROP_TABLE + SPACE + FavoriteEntry.TABLE_NAME;

}