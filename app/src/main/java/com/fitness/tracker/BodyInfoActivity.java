package com.fitness.tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BodyInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.body_info);

        Button nextBody;
       // nextBody = findViewById(R.id.bNextBody);
       /* nextBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BodyInfoActivity.this, Activeness.class);
                startActivity(intent);
            }
        });*/
    }
}

