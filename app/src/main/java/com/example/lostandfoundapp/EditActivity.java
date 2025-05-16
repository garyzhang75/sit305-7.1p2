package com.example.lostandfoundapp;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    EditText editName, editPhone, editDescription, editDate, editLocation;
    RadioGroup radioGroup;
    Button btnUpdate;
    DBHelper db;
    int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        radioGroup = findViewById(R.id.radioGroupPostType);
        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editDescription = findViewById(R.id.editDescription);
        editDate = findViewById(R.id.editDate);
        editLocation = findViewById(R.id.editLocation);
        btnUpdate = findViewById(R.id.btnUpdate);
        db = new DBHelper(this);

        itemId = getIntent().getIntExtra("id", -1);
        String[] parts = getIntent().getStringExtra("desc").split(": ", 2);
        String[] contactParts = parts[1].split("\\n", 2);
        String[] locationParts = getIntent().getStringExtra("loc").split("\\n", 2);

        // Populate form
        if (parts[0].equals("Lost")) {
            radioGroup.check(R.id.radioLost);
        } else {
            radioGroup.check(R.id.radioFound);
        }
        editName.setText(contactParts[0].split(" ", 2)[0]);
        editPhone.setText(contactParts[0].split("[()]", 2)[1]);
        editDescription.setText(contactParts[1]);
        editDate.setText(locationParts.length > 1 ? locationParts[1] : "");
        editLocation.setText(locationParts[0]);

        btnUpdate.setOnClickListener(v -> {
            String postType = ((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
            String name = editName.getText().toString();
            String phone = editPhone.getText().toString();
            String description = editDescription.getText().toString();
            String date = editDate.getText().toString();
            String location = editLocation.getText().toString();

            String fullDesc = postType + ": " + name + " (" + phone + ")\n" + description;
            String fullLoc = location + "\n" + date;

            db.updateItemById(itemId, fullDesc, fullLoc);
            Toast.makeText(this, "Item updated", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
