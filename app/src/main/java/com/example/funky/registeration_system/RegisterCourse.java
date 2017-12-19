package com.example.funky.registeration_system;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RegisterCourse extends AppCompatActivity {

    private Button RegisterCourseButton;
    Spinner registercourse_spinner;
    ArrayAdapter<String> RegisterCourseAdapter;
    private FirebaseDatabase Database = FirebaseDatabase.getInstance();
    private DatabaseReference mRef = Database.getReference("Courses");

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mDatabase;
    private Button LogoutButton;
    private ImageButton GoToProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_course);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        LogoutButton=(Button) findViewById(R.id.logout);
        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(RegisterCourse.this,LoginActivity.class));
                finish();
            }
        });

        RegisterCourseButton = (Button) findViewById(R.id.registercourse);
        RegisterCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String Course = registercourse_spinner.getSelectedItem().toString();
                final DatabaseReference sc = Database.getReference("StudentCourse");
                DatabaseReference u = Database.getReference("User");
                final String User_id = mAuth.getCurrentUser().getUid();

                sc.child(User_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getChildrenCount() < 5) {
                            DatabaseReference newsc = sc.child(User_id);
                            newsc.child(Course).setValue(Course);
                            Toast.makeText(getApplicationContext(), "A New Course Called :" + Course + " Registered Successfully", Toast.LENGTH_LONG).show();
                        } else  {
                            Toast.makeText(getApplicationContext(), "You Can Register Only 5 Subjects ", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
        GoToProfile=(ImageButton) findViewById(R.id.gotoprofile);
        GoToProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterCourse.this,StudentActivity.class));
            }
        });
        registercourse_spinner = (Spinner) findViewById(R.id.registercourse_spinner);
        final List<String> Courses = new ArrayList<String>();
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot Course : dataSnapshot.getChildren()) {
                    Courses.add(Course.getValue().toString());
                }
                RegisterCourseAdapter = new ArrayAdapter<>(RegisterCourse.this, android.R.layout.simple_spinner_item, Courses);
                RegisterCourseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                registercourse_spinner.setAdapter(RegisterCourseAdapter);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
