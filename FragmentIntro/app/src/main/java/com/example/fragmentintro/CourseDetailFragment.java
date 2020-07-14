package com.example.fragmentintro;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fragmentintro.data.Course;
import com.example.fragmentintro.data.CourseData;


public class CourseDetailFragment extends Fragment {
    Course course;

    //Required no-args constructor
    public CourseDetailFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("course_id")) {
            int position = bundle.getInt("course_id");

            course = new CourseData().courseList().get(position);



        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_course_detail, container, false);

        if (course != null) {
            TextView courseName = view.findViewById(R.id.detailCourseName);
            courseName.setText(course.getCourseName());


            ImageView courseImage = view.findViewById(R.id.detailCoureImage);
            courseImage.setImageResource(course.getImageResourceId(getActivity()));

            TextView courseDescription = view.findViewById(R.id.detailsCourseDescription);
            courseDescription.setText(course.getCourseName());
        }

        return view;

    }
}