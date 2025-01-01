package com.example.valve.Approved_flow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.valve.R;

import java.util.List;

public class ReadingAdapter extends ArrayAdapter<Reading> {
    private View statusIndicator;


    public ReadingAdapter(@NonNull Context context, List<Reading> readings) {
        super(context, 0, readings);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Reading reading = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.reading_item, parent, false);
        }
        statusIndicator = convertView.findViewById(R.id.status_indicator);
        statusIndicator.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.pale_blue));

        TextView textView6 = convertView.findViewById(R.id.textView6);
        TextView textView7 = convertView.findViewById(R.id.textView7);
        TextView textView8 = convertView.findViewById(R.id.textView8);
        TextView textView9 = convertView.findViewById(R.id.textView9);

        textView6.setText("Time: ");
        textView7.setText(reading.getTime()); // You can set actual time info if needed
        textView8.setText("Reading: ");
        textView9.setText(reading.getReading());
        if(reading.getType().equals("O2")){
            statusIndicator.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.pale_blue));
        }
        if(reading.getType().equals("NG")){
            statusIndicator.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green_approval));
        }

        return convertView;
    }
}
