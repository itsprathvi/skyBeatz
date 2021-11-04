package com.example.createelementonclick;

import androidx.appcompat.app.AppCompatActivity;

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

    public void addCLickHndlr() {
        b1 = (Button) findViewById(R.id.add);
        b2 = (Button) findViewById(R.id.remove);
        a1 = (LinearLayout) findViewById(R.id.playground);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "add", Toast.LENGTH_LONG).show();
                LinearLayout ll = new LinearLayout(getApplicationContext());
                ll.setOrientation(LinearLayout.HORIZONTAL);
                // Create TextView
                TextView product = new TextView(getApplicationContext());
                product.setText("  Called You!!  ");
                ll.addView(product);

                a1.addView(ll);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "sub", Toast.LENGTH_LONG).show();
                if(a1.getChildCount()>0) {
                    a1.removeViewAt(a1.getChildCount() - 1);
                }else{
                Toast.makeText(getApplicationContext(), "Nothing to remove 😅", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}