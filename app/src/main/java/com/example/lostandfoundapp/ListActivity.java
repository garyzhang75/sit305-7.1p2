package com.example.lostandfoundapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ListView listView;
    DBHelper db;
    ArrayList<String> descriptions, locations;
    ArrayList<Integer> ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.listViewItems);
        db = new DBHelper(this);
        descriptions = new ArrayList<>();
        locations = new ArrayList<>();
        ids = new ArrayList<>();

        loadData();
    }

    private void loadData() {
        Cursor cursor = db.getAllItems();
        while (cursor.moveToNext()) {
            ids.add(cursor.getInt(0));
            descriptions.add(cursor.getString(1));
            locations.add(cursor.getString(2));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, descriptions);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("id", ids.get(position));
            intent.putExtra("desc", descriptions.get(position));
            intent.putExtra("loc", locations.get(position));
            startActivity(intent);
        });
    }
}