package com.example.valve.Closure_flow;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.valve.R;

import java.util.List;

public class TicketAdapter extends ArrayAdapter<Ticket> {

    private final FragmentManager fragmentManager;
    private View statusIndicator;

    public TicketAdapter(Context context, List<Ticket> tickets, FragmentManager fragmentManager) {
        super(context, 0, tickets);
        this.fragmentManager = fragmentManager;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Ticket ticket = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ticket_item, parent, false);
        }

        TextView name=convertView.findViewById(R.id.ticket_name);
        TextView agency_name= convertView.findViewById(R.id.ticket_agency_name);
        TextView tpe_name=convertView.findViewById(R.id.ticket_tpe);
        TextView dist=convertView.findViewById(R.id.ticket_district);
        TextView loctn=convertView.findViewById(R.id.ticket_location);
        TextView date=convertView.findViewById(R.id.ticket_date);

        TextView statusTextView=convertView.findViewById(R.id.ticket_status);
        TextView ticketid=convertView.findViewById(R.id.ticket_id);
        statusIndicator = convertView.findViewById(R.id.status_indicator);
        statusIndicator.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green_approval));

        // Populate the data into the template view using the data object
//        titleTextView.setText(ticket.getTitle());
//        descriptionTextView.setText(ticket.getDescription());
        name.setText(ticket.getName());
        agency_name.setText(ticket.getAgency_name());
        String tpeName = ticket.getTpE_name();
        if (tpeName == null || tpeName.isEmpty() || tpeName.equals("null")) {
            tpe_name.setText("TPE Not Present");
        } else {
            tpe_name.setText(tpeName);
        }
        dist.setText(ticket.getDistrict());
        loctn.setText(ticket.getValve_name());
        // Concatenate formattedDate and formattedTime with a space in between
        String dateTimeString = ticket.getFormattedDate() + " " + ticket.getFormattedTime();

// Set the concatenated string in the date TextView
        date.setText(dateTimeString);

        statusTextView.setText(ticket.getStatus());
        ticketid.setText(String.valueOf(ticket.getId()));
        if(ticket.getStatus().equals("REQUESTED")){
            statusIndicator.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.blue_requested));
        }
        if(ticket.getStatus().equals("REJECTED")){
            statusIndicator.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red_rejected));
        }
        if(ticket.getStatus().equals("APPROVED")){
            statusIndicator.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green_approval));
        }
        if(ticket.getStatus().equals("CLOSED")){
            statusIndicator.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPressed));
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = statusTextView.getText().toString();
//                u can refer the id of the ticket, (that is the row of permit data this ticket is of), using below statement
                int x=ticket.getId();
                switch (status) {
                    case "CLOSED":
                        // Perform action for "REQUESTED" status
//                        initially every entry in status column is requested.
                        performApprovedAction(x);
//                        performRequestedAction(x);
                        break;
                    case "APPROVED":
                        // Perform action for "APPROVED" status
                        performApprovedAction(x);
                        break;
                    case "REJECTED":
                        // Perform action for "REJECTED" status
//                        performRejectedAction(x);
                        break;
                    default:
                        // Default action or message for unknown statuses
//                        performDefaultAction(x);
                        break;
                }
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

    private void performApprovedAction(int x){
        C_ViewPermit cViewPermit = new C_ViewPermit();
        Bundle bundle = new Bundle();
        bundle.putInt("id", x); // Assuming id is an integer, change type if necessary

        // Set arguments on the fragment
        cViewPermit.setArguments(bundle);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_carrier_id_ap, cViewPermit);
        transaction.addToBackStack(null);

        transaction.commit();

    }
}