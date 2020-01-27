package com.example.myscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Todolist extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    EditText todonew;
    Button todoadd;
    ListView todolist;

    private ArrayList<String> todoitems;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);

        todonew = findViewById(R.id.todo_new);
        todoadd = findViewById(R.id.todo_add);
        todolist = findViewById(R.id.todo_list);

        todoitems = todolistHelper.ReadData(this);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoitems);
        todolist.setAdapter(adapter);

        todoadd.setOnClickListener(this);
        todolist.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.todo_add:

                String newItem = todonew.getText().toString();
                adapter.add(newItem);
                todonew.setHint("Enter new item.");
                todonew.setText("");

                todolistHelper.WriteData(todoitems, this);

                Toast.makeText(Todolist.this, "Added", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        todoitems.remove(i);
        adapter.notifyDataSetChanged();
        todolistHelper.WriteData(todoitems,this);
        Toast.makeText(this, "Removed", Toast.LENGTH_SHORT).show();
    }
}