package com.example.taskapp;

import static java.lang.Long.parseLong;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.taskapp.fileio.FileHelper;
import com.example.taskapp.fileio.models.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

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

        Task t = new Task(1, "Laundry", new Date(), false);
        String csv = convertTaskToCSV(t);
        Log.d(TAG, csv);
    }

    private String convertTaskToCSV(Task task){
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
        return task.getId() + "," + task.getDescription().toString() + "," + task.getDue().toString() + "," + task.isDone();
    }

    private Task convertCSVToTask(String i){

        String[] sections = i.split(",");
        long id = parseLong(sections[0]);
        String description = sections[1];
       // Date due =
       Task task = new Task(id,"temp", new Date(), true);

        return task;
    };
}