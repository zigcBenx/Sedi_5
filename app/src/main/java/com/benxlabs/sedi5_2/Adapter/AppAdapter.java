package com.benxlabs.sedi5_2.Adapter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.benxlabs.sedi5_2.Applications;
import com.benxlabs.sedi5_2.Model.AppInfo;
import com.benxlabs.sedi5_2.R;

import java.util.List;

public class AppAdapter extends ArrayAdapter<AppInfo> {

    LayoutInflater layoutInflater;
    PackageManager packageManager;
    List<AppInfo> apps;

    Context context;

    public AppAdapter(Context context, List<AppInfo> apps) {
        super(context, R.layout.app_item_layout,apps);

        layoutInflater = LayoutInflater.from(context);
        packageManager = context.getPackageManager();
        this.apps = apps;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        AppInfo current = apps.get(position);
        View view = convertView;

        if(view == null){
            view = layoutInflater.inflate(R.layout.app_item_layout,parent,false);
        }

        TextView textViewTitle = (TextView)view.findViewById(R.id.titleTextView);
        textViewTitle.setText(current.label);


        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(current.info.packageName,0);

            if(!TextUtils.isEmpty(packageInfo.versionName)){
                String versionInfo = String.format("%s",packageInfo.versionName);
                TextView textViewVersion= (TextView)view.findViewById(R.id.versionId);
                textViewVersion.setText(versionInfo);
            }

            if(!TextUtils.isEmpty(current.info.packageName)){
                TextView textSubTitle= (TextView)view.findViewById(R.id.subTitle);
                textSubTitle.setText(current.info.packageName);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.iconImage);
        Drawable background = current.info.loadIcon(packageManager);
        imageView.setBackgroundDrawable(background);

        CheckBox checkBox = view.findViewById(R.id.checkbox);
        checkBox.setTag(position);//Which check box is selected

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Izpis imena označene/odznačene aplikacije---> uporabi parameter isChecked
                Toast.makeText(context, apps.get(position).label, Toast.LENGTH_SHORT).show();

            }
        });


        return view;
    }
}
