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

public class CustomArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final int layout;
    private final String [] imageNames;
    private final int [] imageIds;

    public CustomArrayAdapter(Context context, int layout, String[] imageNames, int[] imageIds) {
        super(context, layout,imageNames);
        this.context    = context;
        this.layout     = layout;
        this.imageNames = imageNames;
        this.imageIds   = imageIds;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,@NonNull ViewGroup parent) {

        // inflate the layout to a java object
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View layoutView = inflater.inflate(this.layout,null);

        // connect the layout object with components
        ImageView imageView = layoutView.findViewById(R.id.imageView);
        TextView mainTextView = layoutView.findViewById(R.id.textView);
        TextView  subtitleTextView = layoutView.findViewById(R.id.textView2);

        // populate components with content
        imageView.setImageResource(imageIds[position]);
        mainTextView.setText(imageNames[position]);
        subtitleTextView.setText("Brasov");

        return layoutView;
    }
}
