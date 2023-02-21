package com.example.taskapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import com.example.taskapp.models.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskDetailsActivity extends AppCompatActivity {

    public static final String TAG = "TaskDetailsActivity";
    public static final String EXTRA_TASK_ID = "taskId";

    TaskDataAccess da;
    Task task;

    EditText txtDescription;
    EditText txtDueDate;
    CheckBox chkDone;
    Button btnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        txtDescription = findViewById(R.id.txtDescription);
        txtDueDate = findViewById(R.id.txtDueDate);
        chkDone = findViewById((R.id.chkDone));
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(save()){
                    Intent i = new Intent(TaskDetailsActivity.this, TaskListActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(TaskDetailsActivity.this, "Unable to save task.", Toast.LENGTH_LONG).show();
                }
            }
        });

        da = new TaskDataAccess(this);
        Intent i = getIntent();
        long id = i.getLongExtra(EXTRA_TASK_ID, 0);
        if(id > 0){
            task = da.getTaskById(id);
            Log.d(TAG, task.toString());
            putDataIntoUI();
        }
    }

    private void putDataIntoUI(){
        if(task != null){
            txtDescription.setText(task.getDescription());
            chkDone.setChecked(task.isDone());
            String dateStr = null;
            SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
            dateStr = sdf.format(task.getDue());
            txtDueDate.setText(dateStr);
        }
    }

    private boolean validate(){
        boolean isValid = true;

        if(txtDescription.getText().toString().isEmpty()){
            isValid = false;
            txtDescription.setError("You must enter a description.");
        }

        Date dueDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
        if(txtDueDate.getText().toString().isEmpty()){
            isValid = false;
            txtDueDate.setError(("Date is required."));
        }else{
            try {
                dueDate = sdf.parse(txtDueDate.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
                isValid = false;
                txtDueDate.setError("Enter valid date.");
            }
        }

        return isValid;
    }

    private boolean save(){
        if(validate()){
            getDataFromUI();
            if(task.getId() > 0){
                Log.d(TAG, "UDPDATE TASK");
                da.updateTask(task);
            }else{
                Log.d(TAG, "INSERT TASK");
                da.insertTask(task);
            }
            return true;
        }
        return false;
    }

    private void getDataFromUI(){
        String desc = txtDescription.getText().toString();
        String dueDateStr = txtDueDate.getText().toString();
        boolean done = chkDone.isChecked();

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
        try {
            date = sdf.parse(dueDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d(TAG, "Could not parse date from string");
        }

        if(task != null){
            task.setDescription(desc);
            task.setDue(date);
            task.setDone(done);
        }else{
            task = new Task(desc, date, done);
        }

    }

}