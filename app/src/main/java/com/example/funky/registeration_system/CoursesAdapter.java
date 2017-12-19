package com.example.funky.registeration_system;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CoursesAdapter extends ArrayAdapter<String> {
  private FirebaseDatabase database = FirebaseDatabase.getInstance();
  private DatabaseReference myRef = database.getReference();
  private FirebaseAuth mAuth;
  private FirebaseAuth.AuthStateListener mAuthListener;
  private FirebaseDatabase mDatabase;

  ArrayList<String> courses;
  private static class ViewHolder {
    TextView name;
    ImageView delete;
  }

  public CoursesAdapter(Context context, ArrayList<String> courses) {
    super(context, R.layout.subject_item, courses);
    this.courses = courses;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    final String course = getItem(position);
    ViewHolder viewHolder; // view lookup cache stored in tag
    if (convertView == null) {
      viewHolder = new ViewHolder();
      LayoutInflater inflater = LayoutInflater.from(getContext());
      convertView = inflater.inflate(R.layout.subject_item, parent, false);
      viewHolder.name = (TextView) convertView.findViewById(R.id.course_name);
      viewHolder.delete = (ImageView) convertView.findViewById(R.id.delete_course);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }
    viewHolder.name.setText(course);
    viewHolder.delete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        courses.remove(position);
        notifyDataSetChanged();
        FirebaseUser currentUser = mAuth.getCurrentUser();

      myRef.child("StudentCourse/"+currentUser.getUid()).child(course).removeValue();
      }
    });
    return convertView;
  }
}
