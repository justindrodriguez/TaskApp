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

import com.example.taskapp.fileio.csvTaskDataAccess;
import com.example.taskapp.models.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskDetailsActivity extends AppCompatActivity {

    public static final String TAG = "TaskDetailsActivity";
    public static final String EXTRA_TASK_ID = "taskId";

    Taskable da;
    Task task;

    EditText txtDescription;
    EditText txtDueDate;
    CheckBox chkDone;
    Button btnSave;


    SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");

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

        //da = new TaskDataAccess(this);
        da = new csvTaskDataAccess(this);
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

        if(txtDueDate.getText().toString().isEmpty()){
            isValid = false;
            txtDueDate.setError(("Date is required."));
        }else{
//            try {
//                dueDate = sdf.parse(txtDueDate.getText().toString());
//            } catch (ParseException e) {
//                e.printStackTrace();
//                isValid = false;
//                txtDueDate.setError("Enter valid date.");
//
//            }
            String regex = "^(1[0-2]|0[1-9])/(3[01]"
                    + "|[12][0-9]|0[1-9])/[0-9]{4}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(txtDueDate.getText().toString());
            if(!matcher.matches()){
                isValid = false;
                txtDueDate.setError("The date entered is not valid");
            }
        }

        return isValid;
    }

    private boolean save(){
        if(validate()){
            getDataFromUI();
            if(task.getId() > 0){
                Log.d(TAG, "UPDATE TASK");
                try {
                    da.updateTask(task);
                } catch (Exception e) {
                    //e.printStackTrace();
                    Log.d(TAG, e.getMessage());
                }
            }else{
                Log.d(TAG, "INSERT TASK");
                try {
                    da.insertTask(task);
                } catch (Exception e) {
                    //e.printStackTrace();
                    Log.d(TAG, e.getMessage());
                }
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