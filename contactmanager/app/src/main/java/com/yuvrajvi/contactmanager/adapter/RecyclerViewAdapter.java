package com.yuvrajvi.contactmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuvrajvi.contactmanager.Details_activity;
import com.yuvrajvi.contactmanager.MainActivity;
import com.yuvrajvi.contactmanager.R;
import com.yuvrajvi.contactmanager.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Contact> contactList;

    public RecyclerViewAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.contect_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact=contactList.get(position);
        holder.name.setText(contact.getName());
        holder.phonenumber.setText(contact.getPhonenumber());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView phonenumber;
        public ImageView photo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //itemView.setOnClickListener(this);
            name=itemView.findViewById(R.id.name);
            phonenumber=itemView.findViewById(R.id.phone_number);
            photo=itemView.findViewById(R.id.photo);
            photo.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position= getAdapterPosition();
            Contact contact=contactList.get(position);
            switch (v.getId()){
                case R.id.photo:
                    Intent intent=new Intent(context, Details_activity.class);
                    intent.putExtra("name",contact.getName());
                    intent.putExtra("number",contact.getPhonenumber());

                    context.startActivity(intent);
                    break;
            }
            //String name=contact.getName();
        }
    }
}
