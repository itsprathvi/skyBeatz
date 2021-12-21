package com.example.intent2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class password extends AppCompatActivity {
    TextView t1 ;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        b1 = findViewById(R.id.button2);
        t1  = findViewById(R.id.textView2);
        String a = getIntent().getStringExtra("email");
        t1.setText(a);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                intent.putExtra("email", a);
                startActivity(intent);
            }
        });

    }
}