package com.example.notekeeperapp.data

import android.content.ContentValues
import android.content.Context

class NoteRepository(context: Context) {
    private val dbHelper = NoteDatabaseHelper(context)

    fun getAllNotes(): List<Note> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            NoteDatabaseHelper.Tables.NOTES,
            arrayOf(
                NoteDatabaseHelper.Columns.ID,
                NoteDatabaseHelper.Columns.TITLE,
                NoteDatabaseHelper.Columns.CONTENT,
                NoteDatabaseHelper.Columns.TIMESTAMP
            ),
            null, null, null, null,
            NoteDatabaseHelper.Columns.TIMESTAMP + " DESC"
        )
        val list = mutableListOf<Note>()
        cursor.use {
            while (it.moveToNext()) {
                val id = it.getLong(0)
                val title = it.getString(1)
                val content = it.getString(2)
                val ts = it.getLong(3)
                list.add(Note(id, title, content, ts))
            }
        }
        return list
    }

    fun getNoteById(id: Long): Note? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            NoteDatabaseHelper.Tables.NOTES,
            arrayOf(
                NoteDatabaseHelper.Columns.ID,
                NoteDatabaseHelper.Columns.TITLE,
                NoteDatabaseHelper.Columns.CONTENT,
                NoteDatabaseHelper.Columns.TIMESTAMP
            ),
            "${NoteDatabaseHelper.Columns.ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )
        cursor.use {
            if (it.moveToFirst()) {
                return Note(
                    id = it.getLong(0),
                    title = it.getString(1),
                    content = it.getString(2),
                    timestamp = it.getLong(3)
                )
            }
        }
        return null
    }

    fun insertNote(title: String, content: String): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(NoteDatabaseHelper.Columns.TITLE, title)
            put(NoteDatabaseHelper.Columns.CONTENT, content)
            put(NoteDatabaseHelper.Columns.TIMESTAMP, System.currentTimeMillis())
        }
        return db.insert(NoteDatabaseHelper.Tables.NOTES, null, values)
    }

    fun updateNote(id: Long, title: String, content: String): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(NoteDatabaseHelper.Columns.TITLE, title)
            put(NoteDatabaseHelper.Columns.CONTENT, content)
            put(NoteDatabaseHelper.Columns.TIMESTAMP, System.currentTimeMillis())
        }
        return db.update(
            NoteDatabaseHelper.Tables.NOTES,
            values,
            "${NoteDatabaseHelper.Columns.ID}=?",
            arrayOf(id.toString())
        )
    }

    fun deleteNote(id: Long): Int {
        val db = dbHelper.writableDatabase
        return db.delete(
            NoteDatabaseHelper.Tables.NOTES,
            "${NoteDatabaseHelper.Columns.ID}=?",
            arrayOf(id.toString())
        )
    }
}
