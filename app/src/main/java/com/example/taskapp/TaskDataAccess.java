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

    public Task insertTask(Task t) throws Exception {
        if(t.isValid()){
            allTasks.add(t);
            t.setId(getMaxId() + 1);
            return t;
        }else{
            throw new Exception("INSERT FAILED: INVALID TASK");
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

    public ArrayList<Task> getAllTasks(){
        ArrayList<Task> copyList = new ArrayList();
        for(Task t : allTasks){
            Task copy = new Task(t.getId(), t.getDescription(), t.getDue(), t.isDone());
            copyList.add(copy);
        }
        return copyList;
    }

    public Task getTaskById(long id){
      for(Task t : allTasks){
          if(t.getId() == id){
              return new Task(t.getId(), t.getDescription(), t.getDue(), t.isDone());
          }
      }
      return null;
    };

    public Task updateTask(Task t) throws Exception {
        // TODO: Validate t
        if(t.isValid()){
            for(Task currentTask : allTasks){
                if(currentTask.getId() == t.getId()){
                    currentTask.setDescription(t.getDescription());
                    currentTask.setDue(t.getDue());
                    currentTask.setDone(t.isDone());
                    break;
                }
            }
            return t;
        }else{
            throw new Exception("UPDATE FAILED: INVALID TASK");
        }

    };

    public int deleteTask(Task t){
        for(Task currentTask : allTasks){
            if(currentTask.getId() == t.getId()){
                allTasks.remove(currentTask);
                return 1;
            }
        }
        return 0;
    };
}
