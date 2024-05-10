package com.example.librarysystem;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;



public class BookManagementActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private ListView listViewBooks;
    private Button addBookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_management);

        dbHelper = new DBHelper(this);
        listViewBooks = findViewById(R.id.listViewBooks);
        addBookButton = findViewById(R.id.addBookButton);

        displayAllBooks();

        listViewBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected book from the cursor
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                int bookId = cursor.getInt(cursor.getColumnIndexOrThrow("_id")); // Correct column name

                // Start UpdateBookActivity and pass bookId as extra
                Intent intent = new Intent(BookManagementActivity.this, UpdateBookActivity.class);
                intent.putExtra("bookId", bookId);
                startActivity(intent);
            }
        });

        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookManagementActivity.this, AddBookActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        displayAllBooks(); // Refresh the ListView adapter
    }

    private void displayAllBooks() {
        Cursor cursor = dbHelper.getAllBooks();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No books available", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] columns = new String[]{DBHelper.KEY_TITLE, DBHelper.KEY_AUTHOR, DBHelper.KEY_ISBN};
        int[] to = new int[]{R.id.textViewTitle, R.id.textViewAuthor, R.id.textViewISBN};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.book_item, cursor, columns, to, 0);
        listViewBooks.setAdapter(adapter);
    }
}

