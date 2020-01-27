package com.example.myscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNote extends AppCompatActivity {

    Button backbutton;
    Button clearbutton;
    EditText newnoteEtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        backbutton = findViewById(R.id.back);
        clearbutton = findViewById(R.id.clear);
        newnoteEtext = findViewById(R.id.new_note);

        clear();
        back();
    }

    private void clear() {

        clearbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        newnoteEtext.setText("");
                    }
                }
        );
    }

    private void back() {

        backbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(AddNote.this,MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
