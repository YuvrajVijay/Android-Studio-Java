package com.yuvrajvi.contactmanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.yuvrajvi.contactmanager.R;
import com.yuvrajvi.contactmanager.model.Contact;
import com.yuvrajvi.contactmanager.util.utilclass;

import java.util.ArrayList;
import java.util.List;

public class databasehandler extends SQLiteOpenHelper {
    public databasehandler(Context context) {
        super(context, utilclass.DATABASE_NAME, null, utilclass.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE="CREATE TABLE "+utilclass.TABLE_NAME+"("+utilclass.KEY_ID+" INTEGER PRIMARY KEY,"+
                utilclass.KEY_NAME+" TEXT,"+utilclass.KEY_PHONE_NUMBER+" TEXT"+")";
        db.execSQL(CREATE_CONTACT_TABLE);
        Log.d("lala", "onCreate: "+"created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE= String.valueOf(R.string.del_table);
        db.execSQL(DROP_TABLE, new String[]{utilclass.DATABASE_NAME});
        Log.d("lala", "onCreate: "+"dcreated");

        onCreate(db);
    }
    public void addcontact(Contact contact){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(utilclass.KEY_NAME,contact.getName());
        values.put(utilclass.KEY_PHONE_NUMBER,contact.getPhonenumber());

        db.insert(utilclass.TABLE_NAME,null,values);
        db.close();
    }
    public Contact getcontact(int id){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.query(utilclass.TABLE_NAME,new String[]{utilclass.KEY_ID,utilclass.KEY_NAME,utilclass.KEY_PHONE_NUMBER},
                utilclass.KEY_ID+"=?",new String[]{String.valueOf(id)},
                null,null,null);

        if(cursor!=null){
            cursor.moveToFirst();
        }

        Contact contact=new Contact();
        ///??????????try to use id passed directly
        contact.setId(Integer.parseInt(cursor.getString(0)));
        contact.setName(cursor.getString(1));
        contact.setPhonenumber(cursor.getString(2));

        return contact;
    }

    public List<Contact> getallcontacts(){
        List<Contact> contactList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        String selectall="SELECT * FROM "+utilclass.TABLE_NAME;
        Cursor cursor=db.rawQuery(selectall,null);

        if(cursor.moveToFirst()){
            do{
                Contact contact=new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhonenumber(cursor.getString(2));
                contactList.add(contact);
            }while (cursor.moveToNext());
        }
        return contactList;
    }

    public int updatecontact(Contact contact){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(utilclass.KEY_NAME,contact.getName());
        values.put(utilclass.KEY_PHONE_NUMBER,contact.getPhonenumber());
        return db.update(utilclass.TABLE_NAME,values,utilclass.KEY_ID+
                "=?",new String[]{String.valueOf(contact.getId())});
    }
    public void deletecontect(Contact contact){
        SQLiteDatabase db=this.getWritableDatabase();


        db.delete(utilclass.TABLE_NAME,utilclass.KEY_ID+"=?",new String[]{String.valueOf(contact.getId())});
        db.close();
    }
    public int getcount(){
        SQLiteDatabase db=this.getReadableDatabase();
        String countquery="SELECT * FROM "+utilclass.TABLE_NAME;
        Cursor cursor=db.rawQuery(countquery,null);
        return cursor.getCount();
    }
}
