package com.benxlabs.sedi5_2;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ActivitySettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



       /* try {//Tryin to get settings
            int temp  = Settings.System.getInt(this.getContentResolver(),Settings.ACTION_USAGE_ACCESS_SETTINGS);
            Toast.makeText(this, temp, Toast.LENGTH_SHORT).show();
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    public void setPermission(View v){
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(intent);
    }
}
