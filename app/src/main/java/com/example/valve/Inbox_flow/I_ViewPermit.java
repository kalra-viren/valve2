package com.example.valve.Inbox_flow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.example.valve.R;
import com.example.valve.Util.APIS_URLs;

public class I_ViewPermit extends Fragment {
    private int id;

    public I_ViewPermit() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_i_view_permit, container, false);
        WebView webView = view.findViewById(R.id.view_permit);
        if (getArguments() != null) {
            id = getArguments().getInt("id"); // Use getString() or getDouble() if id is of different type
        }

        // Enable JavaScript and other settings
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);

        // Handle URL loading within the WebView
        webView.setWebViewClient(new WebViewClient());

        // Load the URL
//        String url = "https://mgltest.mahanagargas.com/ApprovalTicketsWeb/CSEPermit.aspx?id="+id;
        String url = APIS_URLs.permit_url+id;
        webView.loadUrl(url);

        return view;
    }
}
