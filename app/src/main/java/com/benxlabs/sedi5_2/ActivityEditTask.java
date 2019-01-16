package com.benxlabs.sedi5_2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityEditTask extends AppCompatActivity {

    public static final String TAG = "ActivityEditTask";

    DatabaseHelper mDatabaseHelper;

    private FloatingActionButton btnAdd;

    private TextView editDate;
    private TextView editTime;
    private EditText setDate;
    private EditText setTime;

    private EditText titleE;
    private EditText bodyE;
    Calendar c;
    DatePickerDialog dpd;

    TimePickerDialog tpd;

    private boolean state = false; //false == ADDING // true == EDDITING

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        editDate = (TextView)findViewById(R.id.set_date);
        editTime = (TextView)findViewById(R.id.set_time);
        setDate = (EditText)findViewById(R.id.editDate);
        setTime = (EditText)findViewById(R.id.editTime);

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar();
            }
        });
        editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClock();
            }
        });
        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar();
            }
        });
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showClock();
            }
        });

        mDatabaseHelper = new DatabaseHelper(this);

        titleE = (EditText)findViewById(R.id.title);
        bodyE = (EditText)findViewById(R.id.body_text);
        btnAdd = (FloatingActionButton)findViewById(R.id.starred2);


        //IF there is EDITING STATE
        Intent receivedIntent = getIntent();
        final int receivedID = receivedIntent.getIntExtra("id",-1);//If this is ADD STATE default value will be -1

        if(receivedID > -1){
            Toast.makeText(this, "Editing State", Toast.LENGTH_SHORT).show();
            state=true;

            Cursor data = mDatabaseHelper.getItemData(receivedID);
            int itemID = -1;
            String title = "";
            String body = "";
            String date = "";
            String time = "";
            while(data.moveToNext()){
                itemID = data.getInt(0);
                title = data.getString(1);
                body = data.getString(2);
                date = data.getString(3);
                time = data.getString(4);
            }
            //Toast.makeText(this, title+" "+body+" "+date+" "+time, Toast.LENGTH_SHORT).show();
            titleE.setText(title);
            bodyE.setText(body);
            setDate.setText(date);
            setTime.setText(time);
        }else{
            state=false;
            Toast.makeText(this, "Adding State", Toast.LENGTH_SHORT).show();
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleE.getText().toString();
                String body = bodyE.getText().toString();
                String date = setDate.getText().toString();
                String time = setTime.getText().toString();

                if(state){
                    if (title.length() != 0 && body.length() != 0 && date.length() != 0 && time.length() != 0) {
                        showMessage("Updating task...");
                        mDatabaseHelper.updateItem(receivedID,title,body,date,time);
                        finish();
                        Intent intent = new Intent(ActivityEditTask.this, PlanActivity.class);
                        startActivity(intent);
                    }else{
                        showMessage("You must fill all fields...");
                    }

                }else {
                    //showMessage(title+" "+body+" "+date+" "+time);
                    if (title.length() != 0 && body.length() != 0 && date.length() != 0 && time.length() != 0) {
                        showMessage("Adding task...");
                        AddData(title, body, date, time);
                        finish();
                        Intent intent = new Intent(ActivityEditTask.this, PlanActivity.class);
                        startActivity(intent);
                    } else {
                        showMessage("You must fill all fields...");
                    }
                }
            }
        });

    }

    @SuppressLint("NewApi")
    private void showCalendar(){
        closeKeyboard();
        c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        dpd = new DatePickerDialog(ActivityEditTask.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setDate.setText(dayOfMonth+"."+(month+1)+"."+year);
            }
        },day,month,year);
        if(setDate.getText().length() !=0){

            //Toast.makeText(this, setDate.getText().toString().split("\\.")[0], Toast.LENGTH_SHORT).show();
            int y  = Integer.parseInt(setDate.getText().toString().split("\\.")[2]);
            int m = Integer.parseInt(setDate.getText().toString().split("\\.")[1])-1;
            int d = Integer.parseInt(setDate.getText().toString().split("\\.")[0]);
            dpd.updateDate(y,m,d);

        }else {
            dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        }
        dpd.show();
    }

    @SuppressLint("NewApi")
    private void showClock(){
        closeKeyboard();
        c = Calendar.getInstance();
        tpd = new TimePickerDialog(ActivityEditTask.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                setTime.setText(hourOfDay+":"+minute);
            }
        },0,0,true);
        if(setTime.getText().length() != 0){//Če je že bil dolčen čas
            tpd.updateTime(Integer.parseInt(setTime.getText().toString().split(":")[0]),Integer.parseInt(setTime.getText().toString().split(":")[1]));
        }else {
            tpd.updateTime(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
        }
        tpd.show();
    }

    public void AddData(String title, String body, String date, String time){
        boolean insertData = mDatabaseHelper.addData(title,body,date,time);

        if(insertData){
            showMessage("Successfully inserted task!");
        }else{
            showMessage("Failed to insert task...");
        }
    }

    private void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //Confirm dialog while editing


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("All changes will be discard! \nAre your sure you want to exit?")
            .setCancelable(false)
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityEditTask.super.onBackPressed();
                }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void closeKeyboard() {
        View view =getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
