package com.example.taskapp;

import static java.lang.Long.parseLong;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.taskapp.fileio.FileHelper;
import com.example.taskapp.fileio.models.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FileIOActivity extends AppCompatActivity {

    public static final String TAG = "FileIOActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_ioactivity);

        final String TASKS_FILE = "tasks.csv";
        ArrayList<Task> tasks = new ArrayList<Task>(){{
            add(new Task(1, "Short Run", new Date(2023, 6, 14), false));
            add(new Task(2, "Deep Stretching", new Date(2023, 7, 7), true));
            add(new Task(3, "Plank Session", new Date(2023, 4, 12), false));
        }};

        String csvData = "";

        for(Task t : tasks){
            csvData += convertTaskToCSV(t) + "\n";

        }
        Log.d(TAG, csvData);
        FileHelper helper = new FileHelper();
        helper.writetoFile(TASKS_FILE, csvData, this);

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

        ArrayList<Task> anotherTasks = new ArrayList<Task>();
        String incomingCSV = helper.readFromFile(TASKS_FILE, this);
        Log.d(TAG, incomingCSV);
        String[] parts = incomingCSV.split("\n");
        //for(String p : parts)Log.d(TAG, "THIS TASK: " + p);;
        for(String part : parts){
            anotherTasks.add(convertCSVToTask(part));
        }
        Log.d(TAG, "Following should be CSV converted to Task array: \n" + anotherTasks.toString());

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