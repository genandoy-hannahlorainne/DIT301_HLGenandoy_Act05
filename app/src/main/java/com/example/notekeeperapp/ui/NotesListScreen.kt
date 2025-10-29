@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.notekeeperapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notekeeperapp.data.Note

@Composable
fun NotesListScreen(
    viewModel: NotesViewModel,
    onAdd: () -> Unit,
    onOpen: (Long) -> Unit
) {
    val notes by viewModel.notes.collectAsState()
    LaunchedEffect(Unit) { viewModel.loadNotes() }
    Scaffold(
        topBar = { TopAppBar(title = { Text("Notes") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) { Icon(Icons.Default.Add, contentDescription = null) }
        }
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            if (notes.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("No notes yet")
                    Text("Tap + to add one", style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                NotesList(
                    notes = notes,
                    onOpen = onOpen,
                    onDelete = { viewModel.deleteNote(it) }
                )
            }
        }
    }
}

@Composable
private fun NotesList(
    notes: List<Note>,
    onOpen: (Long) -> Unit,
    onDelete: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(notes, key = { it.id }) { note ->
            ListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpen(note.id) },
                headlineContent = { Text(note.title) },
                supportingContent = { Text(note.content, maxLines = 2) },
                trailingContent = {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        IconButton(onClick = { onOpen(note.id) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit note")
                        }
                        IconButton(onClick = { onDelete(note.id) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete note")
                        }
                    }
                }
            )
        }
    }
}
