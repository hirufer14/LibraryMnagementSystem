package com.example.librarysystem;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;



public class UpdateMemberActivity extends AppCompatActivity {

    private EditText nameEditText, contactEditText;
    private Button updateButton;
    private DBHelper dbHelper;
    private int memberId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_member);

        dbHelper = new DBHelper(this);
        nameEditText = findViewById(R.id.nameEditText);
        contactEditText = findViewById(R.id.contactEditText);
        updateButton = findViewById(R.id.updateButton);

        // Get the memberId from the intent extras
        Intent intent = getIntent();
        memberId = intent.getIntExtra("memberId", -1);

        if (memberId == -1) {
            Toast.makeText(this, "Invalid member", Toast.LENGTH_SHORT).show();
            finish(); // Close activity if memberId is not provided
        }

        // Fetch member details from database and populate EditText fields
        populateMemberDetails();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMember();
            }
        });
    }

    private void populateMemberDetails() {
        // Fetch member details from database using memberId
        Cursor cursor = dbHelper.getMemberById(memberId);
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KEY_MEMBER_NAME));
            String contact = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.KEY_MEMBER_CONTACT));

            nameEditText.setText(name);
            contactEditText.setText(contact);
        }
    }

    private void updateMember() {
        String name = nameEditText.getText().toString().trim();
        String contact = contactEditText.getText().toString().trim();

        if (name.isEmpty() || contact.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int rowsAffected = dbHelper.updateMember(memberId, name, contact);
        if (rowsAffected > 0) {
            Toast.makeText(this, "Member updated successfully", Toast.LENGTH_SHORT).show();
            finish(); // Close activity after updating member
        } else {
            Toast.makeText(this, "Failed to update member", Toast.LENGTH_SHORT).show();
        }
    }
}

