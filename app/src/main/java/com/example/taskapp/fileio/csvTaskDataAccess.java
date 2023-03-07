package com.example.taskapp.fileio;

import static java.lang.Long.parseLong;

import android.content.Context;
import android.util.Log;

import com.example.taskapp.Taskable;
import com.example.taskapp.models.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class csvTaskDataAccess implements Taskable {

    public static final String TAG = "csvTaskDataAccess";
    public static final String DATA_FILE = "tasks.csv";

    private ArrayList<Task> allTasks;
    private Context context;

    public csvTaskDataAccess(Context c){
        this.context = c;
        this.allTasks = new ArrayList();
        loadTasks();
        //
        // TEST CODE
//        this.allTasks.add(new Task(1, "Constructor task test", new Date(), true));
//        this.allTasks.add(new Task(2, "Constructor task test", new Date(), true));
//        saveTasks();
//        loadTasks();
//        Log.d(TAG, allTasks.toString());
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        loadTasks();
        return allTasks;
    }

    @Override
    public Task getTaskById(long id) {
        for(Task t : allTasks){
            if(id == t.getId()){
                return t;
            }
        }
        return null;
    }

    @Override
    public Task insertTask(Task t) throws Exception {
        if(t.isValid()){
            long newId = getMaxId() + 1;
            t.setId(newId);
            allTasks.add(t);
            saveTasks();
        }else{
            throw new Exception("Invalid Task");
        }
        return t;
    }

    @Override
    public Task updateTask(Task t) throws Exception {
        if(t.isValid()){
            Task taskToUpdate = getTaskById(t.getId());
            taskToUpdate.setDescription(t.getDescription());
            taskToUpdate.setDone(t.isDone());
            taskToUpdate.setDue(t.getDue());
            saveTasks();
        }else{
            throw new Exception("Task is invalid.");
        }

        return t;

    }

    @Override
    public int deleteTask(Task t) {
        Task taskToRemove = getTaskById(t.getId());
        if(taskToRemove != null){
            allTasks.remove(allTasks.indexOf(taskToRemove));
            saveTasks();
            return 1;
        }else{
            return 0;
        }
    };

    private String convertTaskToCSV(Task task){
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
        return task.getId() + "," + task.getDescription().toString() + "," + task.getDue().toString() + "," + task.isDone();
    };

    private Task convertCSVToTask(String i){

        String[] sections = i.split(",");
        long id = parseLong(sections[0]);
        String description = sections[1];
        Task task = new Task(id,"temp", new Date(), true);

        return task;
    };

    private void loadTasks(){
        ArrayList<Task> dataList = new ArrayList();
        String dataString = FileHelper.readFromFile(DATA_FILE, context);
        if(dataString == null){
            Log.d(TAG, "No data file/created.");
            return;
        }

        String[] lines = dataString.trim().split("\n");

        for(String line : lines){
            Task t = convertCSVToTask(line);
            if(t != null){
                dataList.add(t);
            }
        }
        this.allTasks = dataList;
    };

    private void saveTasks(){
        String dataString = "";
        StringBuilder sb = new StringBuilder();
        for(Task t : allTasks){
            String csv = convertTaskToCSV(t);
            sb.append(csv);
        }

        dataString = sb.toString();
        if(FileHelper.writetoFile(DATA_FILE, dataString, context)){
            Log.d(TAG, "Successfully saved data.");
        }else{
            Log.d(TAG, "Failed to write data to file.");
        }
    };

    private long getMaxId(){
        long maxId = 0;
        for(Task t : allTasks){
            long tId = t.getId();
            maxId = tId > maxId ? tId : maxId;
        }
        return maxId;
    };
}
