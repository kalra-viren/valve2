package com.example.valve;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentTransaction;

import com.example.valve.Login_AIC.aic;
import com.example.valve.Login_TPE.tpe;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {

    private View highlightView;
    private LinearLayout buttonContainer;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout=findViewById(R.id.tab_layout);
        tabLayout.selectTab(tabLayout.getTabAt(0));
        tpe tp=new tpe();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.fragment_carrier_id_ap,tp);
        transaction.commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Handle tab selection here
                switch (tab.getPosition()) {
                    case 0:
                        // Action for Monday tab
                        tpe tp=new tpe();
                        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        transaction.replace(R.id.fragment_carrier_id_ap,tp);
                        transaction.commit();

                        break;
                    case 1:
                        // Action for Tuesday tab
                        aic a=new aic();
                        FragmentTransaction transaction2=getSupportFragmentManager().beginTransaction();
                        transaction2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        transaction2.replace(R.id.fragment_carrier_id_ap,a);
                        transaction2.commit();
                        break;
                    // Add additional cases for more tabs if needed
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Handle tab unselected event if needed
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Handle tab reselected event if needed
            }
        });

//        Button button = findViewById(R.id.button);
//        Button button2 = findViewById(R.id.button2);
//        highlightView = findViewById(R.id.highlight_view);
//        buttonContainer = findViewById(R.id.buttonContainer);

        // Set initial position of the highlighter
//        highlightView.setTranslationX(button.getX());

        // Set click listeners for buttons
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                moveHighlighter(v);
//                tpe tp=new tpe();
//                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragment_carrier_layout_id,tp);
//                transaction.commit();
//
//
//            }
//        });
//
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                moveHighlighter(v);
//                aic a=new aic();
//                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
//                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                transaction.replace(R.id.fragment_carrier_layout_id,a);
//                transaction.commit();
//            }
//        });
    }

    private void moveHighlighter(View button) {
        // Calculate the position of the clicked button and move the highlighter
        float translationX = button.getX();

        // Animate the movement of the highlighter to create a smooth transition
        highlightView.animate().translationX(translationX).setDuration(200).start();
    }
}