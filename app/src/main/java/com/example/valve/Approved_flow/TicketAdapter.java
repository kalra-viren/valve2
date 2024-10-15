package com.example.valve.Approved_flow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.valve.R;

import java.util.List;

public class TicketAdapter extends ArrayAdapter<Ticket> {

    public TicketAdapter(Context context, List<Ticket> tickets) {
        super(context, 0, tickets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Ticket ticket = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ticket_item, parent, false);
        }

        // Lookup view for data population
        TextView titleTextView = convertView.findViewById(R.id.ticket_title);
        TextView descriptionTextView = convertView.findViewById(R.id.ticket_description);

        // Populate the data into the template view using the data object
        titleTextView.setText(ticket.getTitle());
        descriptionTextView.setText(ticket.getDescription());

        // Return the completed view to render on screen
        return convertView;
    }
}
