package com.libin.pocketbook.app.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.libin.pocketbook.app.PocketApplication;
import com.libin.pocketbook.app.database.ReminderContract.FavoriteEntry;
import com.libin.pocketbook.app.model.PocketReminder;

import org.androidannotations.annotations.EBean;

import java.util.HashMap;

/**
 * Created by libin on 8/5/14.
 */
@EBean(scope = EBean.Scope.Singleton)
public class ReminderExecutor {

    public long add(PocketReminder pocketReminder) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(FavoriteEntry.COLUMN_TITLE, pocketReminder.getTitle());
        contentValues.put(FavoriteEntry.COLUMN_PACKAGE_NAME, pocketReminder.getPackageName());
        contentValues.put(FavoriteEntry.COLUMN_DATE, pocketReminder.getDate());
        contentValues.put(FavoriteEntry.COLUMN_URL , pocketReminder.getUrl());
        return PocketApplication
                .getAppInstance()
                .getSQLiteInstance()
                .insert(FavoriteEntry.TABLE_NAME, null, contentValues);
    }

    public void update(PocketReminder pocketReminder) {
        ContentValues contentValues = new ContentValues();
        String whereClause = FavoriteEntry._ID + "= ? ";
        String[] whereArgs = new String[]{String.valueOf(pocketReminder.getId())};

        PocketApplication
                .getAppInstance()
                .getSQLiteInstance().update(FavoriteEntry.TABLE_NAME, contentValues, whereClause, whereArgs);
    }

    public void remove(PocketReminder pocketReminder) {
        String whereClause = FavoriteEntry._ID + "= ? ";
        String[] whereArgs = new String[]{String.valueOf(pocketReminder.getId())};

        PocketApplication
                .getAppInstance()
                .getSQLiteInstance().delete(FavoriteEntry.TABLE_NAME, whereClause, whereArgs);
    }

    public void removeAll(String name) {
        String whereClause = FavoriteEntry.COLUMN_PACKAGE_NAME + "= ? ";
        String[] whereArgs = new String[]{name};

        PocketApplication
                .getAppInstance()
                .getSQLiteInstance()
                .delete(FavoriteEntry.TABLE_NAME, whereClause, whereArgs);
    }

    public HashMap<Long, PocketReminder> getAll(String query) {
        HashMap<Long, PocketReminder> reminderHashMap = new HashMap<Long, PocketReminder>();
        String[] queryList = {
                FavoriteEntry._ID,
                FavoriteEntry.COLUMN_TITLE,
                FavoriteEntry.COLUMN_PACKAGE_NAME,
                FavoriteEntry.COLUMN_DATE,
                FavoriteEntry.COLUMN_URL};
        String where = null;

        if (query != null) {
            where = FavoriteEntry.COLUMN_DATE + " = '" + query + "'";
        }

        Cursor cursor = PocketApplication
                .getAppInstance()
                .getSQLiteInstance()
                .query(FavoriteEntry.TABLE_NAME,
                        queryList, where, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                PocketReminder pocketReminder = new PocketReminder();
                int index = 0;

                index = cursor.getColumnIndex(FavoriteEntry._ID);
                pocketReminder.setId(Long.valueOf(cursor.getString(index)));
                index = cursor.getColumnIndex(FavoriteEntry.COLUMN_TITLE);
                pocketReminder.setTitle(cursor.getString(index));
                index = cursor.getColumnIndex(FavoriteEntry.COLUMN_PACKAGE_NAME);
                pocketReminder.setPackageName(cursor.getString(index));
                index = cursor.getColumnIndex(FavoriteEntry.COLUMN_URL);
                pocketReminder.setUrl(cursor.getString(index));
                index = cursor.getColumnIndex(FavoriteEntry.COLUMN_DATE);
                pocketReminder.setDate(cursor.getString(index));
                reminderHashMap.put(pocketReminder.getId(), pocketReminder);
            }
            while (cursor.moveToNext());

        }
        if (cursor != null) {
            cursor.close();
        }
        return reminderHashMap;
    }

}
