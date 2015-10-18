package com.libin.pocketbook.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.libin.pocketbook.app.database.SQLiteAsyncTask;

/**
 * Created by libin on 8/5/14.
 */
public class PocketApplication extends Application {

    private static PocketApplication appInstance;

    private SharedPreferences preferences;

    private SQLiteDatabase mSQLiteDatabase = null;

    public static PocketApplication getAppInstance() {
        return appInstance;
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        initPreference();
        initSQLite();
    }

    private void initSQLite() {
        // create an sqlite task instance to access the database
        SQLiteAsyncTask sqLiteAsyncTask = new SQLiteAsyncTask(getApplicationContext());
        mSQLiteDatabase = (SQLiteDatabase) sqLiteAsyncTask.loadInBackground();
    }

    public SQLiteDatabase getSQLiteInstance() {
        return mSQLiteDatabase;
    }

    private void initPreference() {
        preferences = getSharedPreferences("private preference", MODE_PRIVATE);
    }

}
