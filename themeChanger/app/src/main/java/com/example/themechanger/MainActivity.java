package com.example.themechanger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button b1;
    TextView head, body;
    Boolean Theme;
    LinearLayout a1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Theme = Boolean.TRUE;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addCLickHndlr();
    }

    public void addCLickHndlr() {
        b1 = (Button) findViewById(R.id.button);
        head = (TextView) findViewById(R.id.head);
        body = (TextView) findViewById(R.id.body);
        a1 = (LinearLayout) findViewById(R.id.lay);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Theme){
                    a1.setBackgroundColor(Color.parseColor("#333333"));
                    body.setTextColor(Color.parseColor("#aaaaaa"));
                    head.setTextColor(Color.parseColor("#dddddd"));
                    b1.setText("White Theme");
                }else{
                    a1.setBackgroundColor(Color.parseColor("#dddddd"));
                    body.setTextColor(Color.parseColor("#555555"));
                    head.setTextColor(Color.parseColor("#333333"));
                    b1.setText("Dark Theme");
                }
                Theme = !Theme;
            }
        });
    }
}