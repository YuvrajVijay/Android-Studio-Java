package com.example.fragmentintro.data;

import com.example.fragmentintro.R;

import java.util.ArrayList;

public class CourseData {
    private String[] courseNames={"First Course","Second Course","Third Course"};

    public ArrayList<Course> courseList(){
        ArrayList<Course> courses=new ArrayList<>();
        for(String c:courseNames){
            Course course=new Course(c,"ic_launcher_background");

            courses.add(course);
        }
        return courses;
    }
}
