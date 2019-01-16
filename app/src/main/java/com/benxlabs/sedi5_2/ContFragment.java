package com.benxlabs.sedi5_2;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.benxlabs.sedi5_2.Adapter.ContactAdapter;
import com.benxlabs.sedi5_2.contacts.MyContacts;

import java.util.ArrayList;

public class ContFragment extends Fragment {

    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*recyclerView = (RecyclerView)view.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
*/
        loadContacts();
    }

    private void loadContacts() {
        //Cursor cursor = getActivity().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null,null,null,ContactsContract
        //    .CommonDataKinds.Phone.NUMBER);

        //ArrayList<MyContacts> arrayList = new ArrayList<>();



        /*if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                if(number.length()>0){
                    Cursor phoneCursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",new String[]{id},null);

                    if(phoneCursor.getCount()>0){
                        while(phoneCursor.moveToNext()){
                            String phoneNumberValue = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                            MyContacts myContacts = new MyContacts(name,phoneNumberValue);

                            arrayList.add(myContacts);
                        }
                    }
                    phoneCursor.close();
                }
            }

            //use the adapter and set recycle
            ContactAdapter contactAdapter = new ContactAdapter(getActivity(),arrayList);
            recyclerView.setAdapter(contactAdapter);
            contactAdapter.notifyDataSetChanged();

        }else{
            Toast.makeText(getActivity(), "No contacts found", Toast.LENGTH_SHORT).show();
        }*/
    }
}
