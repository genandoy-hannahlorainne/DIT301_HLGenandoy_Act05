package com.example.notekeeperapp.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NoteDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE ${Tables.NOTES} (" +
                "${Columns.ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${Columns.TITLE} TEXT NOT NULL, " +
                "${Columns.CONTENT} TEXT NOT NULL, " +
                "${Columns.TIMESTAMP} INTEGER NOT NULL"
                + ")"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${Tables.NOTES}")
        onCreate(db)
    }

    object Tables {
        const val NOTES = "notes"
    }

    object Columns {
        const val ID = "id"
        const val TITLE = "title"
        const val CONTENT = "content"
        const val TIMESTAMP = "timestamp"
    }

    companion object {
        private const val DB_NAME = "notekeeper.db"
        private const val DB_VERSION = 1
    }
}
