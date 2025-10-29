@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.notekeeperapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditNoteScreen(
    viewModel: NotesViewModel,
    noteId: Long?,
    onSaved: () -> Unit,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    LaunchedEffect(noteId) {
        if (noteId != null && noteId > 0) {
            val note = viewModel.getNote(noteId)
            if (note != null) {
                title = note.title
                content = note.content
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(if ((noteId ?: 0L) > 0) "Edit Note" else "New Note") }) }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Content") },
                modifier = Modifier.weight(1f).fillMaxSize().padding(bottom = 16.dp)
            )
            Button(onClick = {
                if ((noteId ?: 0L) > 0) {
                    viewModel.updateNote(noteId!!, title, content, onSaved)
                } else {
                    viewModel.addNote(title, content, onSaved)
                }
            }) {
                Text("Save")
            }
        }
    }
}
