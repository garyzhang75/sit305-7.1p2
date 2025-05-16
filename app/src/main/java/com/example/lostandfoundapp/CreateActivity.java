package com.example.lostandfoundapp;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class CreateActivity extends AppCompatActivity {

    EditText editName, editPhone, editDescription, editDate, editLocation;
    RadioGroup radioGroup;
    Button btnSave;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        radioGroup = findViewById(R.id.radioGroupPostType);
        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editDescription = findViewById(R.id.editDescription);
        editDate = findViewById(R.id.editDate);
        editLocation = findViewById(R.id.editLocation);
        btnSave = findViewById(R.id.btnSave);
        db = new DBHelper(this);

        btnSave.setOnClickListener(v -> {
            String postType = ((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
            String name = editName.getText().toString();
            String phone = editPhone.getText().toString();
            String description = editDescription.getText().toString();
            String date = editDate.getText().toString();
            String location = editLocation.getText().toString();

            String fullDesc = postType + ": " + name + " (" + phone + ")\n" + description;

            boolean result = db.insertItem(fullDesc, location + "\n" + date);
            if (result) {
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error saving", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

