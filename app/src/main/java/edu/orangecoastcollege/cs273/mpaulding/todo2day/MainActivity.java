package edu.orangecoastcollege.cs273.mpaulding.todo2day;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DBHelper database;
    private List<Task> taskList;
    private TaskListAdapter taskListAdapter;

    private EditText taskEditTask;
    private ListView taskListView;

    public void addTask(View view){
        String description = taskEditTask.getText().toString();
        if(description.isEmpty())
        {
            Toast.makeText(this, "Desciption invalid! Does not have text", Toast.LENGTH_SHORT).show();
        }else
        {
            Task newTask = new Task(description, 0);

           //add task to ListAdapter
           taskListAdapter.add(newTask);

            //add to DataBase
            database.addTask(newTask);

            taskEditTask.setText("");
        }
    }
    public void changeTaskStatus(View view)
    {
        CheckBox selectedCheck = (CheckBox) view;
        Task selectedTask =(Task) selectedCheck.getTag();

        selectedTask.setIsDone(selectedCheck.isChecked()?1:0);


        database.upDateTask(selectedTask);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FOR NOW (TEMPORARY), delete the old database, then create a new one
        // this.deleteDatabase("Tasks");

        // Let's make a DBHelper reference:
        database = new DBHelper(this);

        database.addTask(new Task("Dummy",1));
        //fillList with task from database
        taskList = database.getAllTasks();
    //create taskListAdapter
        taskListAdapter = new TaskListAdapter(this, R.layout.task_item,taskList);

        //reference for taskListView
        taskListView = (ListView) findViewById(R.id.taskListView);

        taskListView.setAdapter(taskListAdapter);

        //connect editText with layout
        taskEditTask = (EditText) findViewById(R.id.taskEditText);
    }

    /**
     *
     * @param view
     */
    public void clearAllTasks(View view)
    {
        taskList.clear();
        //clear all records n Data Table
        database.deleteAllTask();
        //database records are deleted but will now need to tell adapter
        taskListAdapter.notifyDataSetChanged();
    }
}