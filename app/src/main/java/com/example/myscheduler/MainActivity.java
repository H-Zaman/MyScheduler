package com.example.myscheduler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements popup.popuplistener{
    
    Database databaseOB;

    FloatingActionButton fab_m, fab_1,fab_2,fab_3,fab_4;
    Button Main_button;
    TextView tv_lastReset;
    Animation fab_open,fab_close,fab_scale,main_scale;
    boolean isOpen = false;

    String mypass = "123";

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM");
    String date_today = sdf.format(new Date());
    
    int currentDay,lastReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseOB = new Database(this);

        Main_button = (Button)findViewById(R.id.main_button);
        fab_m = (FloatingActionButton)findViewById(R.id.hide_button_m);
        fab_1 = (FloatingActionButton)findViewById(R.id.btn_new_note);
        fab_2 = (FloatingActionButton)findViewById(R.id.btw_view_note);
        fab_3 = (FloatingActionButton)findViewById(R.id.btn_todolist);
        fab_4 = (FloatingActionButton)findViewById(R.id.btn_delall);
        tv_lastReset = (TextView)findViewById(R.id.tv_lastreset);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        fab_scale = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_scale);
        main_scale = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_scale_m);

        fab_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(isOpen){

                    fab_1.startAnimation(fab_close);
                    fab_2.startAnimation(fab_close);
                    fab_3.startAnimation(fab_close);
                    fab_4.startAnimation(fab_close);

                    fab_1.setClickable(false);
                    fab_2.setClickable(false);
                    fab_3.setClickable(false);
                    fab_4.setClickable(false);

                    isOpen = false;
                }
                else{

                    view.startAnimation(fab_scale);
                    fab_1.startAnimation(fab_open);
                    fab_2.startAnimation(fab_open);
                    fab_3.startAnimation(fab_open);
                    fab_4.startAnimation(fab_open);

                    fab_1.setClickable(true);
                    fab_2.setClickable(true);
                    fab_3.setClickable(true);
                    fab_4.setClickable(true);

                    isOpen = true;
                }
            }
        });
        
        Main_button();
        SetLocalDataFromDb();
        DeleteData_button();
        delall();
        todolist();
        addnote();


        //viewall();
    }

    private void addnote() {
        fab_1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this,AddNote.class);
                        startActivity(intent);
                    }
                }
        );
    }

    private void todolist() {
        fab_3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this,Todolist.class);
                        startActivity(intent);
                    }
                }
        );
    }

    private void SetLocalDataFromDb() {
        Cursor data = databaseOB.getdata();

        if (data.getCount() == 0){
            Main_button.setText("0");
            tv_lastReset.setText(lastReset+" days");

        }
        else{
            while (data.moveToNext()){

                currentDay = Integer.parseInt(data.getString(0));
                lastReset = Integer.parseInt(data.getString(1));

                Main_button.setText(data.getString(0));
                tv_lastReset.setText(data.getString(1)+" days");

            }
        }

    }

    private void Main_button() {

        Main_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        boolean gg = databaseOB.InsertMainData(currentDay+1,lastReset,date_today);

                        if(gg){
                            //animation
                            view.startAnimation(main_scale);
                            //Toast.makeText(MainActivity.this,"Done",Toast.LENGTH_LONG).show();
                            SetLocalDataFromDb();
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    private void DeleteData_button() {
        fab_4.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openDialouge();
                    }
                }
        );
    }

    private void openDialouge() {
        popup pop = new popup();
        pop.show(getSupportFragmentManager(),"hahaha");
    }

    @Override
    public void sendpassword(String password) {
        if(password.equals(mypass)){
            databaseOB.resetData (currentDay);
            SetLocalDataFromDb();
            Toast.makeText(MainActivity.this,"Done",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(MainActivity.this,"NOPE Wrong Password bruh!",Toast.LENGTH_LONG).show();
        }
    }

//extra
    public void viewall() {

        fab_3.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Cursor res = databaseOB.getalldata();
                        if (res.getCount() == 0){
                            //Toast.makeText(MainActivity.this,"NO data to show",Toast.LENGTH_LONG).show();
                            showmessage("Error","No data");
                        }
                        else{
                            //Toast.makeText(MainActivity.this,"data here",Toast.LENGTH_LONG).show();

                            StringBuffer buffer = new StringBuffer();
                            while (res.moveToNext()){
                                buffer.append("Current Day : "+res.getString(0 )+"\n");
                                buffer.append("Last reset : "+res.getString(1 )+"\n");
                                buffer.append("Date : "+res.getString(2)+"\n\n");
                            }

                            //show all data
                            showmessage("All Data",buffer.toString());
                        }
                    }
                }
        );
    }
    public void showmessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private void delall() {
        fab_2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        databaseOB.deltable();
                        Toast.makeText(MainActivity.this,"Done",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}
