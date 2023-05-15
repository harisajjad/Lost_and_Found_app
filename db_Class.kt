package com.example.task71

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class db_Class(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "LostFoundDb.db"
        private const val TABLE_NAME = "lost_found"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_IS_LOST_OR_FOUND = "is_lost_or_found"
        private const val COLUMN_PHONE = "phone"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_LOCATION = "location"
    }

    // Called when the database is created for the first time
    override fun onCreate(db: SQLiteDatabase) {
        // Create the table with the specified columns
        val createTableQuery = "CREATE TABLE $TABLE_NAME " +
                "($COLUMN_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_IS_LOST_OR_FOUND TEXT, " +
                "$COLUMN_PHONE TEXT, " +
                "$COLUMN_DESCRIPTION TEXT, " +
                "$COLUMN_DATE TEXT, " +
                "$COLUMN_LOCATION TEXT)"

        db.execSQL(createTableQuery)
    }

    // Called when the database needs to be upgraded
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop the existing table if it exists
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        // Create a new table by calling onCreate()
        onCreate(db)
    }

    // Insert data into the database
    fun insertData(lostAndFound: LostAndFound): Long {
        val values = ContentValues()
        values.put(COLUMN_NAME, lostAndFound.name)
        values.put(COLUMN_IS_LOST_OR_FOUND, lostAndFound.isLostOrFound)
        values.put(COLUMN_PHONE, lostAndFound.phone)
        values.put(COLUMN_DESCRIPTION, lostAndFound.description)
        values.put(COLUMN_DATE, lostAndFound.date)
        values.put(COLUMN_LOCATION, lostAndFound.location)

        val db = this.writableDatabase
        val id = db.insert(TABLE_NAME, null, values)
        db.close()

        return id
    }

    // Retrieve data from database
    fun getData(): ArrayList<LostAndFound> {
        val data = ArrayList<LostAndFound>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"

        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                // Retrieve column index
                val idIndex = cursor.getColumnIndex(COLUMN_ID)
                val nameIndex = cursor.getColumnIndex(COLUMN_NAME)
                val lostOrFoundIndex = cursor.getColumnIndex(COLUMN_IS_LOST_OR_FOUND)
                val phoneIndex = cursor.getColumnIndex(COLUMN_PHONE)
                val descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION)
                val dateIndex = cursor.getColumnIndex(COLUMN_DATE)
                val locationIndex = cursor.getColumnIndex(COLUMN_LOCATION)

                if (
                // Checking columns index are valid
                    idIndex != -1 &&
                    nameIndex != -1 &&
                    lostOrFoundIndex != -1 &&
                    phoneIndex != -1 &&
                    descriptionIndex != -1 &&
                    dateIndex != -1 &&
                    locationIndex != -1
                ) {
                    // Create Lost_Found object and
                    // add to the data list

                    data.add(
                        LostAndFound(
                            id = cursor.getInt(idIndex),
                            name = cursor.getString(nameIndex),
                            phone = cursor.getString(phoneIndex),
                            isLostOrFound = cursor.getString(lostOrFoundIndex),
                            description = cursor.getString(descriptionIndex),
                            date = cursor.getString(dateIndex),
                            location = cursor.getString(locationIndex)
                        )
                    )
                }
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return data
    }

    // Retrieve by ID
    fun getDataById(id: Int): LostAndFound? {
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $id"

        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        var lostAndFound: LostAndFound? = null
        if (cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex(COLUMN_ID)
            val nameIndex = cursor.getColumnIndex(COLUMN_NAME)
            val lostOrFoundIndex = cursor.getColumnIndex(COLUMN_IS_LOST_OR_FOUND)
            val phoneIndex = cursor.getColumnIndex(COLUMN_PHONE)
            val descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION)
            val dateIndex = cursor.getColumnIndex(COLUMN_DATE)
            val locationIndex = cursor.getColumnIndex(COLUMN_LOCATION)

            if (
                idIndex != -1 &&
                nameIndex != -1 &&
                lostOrFoundIndex != -1 &&
                phoneIndex != -1 &&
                descriptionIndex != -1 &&
                dateIndex != -1 &&
                locationIndex != -1
            ) {
                // Create Lost_Found object
                lostAndFound = LostAndFound(
                        id = cursor.getInt(idIndex),
                        name = cursor.getString(nameIndex),
                        phone = cursor.getString(phoneIndex),
                        isLostOrFound = cursor.getString(lostOrFoundIndex),
                        description = cursor.getString(descriptionIndex),
                        date = cursor.getString(dateIndex),
                        location = cursor.getString(locationIndex)
                    )
            }
        }

        cursor.close()
        db.close()

        return lostAndFound
    }

    // Delete by ID
    fun deleteDataById(id: Int): Int {
        val db = this.writableDatabase
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(id.toString())
        val deletedRows = db.delete(TABLE_NAME, selection, selectionArgs)
        db.close()

        return deletedRows
    }
}
