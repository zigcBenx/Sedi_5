package com.benxlabs.sedi5_2;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.benxlabs.sedi5_2.Adapter.AppAdapter;
import com.benxlabs.sedi5_2.Model.AppInfo;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Applications extends AppCompatActivity {

    ListView listView;
    SwipeRefreshLayout swipeRefreshLayout;
    boolean mInlcudeSystemApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applications);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listview);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        listView.setTextFilterEnabled(true);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshIt();
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadAppInfoTask loadAppInfoTask = new LoadAppInfoTask();
        loadAppInfoTask.execute(PackageManager.GET_META_DATA);
    }

    private void refreshIt(){
        LoadAppInfoTask loadAppInfoTask = new LoadAppInfoTask();
        loadAppInfoTask.execute(PackageManager.GET_META_DATA);
    }


    class LoadAppInfoTask extends AsyncTask<Integer,Integer,List<AppInfo>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipeRefreshLayout.setRefreshing(true);

        }

        @Override
        protected List<AppInfo> doInBackground(Integer... integers) {

            List<AppInfo> apps = new ArrayList<>();
            PackageManager packageManager = getPackageManager();

            List<ApplicationInfo> infos = packageManager.getInstalledApplications(integers[0]);

            for (ApplicationInfo info : infos){
                if(!mInlcudeSystemApps && (info.flags & ApplicationInfo.FLAG_SYSTEM)==1){
                    continue;
                }

                AppInfo app = new AppInfo();
                app.info = info;
                app.label = (String)info.loadLabel(packageManager);
                apps.add(app);
            }
            //Sort data
            Collections.sort(apps,new DNComperator());

            return apps;
        }

        @Override
        protected void onPostExecute(List<AppInfo> appInfos) {
            super.onPostExecute(appInfos);
            listView.setAdapter(new AppAdapter(Applications.this,appInfos));
            swipeRefreshLayout.setRefreshing(false);
            Snackbar.make(listView,appInfos.size() + "applications loaded", Snackbar.LENGTH_LONG).show();
        }
    }


    private class DNComperator implements Comparator<AppInfo> {

        @Override
        public int compare(AppInfo aa, AppInfo ab) {
            CharSequence sa = aa.label;
            CharSequence sb = ab.label;

            if(sa == null){
                sa = aa.info.packageName;
            }

            if(sb == null){
                sb = ab.info.packageName;
            }

            return Collator.getInstance().compare(sa.toString(),sb.toString());

        }
    }
}
