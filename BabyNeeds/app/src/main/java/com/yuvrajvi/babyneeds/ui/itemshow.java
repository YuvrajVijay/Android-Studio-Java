package com.yuvrajvi.babyneeds.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.snackbar.Snackbar;
import com.yuvrajvi.babyneeds.ListActivity;
import com.yuvrajvi.babyneeds.MainActivity;
import com.yuvrajvi.babyneeds.R;
import com.yuvrajvi.babyneeds.data.databasehandler;
import com.yuvrajvi.babyneeds.model.Details;

import java.text.MessageFormat;
import java.util.List;

public class itemshow extends RecyclerView.Adapter<itemshow.ViewHolder> {

    private Context context;
    private List<Details> contactList;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;
    private Button saveButton;
    private EditText babyItem;
    private EditText itemQuantity;
    private EditText itemColor;
    private EditText itemSize;
    private databasehandler db;

    public itemshow(Context context, List<Details> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.contentshow,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Details contact=contactList.get(position);
        holder.item__Name.setText(contact.getBabyItem());
        holder.item__Quantity.setText(MessageFormat.format("Quantity: {0}", Integer.toString(contact.getItemQuantity())));
        holder.item__Color.setText(MessageFormat.format("Color: {0}", contact.getItemColor()));
        holder.item__Size.setText(MessageFormat.format("Size: {0}", Integer.toString(contact.getItemSize())));
        holder.item__Date.setText(MessageFormat.format("Updated: {0}", contact.getDate_created()));
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView item__Name;
        public TextView item__Quantity;
        public TextView item__Size;
        public TextView item__Color;
        public TextView item__Date;
        public Button editButton,deleteButton;
        public ViewHolder(@NonNull View itemView,Context ctx) {
            super(itemView);
            context=ctx;
            //itemView.setOnClickListener(this);
            item__Name=itemView.findViewById(R.id.item_name);
            item__Size=itemView.findViewById(R.id.item_Size);
            item__Color=itemView.findViewById(R.id.item_Color);
            item__Quantity=itemView.findViewById(R.id.item_Quantity);
            item__Date=itemView.findViewById(R.id.item_Date);
            editButton=itemView.findViewById(R.id.editButton);
            deleteButton=itemView.findViewById(R.id.deleteButton);

            deleteButton.setOnClickListener(this);
            editButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //int position= getAdapterPosition();

            switch (v.getId()){
                case R.id.editButton:
                    createPopupDialog();
                    //editItem(id);
                    break;
                case R.id.deleteButton:
                    builder=new AlertDialog.Builder(context);
                    inflater = LayoutInflater.from(context);
                    View view=inflater.inflate(R.layout.confirmation,null);
                    Button nobutton=view.findViewById(R.id.conf_no_button);
                    Button yesbutton=view.findViewById(R.id.conf_yes_button);
                    builder.setView(view);
                    dialog=builder.create();
                    dialog.show();
                    nobutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    yesbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position= getAdapterPosition();
                            Details contact=contactList.get(position);
                            deleteItem(contact,position);
                            dialog.dismiss();
                        }
                    });

                    break;
            }
            //String name=contact.getName();
        }
        private void editItem(int id){

        }
        private void deleteItem(Details contact,int position){
            databasehandler db=new databasehandler(context);
            db.deletevalue(contact);
            contactList.remove(contact);
            notifyDataSetChanged();
            Log.d("lalala", "deleteItem: "+"have a nice day");

        }
        private void createPopupDialog() {

            int position=getAdapterPosition();
            final Details details=contactList.get(position);

            builder=new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view=inflater.inflate(R.layout.popup,null);
            babyItem=view.findViewById(R.id.babyItem);
            itemQuantity=view.findViewById(R.id.itemQuantity);
            itemSize=view.findViewById(R.id.itemSize);
            itemColor=view.findViewById(R.id.itemColor);
            saveButton=view.findViewById(R.id.saveButton);

            babyItem.setText(details.getBabyItem());
            itemQuantity.setText(String.valueOf(details.getItemQuantity()));
            itemSize.setText(String.valueOf(details.getItemSize()));
            itemColor.setText(details.getItemColor());
            builder.setView(view);
            dialog=builder.create();
            dialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databasehandler db=new databasehandler(context);
                    details.setBabyItem(String.valueOf(babyItem.getText()));
                    details.setItemQuantity(Integer.valueOf(String.valueOf(itemQuantity.getText())));
                    details.setItemColor(String.valueOf(itemColor.getText()));
                    details.setItemSize(Integer.valueOf(String.valueOf(itemSize.getText())));
                    if (!babyItem.getText().toString().isEmpty()
                            && !itemColor.getText().toString().isEmpty()
                            && !itemQuantity.getText().toString().isEmpty()
                            && !itemSize.getText().toString().isEmpty()) {
                        db.updatevalue(details);
                        notifyItemChanged(getAdapterPosition(),details);
                    }else {
                        Snackbar.make(v, "Empty Fields not Allowed", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                    List<Details> contactList1=db.getallvalues();
                    for(Details contact:contactList1){
                        //db.deletecontect(contact);
                        Log.d("MainActivity1", "onCreate: "+contact.getBabyItem());
                    }
                    Log.d("MainActivity1", "onClick: "+"end"+db.getcount());
                    Log.d("Count", "onCreate: "+db.getcount());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();

                            //TODO:Recycler view,itemshow.java,contentshow.xml
                            //context.startActivity(new Intent(context, ListActivity.class));

                        }
                    },1200);

                }
            });

        }

    }
}

