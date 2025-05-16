package com.example.lostandfoundapp;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    TextView tvDetailDescription, tvDetailDate, tvDetailLocation;
    Button btnRemove;
    DBHelper db;
    int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvDetailDescription = findViewById(R.id.tvDetailDescription);
        tvDetailDate = findViewById(R.id.tvDetailDate);
        tvDetailLocation = findViewById(R.id.tvDetailLocation);
        btnRemove = findViewById(R.id.btnRemove);

        db = new DBHelper(this);

        itemId = getIntent().getIntExtra("id", -1);
        tvDetailDescription.setText(getIntent().getStringExtra("desc"));

        String[] parts = getIntent().getStringExtra("loc").split("\\n", 2);
        if (parts.length == 2) {
            tvDetailLocation.setText("At " + parts[0]);
            tvDetailDate.setText(parts[1]);
        } else {
            tvDetailLocation.setText(parts[0]);
            tvDetailDate.setText("");
        }

        btnRemove.setOnClickListener(v -> {
            db.deleteItem(itemId);
            Toast.makeText(this, "Item removed", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
