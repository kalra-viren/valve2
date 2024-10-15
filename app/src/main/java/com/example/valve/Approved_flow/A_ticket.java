package com.example.valve.Approved_flow;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.valve.R;

import java.util.ArrayList;
import java.util.List;

public class A_ticket extends Fragment {

    private ListView ticketListView;
    private TicketAdapter ticketAdapter;
    private List<Ticket> ticketList; // List to hold tickets

    public A_ticket() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_a_ticket, container, false);

        // Initialize ListView and Ticket list
        ticketListView = view.findViewById(R.id.ticket_list_view);
        ticketList = new ArrayList<>();

        // Set up the adapter
        ticketAdapter = new TicketAdapter(getActivity(), ticketList);
        ticketListView.setAdapter(ticketAdapter);

        // Fetch tickets (this is where you would call your API)
        fetchTickets();

        return view;
    }

    private void fetchTickets() {
        // Simulate fetching data from an API
        // Replace this with actual API call logic

        // Example tickets
        ticketList.add(new Ticket("Ticket 1", "Description for Ticket 1"));
        ticketList.add(new Ticket("Ticket 2", "Description for Ticket 2"));
        ticketList.add(new Ticket("Ticket 3", "Description for Ticket 3"));
        ticketList.add(new Ticket("Ticket 3", "Description for Ticket 3"));
        ticketList.add(new Ticket("Ticket 3", "Description for Ticket 3"));
        ticketList.add(new Ticket("Ticket 3", "Description for Ticket 3"));
        ticketList.add(new Ticket("Ticket 3", "Description for Ticket 3"));
        ticketList.add(new Ticket("Ticket 3", "Description for Ticket 3"));
        ticketList.add(new Ticket("Ticket 3", "Description for Ticket 3"));
        ticketList.add(new Ticket("Ticket 3", "Description for Ticket 3"));
        ticketList.add(new Ticket("Ticket 3", "Description for Ticket 3"));
        ticketList.add(new Ticket("Ticket 3", "Description for Ticket 3"));


        // Notify adapter of data changes
        ticketAdapter.notifyDataSetChanged();
    }
}