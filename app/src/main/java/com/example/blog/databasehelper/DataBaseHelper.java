package com.example.blog.databasehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Basic.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Basic";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "basic_title";
    private static final String COLUMN_ANCHOR = "basic_anchor";
    private static final String COLUMN_PAGES = "basic_pages";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_ANCHOR + " TEXT, " +
                        COLUMN_PAGES + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void addData(String title, String anchor, int pages){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_ANCHOR, anchor);
        cv.put(COLUMN_PAGES, pages);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "ADD Successfully", Toast.LENGTH_SHORT).show();
        }
    }
    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);

        }
        return cursor;
    }
    public void updateData(String row_id, String title, String anchor, String pages){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_ANCHOR, anchor);
        cv.put(COLUMN_PAGES, pages);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == -1) Toast.makeText(context, "Failed Update", Toast.LENGTH_SHORT).show();
        else Toast.makeText(context, "Successfully Update", Toast.LENGTH_SHORT).show();
    }
    public void deleteData(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) Toast.makeText(context, "Failed Delete", Toast.LENGTH_SHORT).show();
        else Toast.makeText(context, "Successfully Delete", Toast.LENGTH_SHORT).show();
    }
    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}

