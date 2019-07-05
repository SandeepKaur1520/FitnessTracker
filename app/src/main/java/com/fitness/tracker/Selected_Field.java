package com.fitness.tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Selected_Field extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected__field);


        Button SelectedFieldNext;
        SelectedFieldNext = findViewById(R.id.bselectedNext);
        SelectedFieldNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Selected_Field.this,Period_info.class);
                startActivity(intent);
            }
        });
        Button SelectedFieldNext1;
        SelectedFieldNext1 = findViewById(R.id.bselectedNext1);
        SelectedFieldNext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Selected_Field.this,Body_Info.class);
                startActivity(intent);
            }
        });
    }
}
