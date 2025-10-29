package com.example.notekeeperapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notekeeperapp.ui.theme.NoteKeeperAppTheme
import com.example.notekeeperapp.ui.EditNoteScreen
import com.example.notekeeperapp.ui.NotesListScreen
import com.example.notekeeperapp.ui.NotesViewModel
import com.example.notekeeperapp.ui.NotesViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteKeeperAppTheme {
                NoteKeeperApp()
            }
        }
    }
}

@Composable
fun NoteKeeperApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val vm: NotesViewModel = viewModel(factory = NotesViewModelFactory(context.applicationContext as android.app.Application))

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            NotesListScreen(
                viewModel = vm,
                onAdd = { navController.navigate("edit") },
                onOpen = { id -> navController.navigate("edit/$id") }
            )
        }
        composable(
            route = "edit/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType; defaultValue = 0L; nullable = false })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id")
            EditNoteScreen(
                viewModel = vm,
                noteId = id?.takeIf { it > 0 },
                onSaved = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }
        composable("edit") {
            EditNoteScreen(
                viewModel = vm,
                noteId = null,
                onSaved = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }
    }
}