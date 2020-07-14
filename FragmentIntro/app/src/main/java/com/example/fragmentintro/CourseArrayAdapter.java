package com.example.fragmentintro;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fragmentintro.data.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseArrayAdapter extends ArrayAdapter<Course> {

    private List<Course> courses;
    private Context context;
    public CourseArrayAdapter(@NonNull Context context, int resource, List<Course> courses) {
        super(context, resource, courses);
        this.context=context;
        this.courses=courses;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Course course=courses.get(position);
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.listitem,null);
        ImageView imageView=view.findViewById(R.id.imageView);
        imageView.setImageResource(course.getImageResourceId(context));
        TextView textView=view.findViewById(R.id.coursename);
        textView.setText(course.getCourseName());

        return view;
    }
}
