package com.example.librarysystem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddMemberActivity extends AppCompatActivity {

    private EditText nameEditText, contactEditText;
    private Button saveButton;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        dbHelper = new DBHelper(this);
        nameEditText = findViewById(R.id.nameEditText);
        contactEditText = findViewById(R.id.contactEditText);
        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMember();
            }
        });
    }

    private void addMember() {
        String name = nameEditText.getText().toString().trim();
        String contact = contactEditText.getText().toString().trim();

        if (name.isEmpty() || contact.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = dbHelper.addMember(name, contact);
        if (result != -1) {
            Toast.makeText(this, "Member added successfully", Toast.LENGTH_SHORT).show();
            finish(); // Close activity after adding member
        } else {
            Toast.makeText(this, "Failed to add member", Toast.LENGTH_SHORT).show();
        }
    }


}
