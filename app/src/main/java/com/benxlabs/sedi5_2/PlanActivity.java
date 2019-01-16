package com.benxlabs.sedi5_2;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PlanActivity extends AppCompatActivity {

    private ListView mListView;
    DatabaseHelper mDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        mListView = (ListView)findViewById(R.id.listview);
        mListView.setEmptyView((TextView)findViewById(R.id.no_reminder_text));
        mDatabaseHelper = new DatabaseHelper(this);
        populateListView();




    }

    private void populateListView(){
        Cursor data = mDatabaseHelper.getData();

        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(1));
        }

        ListAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listData);
        mListView.setAdapter(adapter);
    }


    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
