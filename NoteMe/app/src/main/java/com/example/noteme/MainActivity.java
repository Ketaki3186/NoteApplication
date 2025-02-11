package com.example.noteme;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private LinearLayout notesContainer;
    private List<Note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        notesContainer = findViewById(R.id.notesContainer);
        Button saveButton = findViewById(R.id.saveButton);

        noteList = new ArrayList<>();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        loadNotes();
        displayNotes();
    }

    private void displayNotes() {
        for (Note note : noteList) {
            createNoteView(note);
        }
    }

    private void loadNotes() {
        noteList = databaseHelper.getAllNotes();
    }

    private void saveNote() {
        EditText titleEditText = findViewById(R.id.titleEditText);
        EditText contentEditText = findViewById(R.id.contentEditText);
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();

        if (!title.isEmpty() && !content.isEmpty()) {
            Note note = new Note();
            note.setTitle(title);
            note.setContent(content);

            databaseHelper.addNote(note);
            noteList.add(note);
            createNoteView(note);
            clearInputFields();
        }
    }

    private void clearInputFields() {
        EditText titleEditText = findViewById(R.id.titleEditText);
        EditText contentEditText = findViewById(R.id.contentEditText);
        titleEditText.getText().clear();
        contentEditText.getText().clear();
    }

    private void createNoteView(final Note note) {
        // Implement the method to create a view for the note
    }

    // Implement other methods such as deleteNoteAndRefresh, showDeleteDialog, etc.

    @Override
    protected void onDestroy() {
        databaseHelper.close();
        super.onDestroy();
    }
}
