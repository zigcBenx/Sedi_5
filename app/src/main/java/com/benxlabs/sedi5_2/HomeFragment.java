package com.benxlabs.sedi5_2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class HomeFragment extends Fragment {

    private static final String TAG ="HomeFragment";

    private ListView mListView;
    DatabaseHelper mDatabaseHelper;
    SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Adding task
        FloatingActionButton createTaskButton = (FloatingActionButton)view.findViewById(R.id.createTask);
        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Opening new task", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),ActivityEditTask.class);
                startActivity(intent);
            }
        });

        mListView = (ListView)view.findViewById(R.id.listview);
        mListView.setEmptyView((TextView)view.findViewById(R.id.no_reminder_text));
        mDatabaseHelper = new DatabaseHelper(getActivity());
        populateListView();

        refreshLayout = view.findViewById(R.id.refreshTasks);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateListView();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                        Snackbar.make(view,"All tasks loaded...", Snackbar.LENGTH_LONG).show();
                    }
                },2000);
            }
        });


    }

    private void populateListView(){
        Cursor data = mDatabaseHelper.getData();

        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(1));
        }

        ListAdapter adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,listData);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = parent.getItemAtPosition(position).toString();

                Cursor data = mDatabaseHelper.getItemID(title);
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1){
                    Intent editScreentIntent = new Intent(getActivity(),ActivityEditTask.class);
                    editScreentIntent.putExtra("id",itemID);//V edit task pošlje id-> tako preverim a se gre za update al nov insert v bazo
                    startActivity(editScreentIntent);
                }else{
                    toastMessage("There is no id associated with this name...");
                }
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure that you want to delete this item?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String title = parent.getItemAtPosition(position).toString();

                                Cursor data = mDatabaseHelper.getItemID(title);
                                int itemID = -1;
                                while(data.moveToNext()){
                                    itemID = data.getInt(0);
                                }
                                if(itemID > -1){
                                    toastMessage("Deleting item...");
                                    mDatabaseHelper.deleteItem(itemID);
                                    populateListView();
                                }else{
                                    toastMessage("There is no id associated with this name...");
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });
    }


    private void toastMessage(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }



}
