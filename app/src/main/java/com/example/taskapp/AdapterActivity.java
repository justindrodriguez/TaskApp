package com.example.taskapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.taskapp.models.User;

import java.util.ArrayList;

public class AdapterActivity extends AppCompatActivity {

    public static final String TAG = "AdapterActivity";

    ListView listView;
    String[] names = {"Bob", "Dick", "Caitlin"};
    ArrayList<User> users = User.allUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter);

        listView = findViewById(R.id.listView);
        //example1();
        //example2();
        example3();
    } // End of onCreate()

    public void example1(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        // ArrayAdapter adapter has been primed and is ready for appending.
        listView.setAdapter(adapter); // Adapter injected the names list.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedName = names[i];
                Log.d(TAG, "You selected: " + selectedName);
            }
        });
    } //

    public void example2(){
        ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User selectedUser = users.get(i);
                Log.d(TAG, selectedUser.getFirstName());
            }
        });
    }

    public void example3(){
        ArrayAdapter<User> adapter = new ArrayAdapter(this, R.layout.custom_user_list_item, R.id.lblFirstName, users){
            @Override
            public View getView(int position, View convertView, ViewGroup parentListView){
                View listItemView = super.getView(position, convertView, parentListView);
                TextView lblFirstName = listItemView.findViewById(R.id.lblFirstName);
                CheckBox chkActive = listItemView.findViewById((R.id.chkActive));

                User currentUser = users.get(position);
                lblFirstName.setText(currentUser.getFirstName());
                chkActive.setChecked(currentUser.isActive());


                chkActive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        currentUser.setActive(chkActive.isChecked());
                        Log.d(TAG, currentUser.toString());
                    }
                });


                listItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "Display details for: " + currentUser.getFirstName());
                    }
                });

                return listItemView;
            } // End of @Override
        };
        listView.setAdapter(adapter);
    }
}