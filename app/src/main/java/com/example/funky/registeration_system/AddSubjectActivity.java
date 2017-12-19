package com.example.funky.registeration_system;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddSubjectActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextView subjectName;
    private Button saveSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        saveSubject = (Button)findViewById(R.id.saveSubjectID);

        saveSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase = FirebaseDatabase.getInstance().getReference().child("Courses");
                subjectName = (TextView)findViewById(R.id.subjectNameID);


                if (subjectName.getText().toString().trim().equals("") ) {

                    subjectName.setError("Please Enter Subject Name...");

                } else {

                    final String SubName = subjectName.getText().toString().trim().toUpperCase();
                    mDatabase.child(SubName).setValue(SubName).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                subjectName.setText(" ");
                                subjectName.setHint("Enter Subject Name");
                                Toast.makeText(AddSubjectActivity.this, "Subject " + SubName + " added successfully !", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(AddSubjectActivity.this, "Add subject failed Failed !", Toast.LENGTH_LONG).show();
                            }


                        }
                    });
                }

            }
        });

    }
}
