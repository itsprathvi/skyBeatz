package com.example.eventlistner1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button b1, b2;
    LinearLayout a1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addCLickHndlr();
    }

    public void addCLickHndlr(){
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        a1 = (LinearLayout) findViewById(R.id.toaster);
        TextView tv = new TextView(getApplicationContext());
        tv.setText("my text");


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Toaster ", Toast.LENGTH_LONG).show();
                a1.addView(tv);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Toaster ", Toast.LENGTH_LONG).show();
            }
        });
    }
}