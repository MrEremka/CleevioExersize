package com.example.rkluwer.cleeviofilesearchexercise;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class DirecotryFragment extends Fragment {

    // Empty necessary constructor.
    public DirecotryFragment(){}

    /*
    private String[] filePathStrings;
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

        View rootView = inflater.inflate(R.layout.directory_fragment, container, false);

        if (currentPath != null){
            TextView currentTv = rootView.findViewById(R.id.fragment_text_view);
            String[] splitPath = currentPath.split("/");
            currentTv.setText(splitPath[splitPath.length -1]);
        }

        return rootView;
    }

    public void setCurrentPath (String currentPath){
        this.currentPath = currentPath;
    }

    /*
    public void setFilePathStrings (String[] filePathStrings){
        this.filePathStrings = filePathStrings;
    }

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
