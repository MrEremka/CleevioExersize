package com.example.rkluwer.cleeviofilesearchexercise;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class DirectoryFragment extends Fragment {

    private String[] filePathStrings;

    // Empty necessary constructor.
    public DirectoryFragment(){
    }

    /*
    private ArrayList<String> pathHistory;
    private int count;
    */

    private String currentPath;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null){
            //TODO load savedInstanceState
        }
        View rootView = inflater.inflate(R.layout.activity_main_grid_layout, container);

        GridView gridView = rootView.findViewById(R.id.main_activity_grid_view);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, filePathStrings);
        gridView.setAdapter(arrayAdapter);

        return rootView;
    }

    public void setCurrentPath (String currentPath){
        this.currentPath = currentPath;
    }


    public void setFilePathStrings (String[] filePathStrings){
        this.filePathStrings = filePathStrings;
    }

    /*
    public void setPathHistory (ArrayList<String> pathHistory){
        this.pathHistory = pathHistory;
    }

    public void setCount (int count){
        this.count = count;
    }
    */

    private void createLog(String message){
        Log.d("DirectoryFragment", message);
    }
}
