package com.example.funky.registeration_system;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTeacher extends AppCompatActivity {

    private Button AddTeacherButton;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String USERTYPE = "Teacher";
    private FirebaseDatabase mDatabase;
    private EditText TeacherEmail;
    private EditText TeacherPassword;
    private EditText TeacherName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        AddTeacherButton = (Button) findViewById(R.id.addteacherbutton);
        TeacherEmail=(EditText) findViewById(R.id.teacheremail);
        TeacherName=(EditText) findViewById(R.id.teachername);
        TeacherPassword=(EditText) findViewById(R.id.teacherpassword);



        final DatabaseReference UserRef = myRef.child("User");
        AddTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = TeacherEmail.getText().toString();
                final String password = TeacherPassword.getText().toString();
                final String Name = TeacherName.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(Name)
                                .build();
                        FirebaseUser USER = authResult.getUser();
                        USER.updateProfile(profileUpdates);
                        USER = mAuth.getCurrentUser();
                        DatabaseReference newUser = UserRef.child(USER.getUid());
                        newUser.child("Name").setValue(Name);
                        newUser.child("Email").setValue(email);
                        newUser.child("Password").setValue(password);
                        newUser.child("Type").setValue(USERTYPE);
                        newUser.child("Type_Name").setValue(USERTYPE + "_" + Name);
                        Toast.makeText(getApplicationContext(), "Name = " + Name + "\nEmail = " + USER.getEmail() + "\nPassword = " + password, Toast.LENGTH_LONG).show();
                         mAuth.signOut();
                        startActivity(new Intent(AddTeacher.this, LoginActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Registeration Failed\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
             /* DatabaseReference Newref = UserRef.push();
                Newref.child("Name").setValue(Name);
                Newref.child("Email").setValue(email);
                Newref.child("Password").setValue(password);
                String key =  Newref.getKey();*/
                //   Toast.makeText(getApplicationContext(), "Name = " + Name + "\nEmail = " + email + "\nkEY = " + key + "\nPassword = " + password, Toast.LENGTH_LONG).show();
                // Toast.makeText(getApplicationContext(), "Name = " + USER.getDisplayName() + "\nEmail = " + USER.getEmail(), Toast.LENGTH_LONG).show();
            }
        });
    }


}

