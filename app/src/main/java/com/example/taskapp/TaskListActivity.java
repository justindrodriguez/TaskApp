package com.example.taskapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.taskapp.fileio.csvTaskDataAccess;
import com.example.taskapp.models.Task;
import com.example.taskapp.sqlite.SQLTaskDataAccess;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;

public class TaskListActivity extends AppCompatActivity {

    public static final String TAG = "TaskListActivity";
    private ListView lsTasks;
    private Taskable da;
    private ArrayList<Task> allTasks;
    private Button btnAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        //
        // TEST CODE
        //csvTaskDataAccess csvTest = new csvTaskDataAccess(this);
//        SQLTaskDataAccess sda = new SQLTaskDataAccess(this);
//        sda.insertTask(new Task("First Database Check Task", new Date(), false));
//        sda.insertTask(new Task("Second Test Task", new Date(), true));
//        sda.insertTask(new Task("Third task insert test.", new Date(), false));
//
//        ArrayList<Task> testTasks = sda.getAllTasks();
//        for(Task tt : testTasks){
//            Log.d(TAG, tt.toString());
//        }
//
//        Task taskByIdTest = sda.getTaskById(2);
//        Log.d(TAG, taskByIdTest.toString());
//        taskByIdTest.setDescription("This task be been updated in the database.");
//        sda.updateTask(taskByIdTest);
//        Log.d(TAG, taskByIdTest.toString());
//
//        sda.deleteTask(taskByIdTest);
        //
        // End Test Code
        //

        btnAddTask = findViewById(R.id.btnAddTask);
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TaskListActivity.this,TaskDetailsActivity.class);
                startActivity(i);
            }
        });

        lsTasks = findViewById(R.id.lsTasks);
        //da = new TaskDataAccess(this);
        //da = new csvTaskDataAccess(this);
        da = new SQLTaskDataAccess(this);
        allTasks = da.getAllTasks();

        if(allTasks == null || allTasks.size() == 0){
            Intent i = new Intent(this, TaskDetailsActivity.class);
            startActivity(i);
        }

        ArrayAdapter<Task> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, allTasks);
        lsTasks.setAdapter(adapter);

        lsTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "Selected Index" + i);
                Task selectedTask = allTasks.get(i);
                Log.d(TAG, selectedTask.toString());
                Log.d(TAG, "Selected ID: " + selectedTask.getId());

                Intent intent = new Intent(TaskListActivity.this, TaskDetailsActivity.class);
                intent.putExtra(TaskDetailsActivity.EXTRA_TASK_ID, selectedTask.getId());
                startActivity(intent);
            }
        });

    }
}