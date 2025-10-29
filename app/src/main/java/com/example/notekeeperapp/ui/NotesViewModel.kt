package com.example.notekeeperapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notekeeperapp.data.Note
import com.example.notekeeperapp.data.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotesViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = NoteRepository(app)

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    fun loadNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            val all = repo.getAllNotes()
            withContext(Dispatchers.Main) { _notes.value = all }
        }
    }

    suspend fun getNote(id: Long): Note? = withContext(Dispatchers.IO) {
        repo.getNoteById(id)
    }

    fun addNote(title: String, content: String, onDone: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertNote(title, content)
            val all = repo.getAllNotes()
            withContext(Dispatchers.Main) {
                _notes.value = all
                onDone()
            }
        }
    }

    fun updateNote(id: Long, title: String, content: String, onDone: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateNote(id, title, content)
            val all = repo.getAllNotes()
            withContext(Dispatchers.Main) {
                _notes.value = all
                onDone()
            }
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteNote(id)
            val all = repo.getAllNotes()
            withContext(Dispatchers.Main) { _notes.value = all }
        }
    }
}
