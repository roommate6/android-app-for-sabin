package com.example.fiveactivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class SelectIconActivity extends AppCompatActivity {
    private ListView iconsListView;

    private int currentIconId;
    private ArrayList<Integer> selectableIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_icon);

        initializeWidgets();
        setOnClickListener();
        selectableIcons = new ArrayList<>(Arrays.asList(R.drawable.star, R.drawable.caution, R.drawable.warning));
        setIconAdapter();
    }

    public void handleBackEvent(View view) {
        finish();
    }

    private void initializeWidgets(){
        iconsListView = findViewById(R.id.iconsListView);
    }

    private void setOnClickListener() {
        iconsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer selectedIconId = (Integer) iconsListView.getItemAtPosition(position);
                if (selectedIconId != null) {
                    currentIconId = selectedIconId;
                } else {
                    currentIconId = R.drawable.image;
                }
                Intent output = new Intent();
                output.putExtra("selectedIconId", currentIconId);
                setResult(Activity.RESULT_OK, output);
                finish();
            }
        });
    }

    private void setIconAdapter() {
        IconAdapter iconAdapter = new IconAdapter(this, selectableIcons);
        iconsListView.setAdapter(iconAdapter);
    }
}