## Reflection
- **How did you implement CRUD using SQLite?**
  - Defined a table in `NoteDatabaseHelper.kt` (extends `SQLiteOpenHelper`) with columns: `id`, `title`, `content`, `timestamp`.
  - Wrote CRUD in `NoteRepository.kt` using `insert`, `update`, `delete`, and `query` on the `SQLiteDatabase`.
  - Exposed data through `NotesViewModel.kt` using `StateFlow` and coroutines; UI calls ViewModel methods from `NotesListScreen.kt` and `EditNoteScreen.kt`.
  - Navigation is set up in `MainActivity.kt` to open add/edit screens.

- **What challenges did you face in maintaining data persistence?**
  - Ensuring DB work is off the main thread; solved with coroutines (`Dispatchers.IO`).
  - Keeping UI in sync after CRUD; solved by reloading and publishing via `StateFlow` in the ViewModel.
  - Handling app restarts; data persists in SQLite and repopulates on `loadNotes()` in the list screen.
  - Managing schema changes; `onUpgrade()` currently drops/recreates the table for simplicity.

- **How could you improve performance or UI design in future versions?**
  - Migrate to Room ORM with DAOs and `Flow` for reactive queries and safer migrations.
  - Add Paging if the note list grows large, and full-text search with indexing on `title`.
  - Improve UX: confirmation dialogs for delete, Snackbar undo, multiline text editor, better timestamp formatting, and accessibility labels.
  - Add tests (instrumented/unit) for repository and ViewModel.
