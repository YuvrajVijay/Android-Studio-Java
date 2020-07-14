package com.example.fragmentintro;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.fragmentintro.data.Course;
import com.example.fragmentintro.data.CourseData;
import com.example.fragmentintro.utils.ScreenUtils;

import java.util.List;


public class CourseListFragment extends ListFragment {

    private Callbacks activity;
    List<Course> courses = new CourseData().courseList();

    public CourseListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScreenUtils screenUtils=new ScreenUtils(getActivity());

        Log.d("widthheight", "onCreate: "+screenUtils.getDpHeight()+" "+screenUtils.getDpWidth());

        CourseArrayAdapter courseArrayAdapter=new CourseArrayAdapter(getActivity(),
                R.layout.listitem,courses);
        setListAdapter(courseArrayAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.course_list_fragment, container, false);
        return view;
    }

    public interface Callbacks{
        public void onItemSelected(Course course,int position);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        Course course=courses.get(position);
        this.activity.onItemSelected(course,position);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.activity= (Callbacks) context;
    }
}