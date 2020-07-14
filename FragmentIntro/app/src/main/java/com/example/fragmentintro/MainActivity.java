package com.example.fragmentintro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.fragmentintro.data.Course;

public class MainActivity extends AppCompatActivity
implements CourseListFragment.Callbacks {

    private boolean istwopage=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.detailContainer)!=null){
            istwopage=true;
        }
//        FragmentManager fragmentManager=getSupportFragmentManager();
//        Fragment fragment=fragmentManager.findFragmentById(R.id.myContainer);
//        if(fragment==null){
//            fragment=new CourseListFragment();
//            fragmentManager.beginTransaction()
//                    .add(R.id.myContainer,fragment)
//                    .commit();
//        }
    }


    @Override
    public void onItemSelected(Course course,int position) {
        if(istwopage){
            Bundle bundle=new Bundle();
            bundle.putInt("course_id",position);
            FragmentManager fragmentManager=getSupportFragmentManager();
            CourseDetailFragment fragment=new CourseDetailFragment();
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.detailContainer,fragment)
                    .commit();
        }else{
            Toast.makeText(this,"hello",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(MainActivity.this,CourseDetailActivity.class);
            intent.putExtra("course_id",position);
            startActivity(intent);
        }


        //Toast.makeText(this,"hello",Toast.LENGTH_LONG).show();
    }
}