package com.example.taskapp;

import android.content.Context;

import com.example.taskapp.models.Task;

import java.util.ArrayList;
import java.util.Date;

public class TaskDataAccess {

    private Context context;

    public TaskDataAccess(Context c){
        this.context = c;
    }

    private static ArrayList<Task> allTasks = new ArrayList<Task>(){{
       add(new Task(1, "Shovel Snow", new Date(2000, 8, 22), false));
       add(new Task(2, "Dishes", new Date(2000, 8, 22), true));
       add(new Task(3, "Grocery Shopping", new Date(2000, 8, 22), false));
    }};

    public Task insertTask(Task t){
        allTasks.add(t);
        t.setId(allTasks.size());
        return t;
    };

    public ArrayList<Task> getAllTasks(){
        return allTasks;
    }

    public Task getTaskById(long id){
      for(Task t : allTasks){
          if(t.getId() == id){
              return t;
          }
      }
      return null;
    };

    public Task updateTask(Task t){
      return t;
    };

    public int deleteTask(Task t){
        if(allTasks.contains(t)){
            allTasks.remove(t);
            return 1;
        }
        return 0;
    };
}
