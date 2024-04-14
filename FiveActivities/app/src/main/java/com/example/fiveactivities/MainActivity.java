package com.example.fiveactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ListView listView = null;
    private CustomArrayAdapter adapter = null;
    private String[] imageNames = {"Brasov Centrul Vechi", "Brasov Main Street", "Brasov Biserica Newagra"};
    private int [] imageIds = {R.drawable.brasov_1, R.drawable.brasov_2, R.drawable.brasov_3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        adapter = new CustomArrayAdapter(this, R.layout.item_layout, imageNames, imageIds);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,imageNames[i], Toast.LENGTH_SHORT).show();
            }
        });
    }
}