package com.benxlabs.sedi5_2;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AppsFragment extends Fragment {

    private static final String TAG = "Apps fragment";
    public static ArrayList<String> names;
    //public static ArrayList<Drawable> icons;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_apps,container,false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        final ListView listview = (ListView) view.findViewById(R.id.listview);

        final PackageManager packageManager = getActivity().getPackageManager();
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        int count = 0;
        names = new ArrayList<>();
        for (ApplicationInfo packageInfo : packages){
            if((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1){//Brez sistemskih a plikacij
                continue;
            }
            count++;
            //names.add(packageInfo.packageName);
            names.add((String)(packageInfo != null ? packageManager.getApplicationLabel(packageInfo) : "Unknown"));
           /* try {
                icons.add( packageManager.getApplicationIcon(packageInfo.packageName));
            } catch (PackageManager.NameNotFoundException e) {
                //Set default icon
                e.printStackTrace();
            }*/
            //Getting image of applications
            //Drawable icon = getPackageManager().getApplicationIcon(packageInfo.packageName);
            //imageView.setImageDrawable(icon);
        }
        Toast.makeText(getActivity(), String.valueOf(count), Toast.LENGTH_SHORT).show();


        ListViewAdapter adapter = new ListViewAdapter(names,getActivity()/*,icons*/);
        listview.setAdapter(adapter);
    }
}

