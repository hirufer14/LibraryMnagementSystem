package com.example.librarysystem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class AddBookActivity extends AppCompatActivity {

    private EditText titleEditText, authorEditText, isbnEditText;
    private Button saveButton;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        dbHelper = new DBHelper(this);
        titleEditText = findViewById(R.id.titleEditText);
        authorEditText = findViewById(R.id.authorEditText);
        isbnEditText = findViewById(R.id.isbnEditText);
        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBook();
            }
        });
    }

    private void addBook() {
        String title = titleEditText.getText().toString().trim();
        String author = authorEditText.getText().toString().trim();
        String isbn = isbnEditText.getText().toString().trim();

        if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = dbHelper.addBook(title, author, isbn);
        if (result != -1) {
            Toast.makeText(this, "Book added successfully", Toast.LENGTH_SHORT).show();
            finish(); // Close activity after adding book
        } else {
            Toast.makeText(this, "Failed to add book", Toast.LENGTH_SHORT).show();
        }
    }
}
