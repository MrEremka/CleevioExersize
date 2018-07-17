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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> arrayAdapter;

    private String[] filePathStrings;
    private File[] fileArray;
    private File file;

    private int count = 0;
    private ArrayList<String> pathHistory;
    private String lastDirectory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setting the "return" icon in the toolbar
        // The return icon makes the user go back one step in the file menu.
        toolbar.setNavigationIcon(getDrawable(R.mipmap.icon_left48));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count != 0){
                    pathHistory.remove(count);
                    count--;
                    loadInternalStorage();
                }
            }
        });

        listView = findViewById(R.id.main_layout_list_view);

        // Initial check on the permissions and clearing the path history.
        if (isExternalStorageReadable()){
            Toast.makeText(this, "External storage readable", Toast.LENGTH_SHORT).show();
            requestPermission();

            clearPathHistory();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String currentDirectory = pathHistory.get(count) + "/" + (String) parent.getItemAtPosition(position);
                createLog(currentDirectory);
                File file = new File(currentDirectory);

                if (file.isDirectory()) {
                    count++;
                    pathHistory.add(count, currentDirectory);
                    loadInternalStorage();
                } else if (file.isAbsolute()){
                    createLog("This is a file that needs to be opened with an intent");
                    // TODO open the file with an intent
                } else {
                    createLog("Something has gone wrong");
                }
            }
        });
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
                clearPathHistory();
                loadInternalStorage();
                createLog("Refresh menu item clicked");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadInternalStorage() {
        try {
            file = new File(pathHistory.get(count));
            createLog("FilePathHistory: " + pathHistory.get(count));
            fileArray = file.listFiles();

            filePathStrings = new String[fileArray.length];
            for (int i = 0; i < fileArray.length; i++) {
                StringBuilder builder = new StringBuilder();
                builder.append(fileArray[i].toString());
                builder.delete(0, pathHistory.get(count).length() + 1);
                filePathStrings[i] = builder.toString();
                createLog("File directory is: " + filePathStrings[i]);
            }

            arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, filePathStrings);
            listView.setAdapter(arrayAdapter);
        } catch (NullPointerException e){
            createLog(e.getMessage());
        }
    }

    // This method clears the path history and starts the list of files again from the beginning.
    private void clearPathHistory() {
        count = 0;
        pathHistory = new ArrayList<>();
        pathHistory.add(System.getenv("EXTERNAL_STORAGE"));
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
