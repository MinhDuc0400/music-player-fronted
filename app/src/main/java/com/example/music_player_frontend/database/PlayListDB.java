package com.example.music_player_frontend.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.music_player_frontend.model.Song;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PlayListDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "favoritesongs.db";
    private static int DATABASE_VERSION = 1;
    public PlayListDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE song(" +
                "id TEXT PRIMARY KEY," +
                "title TEXT," +
                "thumbnail TEXT," +
                "url TEXT," +
                "artists TEXT," +
                "time INTEGER" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public List<Song> getAll() {
        List<Song> itemList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("song", null, null, null, null, null, null);
        while (cursor != null && cursor.moveToNext()) {
            String id = cursor.getString(0);
            String title = cursor.getString(1);
            String thumbnail = cursor.getString(2);
            String url = cursor.getString(3);
            String artists = cursor.getString(4);
            int time = cursor.getInt(5);
//            String id, String title, String thumbnail, String url, String artists, int time
            Song item = new Song(id, title, thumbnail, url, artists, time);
            itemList.add(item);
        }
        Collections.reverse(itemList);
        return itemList;
    }

    public long addItem(Song item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", item.getId());
        contentValues.put("title", item.getTitle());
        contentValues.put("thumbnail", item.getThumbnail());
        contentValues.put("url", item.getUrl());
        contentValues.put("artists", item.getArtists());
        contentValues.put("time", item.getTime());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("song", null, contentValues);
    }

//    public int editItem(Item item) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("title", item.getTitle());
//        contentValues.put("categopry", item.getCategory());
//        contentValues.put("date", item.getDate());
//        contentValues.put("price", item.getPrice());
//        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
//        String whereClause = "id=?";
//        String[] whereArgs = {Integer.toString(item.getId())};
//        return sqLiteDatabase.update("items", contentValues, whereClause, whereArgs);
//    }

    public int deleteItem(String id) {
        String whereClause = "id=?";
        String[] whereArgs = {id};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("song", whereClause, whereArgs);
    }

    public boolean isSongLiked(String id) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {id};
        Cursor cursor = sqLiteDatabase.query("song", null, whereClause, whereArgs, null, null, null);
        if (cursor != null && cursor.getCount() != 0) {
            return true;
        }
        return false;
    }

//    public List<Item> getItemByDate(String date) {
//        List<Item> itemList = new ArrayList<>();
//        String whereClause = "date like ?";
//        String[] whereArgs = {date};
//        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//        Cursor cursor = sqLiteDatabase.query("items", null, whereClause, whereArgs, null, null, null);
//        while (cursor != null && cursor.moveToNext()) {
//            int id = cursor.getInt(0);
//            String title = cursor.getString(1);
//            String category = cursor.getString(2);
//            String price = cursor.getString(3);
//            Item item = new Item(id, title, category, date, price);
//            itemList.add(item);
//        }
//        return itemList;
//    }
//    public List<Item> getItemByTitle(String key) {
//        List<Item> itemList = new ArrayList<>();
//        String whereClause = "title like ?";
//        String[] whereArgs = {"%" + key + "%"};
//        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//        Cursor cursor = sqLiteDatabase.query("items", null, whereClause, whereArgs, null, null, null);
//        while (cursor != null && cursor.moveToNext()) {
//            int id = cursor.getInt(0);
//            String title = cursor.getString(1);
//            String category = cursor.getString(2);
//            String price = cursor.getString(3);
//            String date = cursor.getString(4);
//            Item item = new Item(id, title, category, date, price);
//            itemList.add(item);
//        }
//        return itemList;
//    }

//    public List<Item> getItemByCategory(String key) {
//        List<Item> itemList = new ArrayList<>();
//        String whereClause = "categopry like ?";
//        String[] whereArgs = {key};
//        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//        Cursor cursor = sqLiteDatabase.query("items", null, whereClause, whereArgs, null, null, null);
//        while (cursor != null && cursor.moveToNext()) {
//            int id = cursor.getInt(0);
//            String title = cursor.getString(1);
//            String category = cursor.getString(2);
//            String price = cursor.getString(3);
//            String date = cursor.getString(4);
//            Item item = new Item(id, title, category, date, price);
//            itemList.add(item);
//        }
//        return itemList;
//    }

//    public List<Item> getItemFromDateToDate(String from, String to) {
//        List<Item> itemList = new ArrayList<>();
//        String whereClause = "date between ? and ?";
//        String[] whereArgs = {from.trim(), to.trim()};
//        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//        Cursor cursor = sqLiteDatabase.query("items", null, whereClause, whereArgs, null, null, null);
//        while (cursor != null && cursor.moveToNext()) {
//            int id = cursor.getInt(0);
//            String title = cursor.getString(1);
//            String category = cursor.getString(2);
//            String price = cursor.getString(3);
//            String date = cursor.getString(4);
//            Item item = new Item(id, title, category, date, price);
//            itemList.add(item);
//        }
//        return itemList;
//    }
}
