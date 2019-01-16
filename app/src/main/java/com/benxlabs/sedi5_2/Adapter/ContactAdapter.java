package com.benxlabs.sedi5_2.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.benxlabs.sedi5_2.R;
import com.benxlabs.sedi5_2.contacts.MyContacts;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyAdapterViewHolder> {

    Context context;
    ArrayList<MyContacts> myContactsArrayList;
    public ContactAdapter(Context context, ArrayList<MyContacts> myContactsArrayList) {
        this.context = context;
        this.myContactsArrayList = myContactsArrayList;
    }

    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Tuki item_Layout sploh nevem ka je oz. kaj mora bit vrjetn je narobe
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout,viewGroup,false);
        return new MyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder myAdapterViewHolder, int i) {
        MyContacts myContacts = myContactsArrayList.get(i);
        myAdapterViewHolder.tvName.setText(myContacts.getName());
        myAdapterViewHolder.tvNumber.setText(myContacts.getNumber());
    }

    @Override
    public int getItemCount() {
        return myContactsArrayList.size();
    }

    public class MyAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvNumber;
        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.txtName);
            tvNumber = (TextView) itemView.findViewById(R.id.txtNumber);
        }
    }

}
