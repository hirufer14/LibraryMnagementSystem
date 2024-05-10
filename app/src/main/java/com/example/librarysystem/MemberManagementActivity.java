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





public class MemberManagementActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private ListView listViewMembers;
    private Button addMemberButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_management);

        dbHelper = new DBHelper(this);
        listViewMembers = findViewById(R.id.listViewMembers);
        addMemberButton = findViewById(R.id.addMemberButton);

        displayAllMembers();

        listViewMembers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected member from the cursor
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                int memberId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));

                // Start UpdateMemberActivity and pass memberId as extra
                Intent intent = new Intent(MemberManagementActivity.this, UpdateMemberActivity.class);
                intent.putExtra("memberId", memberId);
                startActivity(intent);
            }
        });

        addMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemberManagementActivity.this, AddMemberActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        displayAllMembers();
    }
    private void displayAllMembers() {
        Cursor cursor = dbHelper.getAllMembers();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No members available", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] columns = new String[]{DBHelper.KEY_MEMBER_NAME, DBHelper.KEY_MEMBER_CONTACT};
        int[] to = new int[]{R.id.textViewMemberName, R.id.textViewMemberContact};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.member_item, cursor, columns, to, 0);
        listViewMembers.setAdapter(adapter);
    }
}
