package com.example.socialdis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class datavalueadapter extends ArrayAdapter<datavalue> {

    private static final String TAG = "PersonListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView name;
        TextView value;
        TextView distance;
    }
    public datavalueadapter(Context context, int resource, ArrayList<datavalue> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String name = getItem(position).getName();
        String value = getItem(position).getValue();
        String distance = getItem(position).getDistance();

        //Create the person object with the information
        datavalue device = new datavalue(name,value,distance);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView tvname = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvvalue = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvdistance = (TextView) convertView.findViewById(R.id.textView3);

        tvname.setText(name);
        tvvalue.setText(value);
        tvdistance.setText(distance);



        return convertView;
    }
}

