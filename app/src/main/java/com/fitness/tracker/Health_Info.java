package com.fitness.tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Health_Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.health__info);

        Button Profilenext;
        Profilenext = findViewById(R.id.bProfileNext);
        Profilenext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Health_Info.this, Selected_Field.class);
                startActivity(intent);
            }
        });
    }
}
