package com.example.funky.registeration_system;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private ListView StudentSubjects; // its listview
    private ArrayList<String> StudentCourses = new ArrayList<>(); // this the data source
    private ArrayList<String> CoursesKeys = new ArrayList<>(); // this the data source keys to can catch row
    private FirebaseAuth mAuth;
    private Button logoutBtn;
    private Button AddSubject;
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        // ehab task
        mText = (TextView) findViewById(R.id.updateText);
        // end ehab task


        //task fawzy
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null && currentUser.getDisplayName() != null) {
            Toast.makeText(this, "Your have been loged in successfully " + currentUser.getDisplayName() + "!", Toast.LENGTH_LONG).show();
        }

        AddSubject = (Button)findViewById(R.id.add_course);
        AddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( StudentCourses.size() == 5){
                    Toast.makeText(StudentActivity.this ,"You can register only 5 subjects " , Toast.LENGTH_LONG).show();
                }
                if(StudentCourses.size() < 5){
                    Toast.makeText(StudentActivity.this ,"You can add more subjects till 5 " , Toast.LENGTH_LONG).show();
                    startActivity(new Intent(StudentActivity.this,RegisterCourse.class));
                    finish();
                }
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference().child("StudentCourse/"+currentUser.getUid());
        final ArrayAdapter<String> arrayAdapter = new CoursesAdapter(this,StudentCourses);
        StudentSubjects = (ListView)findViewById(R.id.StudentSubjects);
        StudentSubjects.setAdapter(arrayAdapter);

        //task ehab
        StudentSubjects.setLongClickable(true);
        StudentSubjects.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String Selected =  CoursesKeys.get(position);
                mDatabase.child(Selected).removeValue();
                Toast.makeText(StudentActivity.this ,"You are updating Yous Subjects..." , Toast.LENGTH_LONG).show();
                startActivity(new Intent(StudentActivity.this,RegisterCourse.class));
                return true;
            }
        });
        //end task ehab


        // create listner on firebase
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String value = dataSnapshot.getValue(String.class); // hna enta bdeef el asma2 f el lis w kda
                String key = dataSnapshot.getKey();
                CoursesKeys.add(key);
                StudentCourses.add(value);
                arrayAdapter.notifyDataSetChanged();

            }

            // in case student update course
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                String value = dataSnapshot.getValue(String.class);
                String key = dataSnapshot.getKey();
                int index = StudentCourses.indexOf(key);
                StudentCourses.set(index , value);
                arrayAdapter.notifyDataSetChanged();
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


        logoutBtn = (Button)findViewById(R.id.logout_ID);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
                startActivity(new Intent(StudentActivity.this, LoginActivity.class));
            }
        });
    }




}
