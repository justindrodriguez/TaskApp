package com.example.taskapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.taskapp.models.Task;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TaskDataAccess tda = new TaskDataAccess(this);
        ArrayList<Task> tasks = tda.getAllTasks();
        for(Task t : tasks){
            Log.d(TAG, t.toString());
        }

        Task newTask = new Task("Haircut", new Date(), false);
        try {
            newTask = tda.insertTask(newTask);
        } catch (Exception e) {
            //e.printStackTrace();
            Log.d(TAG, e.getMessage());
        }
        Log.d(TAG, "NEW TASK ID: " + newTask.getId());

        Task someTask = tda.getTaskById(1);
        Log.d(TAG, someTask.toString());

        someTask.setDescription("Exercise");
        try {
            tda.updateTask(someTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, someTask.toString());

       int numRows = tda.deleteTask(newTask);
        Log.d(TAG, "DELETED ROWS: " + numRows);
        for(Task t : tasks){
            Log.d(TAG, t.toString());
        }
    }

    public void btnPressed(View v){
        //Toast.makeText(this, R.string.btn_cheers, Toast.LENGTH_LONG).show();
        int idNum = v.getId();
        String idName = getResources().getResourceEntryName(idNum);
        //Toast.makeText(this, idName, Toast.LENGTH_LONG).show();

        Intent i;

        switch(idName){
            case "btnIoActivity":
                i = new Intent(this, FileIOActivity.class);
                startActivity(i);
                break;
            case "btnTaskActivity":
                i = new Intent(this, TaskListActivity.class);
                startActivity(i);
                break;
        }

    }
}