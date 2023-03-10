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

import java.sql.Array;
import java.util.ArrayList;

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
        da = new csvTaskDataAccess(this);
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