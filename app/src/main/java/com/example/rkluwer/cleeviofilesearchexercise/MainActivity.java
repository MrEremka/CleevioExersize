package com.example.rkluwer.cleeviofilesearchexercise;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (isExternalStorageReadable()){
            Toast.makeText(this, "External storage readable", Toast.LENGTH_SHORT).show();
            requestPermission();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                createLog("Setting menu item clicked");
                return true;
            case R.id.action_refresh:
                loadInternalStorage();
                createLog("Refresh menu item clicked");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadInternalStorage() {
        // TODO load the internal storage
    }

    // This method checks whether the internal storage is readable.
    // Permission for reading storage does not have to be granted yet.
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        } else {
            Toast.makeText(this, "Your storage is not readable at the moment", Toast.LENGTH_SHORT).show();
            finish();
        }
        return false;
    }

    // This method asks the user for the permission to read the external storage if not yet granted.
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                    ConstantValues.PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            createLog("The app has the permission to read external storage");
        }
    }

    private void createLog(String message){
        Log.d("MainActivity", message);
    }
}
