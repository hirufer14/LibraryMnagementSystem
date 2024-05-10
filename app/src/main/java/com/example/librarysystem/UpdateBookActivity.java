package com.example.librarysystem;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;



public class UpdateBookActivity extends AppCompatActivity {

    private EditText titleEditText, authorEditText, isbnEditText;
    private Button updateButton;
    private DBHelper dbHelper;
    private int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        dbHelper = new DBHelper(this);
        titleEditText = findViewById(R.id.titleEditText);
        authorEditText = findViewById(R.id.authorEditText);
        isbnEditText = findViewById(R.id.isbnEditText);
        updateButton = findViewById(R.id.updateButton);

        // Get the bookId from the intent extras
        Intent intent = getIntent();
        bookId = intent.getIntExtra("bookId", -1);

        if (bookId == -1) {
            Toast.makeText(this, "Invalid book", Toast.LENGTH_SHORT).show();
            finish(); // Close activity if bookId is not provided
        }

        // Fetch book details from database and populate EditText fields
        populateBookDetails();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBook();
            }
        });
    }

    private void populateBookDetails() {
        // Fetch book details from database using bookId
        Cursor cursor = dbHelper.getBookById(bookId);
        if (cursor != null && cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KEY_TITLE));
            String author = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KEY_AUTHOR));
            String isbn = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KEY_ISBN));

            titleEditText.setText(title);
            authorEditText.setText(author);
            isbnEditText.setText(isbn);
            cursor.close(); // Close cursor when done
        }
    }

    private void updateBook() {
        String title = titleEditText.getText().toString().trim();
        String author = authorEditText.getText().toString().trim();
        String isbn = isbnEditText.getText().toString().trim();

        if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int rowsAffected = dbHelper.updateBook(bookId, title, author, isbn);
        if (rowsAffected > 0) {
            Toast.makeText(this, "Book updated successfully", Toast.LENGTH_SHORT).show();
            finish(); // Close activity after updating book
        } else {
            Toast.makeText(this, "Failed to update book", Toast.LENGTH_SHORT).show();
        }
    }
}
