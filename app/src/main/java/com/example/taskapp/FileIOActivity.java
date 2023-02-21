package com.example.taskapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.taskapp.fileio.FileHelper;

public class FileIOActivity extends AppCompatActivity {

    public static final String TAG = "FileIOActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_ioactivity);

        boolean result = FileHelper.writetoFile("text.txt", "Hello World!!!", this);
        if (result) {
            Log.d(TAG, "PASSED FIRST TEST!");
        }
        else{
            Log.d(TAG, "FAILED to writeToFile() did not succeed");
        }

        String data = FileHelper.readFromFile("test.txt", this);
        if (data != null){
            Log.d(TAG, "PASSED SECOND TEST");
        }else{
            Log.d(TAG, "FAILED to readFromFile()");
        }
    }
}