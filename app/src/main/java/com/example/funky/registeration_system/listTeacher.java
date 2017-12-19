package com.example.funky.registeration_system;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class listTeacher extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ListView teachercourses;
    private ArrayList<String> teachersubjects = new ArrayList<>();
    private ArrayList<String> CoursesKeys = new ArrayList<>();
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_teacher);
        mAuth = FirebaseAuth.getInstance();
        this.setTitle("List Teacher's courses");



        teachercourses = (ListView)findViewById(R.id.teachercourses);
         mDatabase = FirebaseDatabase.getInstance().getReference().child("TeacherCourse");


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1,teachersubjects);
        teachercourses.setAdapter(arrayAdapter);
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {

                    String w = child.getValue(String.class);
                    teachersubjects.add("         "+child.getKey()+ "  is " + w );


                    arrayAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                String value = dataSnapshot.getValue(String.class);
                String key = dataSnapshot.getKey();
                int index = teachersubjects.indexOf(key);
                teachersubjects.set(index , value);
                arrayAdapter.notifyDataSetChanged();



                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {

                    String w = child.getValue(String.class);


                    teachersubjects.add("         "+child.getKey()+ "is " + w );
                    arrayAdapter.notifyDataSetChanged();
                }




            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
