package com.benxlabs.sedi5_2;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter<String> {
    private List<String> Apps = new ArrayList<>();
    private Context context;
    //private ArrayList<Drawable> icons;

    public ListViewAdapter(List<String> Apps, Context context /*,ArrayList<Drawable> icons*/){
        super(context,R.layout.item_layout,Apps);
        this.context = context;
        this.Apps = Apps;
        //this.icons = icons;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View row = inflater.inflate(R.layout.item_layout,parent,false);

        //Ne dela ker nevem kateri Image view spreminjam
        //ImageView imageView = row.findViewById(R.id.appIcon);
        //imageView.setImageDrawable(icons.get(position));

        TextView AppName = row.findViewById(R.id.app_name);
        AppName.setText(Apps.get(position));


        CheckBox checkBox = row.findViewById(R.id.checkbox);
        checkBox.setTag(position);//Which check box is selected

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Izpis imena označene/odznačene aplikacije---> uporabi parameter isChecked
                Toast.makeText(context, AppsFragment.names.get(position), Toast.LENGTH_SHORT).show();

            }
        });

        return row;
    }



}
