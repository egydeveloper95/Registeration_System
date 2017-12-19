package com.example.funky.registeration_system;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AssignSubject extends AppCompatActivity {

    Spinner spinner;
    ArrayAdapter<String> adapter;
    private FirebaseDatabase Database = FirebaseDatabase.getInstance();
    private DatabaseReference mRef = Database.getReference("Courses");
    private EditText TeacherName;
    private Button AssignSubjectToTeacherButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_subject);
        TeacherName = (EditText) findViewById(R.id.teachername);
        AssignSubjectToTeacherButton = (Button) findViewById(R.id.AssignSubject);

        AssignSubjectToTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Course = spinner.getSelectedItem().toString();
               final String Teacher_Name = TeacherName.getText().toString();
               final DatabaseReference tc = Database.getReference("TeacherCourse");
                DatabaseReference u=Database.getReference("User");
                u.orderByChild("Type_Name").equalTo("Teacher_"+Teacher_Name).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()){
                            tc.orderByChild("Teacher").equalTo(Teacher_Name).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.hasChildren())
                                        Toast.makeText(getApplicationContext(), "This Teacher Already Has A Subject ", Toast.LENGTH_LONG).show();
                                    else {
                                        String Course = spinner.getSelectedItem().toString();
                                        String Teacher_Name = TeacherName.getText().toString();
                                        DatabaseReference tc = Database.getReference("TeacherCourse");
                                        DatabaseReference newtc = tc.push();
                                        newtc.child("Course").setValue(Course);
                                        newtc.child("Teacher").setValue(Teacher_Name);
                                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                          //  Toast.makeText(AssignSubject.this, "Teacher", Toast.LENGTH_SHORT).show();
                        } else{

                            Toast.makeText(AssignSubject.this, "Invalid Teacher Name ", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
              /*  tc.orderByChild("Teacher").equalTo(Teacher_Name).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren())
                            Toast.makeText(getApplicationContext(), "Invalid Teacher Name", Toast.LENGTH_LONG).show();
                        else {
                            String Course = spinner.getSelectedItem().toString();
                            String Teacher_Name = TeacherName.getText().toString();
                            DatabaseReference tc = Database.getReference("TeacherCourse");
                            DatabaseReference newtc = tc.push();
                            newtc.child("Course").setValue(Course);
                            newtc.child("Teacher").setValue(Teacher_Name);
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/


            }
        });
        spinner = (Spinner) findViewById(R.id.spinner);
        final List<String> Courses = new ArrayList<String>();
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot Course : dataSnapshot.getChildren()) {
                    Courses.add(Course.getValue().toString());
                }
                adapter = new ArrayAdapter<>(AssignSubject.this, android.R.layout.simple_spinner_item, Courses);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
