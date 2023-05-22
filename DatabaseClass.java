package com.example.a71p;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseClass extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LostFoundDb.db";
    private static final String TABLE_NAME = "lost_found";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_IS_LOST_OR_FOUND = "is_lost_or_found";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_LOCATION = "location";

    public DatabaseClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_IS_LOST_OR_FOUND + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_LOCATION + " TEXT)";

        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Insert a new LostAndFoundModel into the database.
     *
     * @param lostAndFound The LostAndFoundModel to insert.
     * @return The ID of the inserted row.
     */
    public long insertData(LostAndFound lostAndFound) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, lostAndFound.getName());
        values.put(COLUMN_IS_LOST_OR_FOUND, lostAndFound.getIsLostOrFound());
        values.put(COLUMN_PHONE, lostAndFound.getPhone());
        values.put(COLUMN_DESCRIPTION, lostAndFound.getDescription());
        values.put(COLUMN_DATE, lostAndFound.getDate());
        values.put(COLUMN_LOCATION, lostAndFound.getLocation());

        long id = db.insert(TABLE_NAME, null, values);
        db.close();

        return id;
    }

    /**
     * Retrieve all LostAndFoundModel objects from the database.
     *
     * @return An ArrayList of LostAndFoundModel objects.
     */
    public ArrayList<LostAndFound> getData() {
        ArrayList<LostAndFound> data = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
                int lostOrFoundIndex = cursor.getColumnIndex(COLUMN_IS_LOST_OR_FOUND);
                int phoneIndex = cursor.getColumnIndex(COLUMN_PHONE);
                int descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION);
                int dateIndex = cursor.getColumnIndex(COLUMN_DATE);
                int locationIndex = cursor.getColumnIndex(COLUMN_LOCATION);

                LostAndFound lostAndFound = new LostAndFound(
                        cursor.getString(idIndex),
                        cursor.getString(nameIndex),
                        cursor.getString(phoneIndex),
                        cursor.getString(descriptionIndex),
                        cursor.getString(dateIndex),
                        cursor.getString(locationIndex),
                        cursor.getInt(lostOrFoundIndex)
                );
                data.add(lostAndFound);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return data;
    }

    /**
     * Retrieve a LostAndFoundModel by its ID from the database.
     *
     * @param id The ID of the LostAndFoundModel to retrieve.
     * @return The corresponding LostAndFoundModel, or null if not found.
     */
    public LostAndFound getDataById(String id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?", new String[]{id});

        LostAndFound lostAndFound = null;
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            int lostOrFoundIndex = cursor.getColumnIndex(COLUMN_IS_LOST_OR_FOUND);
            int phoneIndex = cursor.getColumnIndex(COLUMN_PHONE);
            int descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION);
            int dateIndex = cursor.getColumnIndex(COLUMN_DATE);
            int locationIndex = cursor.getColumnIndex(COLUMN_LOCATION);

            lostAndFound = new LostAndFound(
                    cursor.getString(idIndex),
                    cursor.getString(nameIndex),
                    cursor.getString(phoneIndex),
                    cursor.getString(descriptionIndex),
                    cursor.getString(dateIndex),
                    cursor.getString(locationIndex),
                    cursor.getInt(lostOrFoundIndex)
            );
        }

        cursor.close();
        db.close();

        return lostAndFound;
    }

    /**
     * Delete a row from the database by its ID.
     *
     * @param id The ID of the row to delete.
     * @return The number of rows affected (should be 1 if successful).
     */
    public int deleteDataById(int id) {
        SQLiteDatabase db = getWritableDatabase();
        int deletedRows = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return deletedRows;
    }
}
