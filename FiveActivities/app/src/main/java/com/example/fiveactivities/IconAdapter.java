package com.example.fiveactivities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class IconAdapter extends ArrayAdapter<Integer>{
    public IconAdapter(Context context, List<Integer> ids) {
        super(context, 0, ids);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Integer id = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.icon_cell,parent, false);
        }

        ShapeableImageView icon = convertView.findViewById(R.id.cellIcon2);

        if (id != null){
            icon.setImageResource(id);
        } else {
            icon.setImageResource(R.drawable.image);
        }

        return convertView;
    }
}
