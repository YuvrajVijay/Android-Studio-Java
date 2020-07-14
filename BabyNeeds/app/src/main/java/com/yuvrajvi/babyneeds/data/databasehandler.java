package com.yuvrajvi.babyneeds.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.yuvrajvi.babyneeds.R;
import com.yuvrajvi.babyneeds.model.Details;
import com.yuvrajvi.babyneeds.utils.utilclass;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class databasehandler extends SQLiteOpenHelper {
    //private final Context context;

    public databasehandler(Context context) {
        super(context, utilclass.DATABASE_NAME, null, utilclass.DATABASE_VERSION);
        //this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE="CREATE TABLE "+utilclass.TABLE_NAME+"("+utilclass.KEY_NAME+" TEXT PRIMARY KEY,"
                +utilclass.KEY_QUANTITY+" INTEGER,"+utilclass.KEY_COLOR+" TEXT,"+utilclass.KEY_SIZE+" INTEGER,"+utilclass.KEY_DATE_ADDED+
                " LONG"+")";
        Log.d("lalala", "onCreate: "+"created");
        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE= String.valueOf(R.string.del_table);
        db.execSQL(DROP_TABLE, new String[]{utilclass.TABLE_NAME});
        Log.d("lalala", "onCreate: "+"dcreated");
        onCreate(db);
    }
    public void addvalues(Details details){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(utilclass.KEY_NAME,details.getBabyItem());
        values.put(utilclass.KEY_QUANTITY,details.getItemQuantity());
        values.put(utilclass.KEY_COLOR,details.getItemColor());
        values.put(utilclass.KEY_SIZE,details.getItemSize());
        values.put(utilclass.KEY_DATE_ADDED,java.lang.System.currentTimeMillis());//timestamp

        db.insert(utilclass.TABLE_NAME,null,values);
        db.close();
    }
    public Details getvalues(String name){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.query(utilclass.TABLE_NAME,new String[]{utilclass.KEY_NAME,utilclass.KEY_QUANTITY,utilclass.KEY_COLOR,
                        utilclass.KEY_SIZE,utilclass.KEY_DATE_ADDED},
                utilclass.KEY_NAME+"=?",new String[]{name},
                null,null,null);

        if(cursor!=null){
            cursor.moveToFirst();
        }

        Details values=new Details();
        ///??????????try to use id passed directly
        values.setBabyItem(cursor.getString(0));
        values.setItemQuantity(Integer.parseInt(cursor.getString(1)));
        values.setItemColor(cursor.getString(2));
        values.setItemSize(Integer.parseInt(cursor.getString(3)));
        DateFormat dateFormat =DateFormat.getDateInstance();
        String formattedDate=dateFormat.format(new Date(cursor.getLong(4)).getTime());
        values.setDate_created(formattedDate);

        return values;
    }

    public List<Details> getallvalues(){
        List<Details> valueList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        String selectall="SELECT * FROM "+utilclass.TABLE_NAME;
        Cursor cursor=db.rawQuery(selectall,null);
        //Cursor cursor=db.rawQuery("SELECT * FROM "+utilclass.TABLE_NAME+" ORDER BY "+utilclass.KEY_DATE_ADDED+" DESC;",null);
//        Cursor cursor=db.query(utilclass.TABLE_NAME,new String[]{utilclass.KEY_NAME,utilclass.KEY_QUANTITY,utilclass.KEY_COLOR,
//                        utilclass.KEY_SIZE,utilclass.KEY_DATE_ADDED},
//                null,null,
//                null,null,null);
        if(cursor.moveToFirst()){
            do{
                Details values=new Details();
                values.setBabyItem(cursor.getString(0));
                values.setItemQuantity(Integer.parseInt(cursor.getString(1)));
                values.setItemColor(cursor.getString(2));
                values.setItemSize(Integer.parseInt(cursor.getString(3)));
                DateFormat dateFormat =DateFormat.getDateInstance();
                String formattedDate=dateFormat.format(new Date(cursor.getLong(4)).getTime());
                values.setDate_created(formattedDate);
                valueList.add(values);
            }while (cursor.moveToNext());
        }
        return valueList;
    }

    public int updatevalue(Details value){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(utilclass.KEY_NAME,value.getBabyItem());
        values.put(utilclass.KEY_QUANTITY,value.getItemQuantity());
        values.put(utilclass.KEY_COLOR,value.getItemColor());
        values.put(utilclass.KEY_SIZE,value.getItemSize());
        values.put(utilclass.KEY_DATE_ADDED,java.lang.System.currentTimeMillis());

        return db.update(utilclass.TABLE_NAME,values,utilclass.KEY_NAME+
                "=?",new String[]{String.valueOf(value.getBabyItem())});
    }
    public void deletevalue(Details value){
        SQLiteDatabase db=this.getWritableDatabase();

        Log.d("lalala", "deleteItem: "+"have a  day");
        db.delete(utilclass.TABLE_NAME,utilclass.KEY_NAME+"=?",new String[]{String.valueOf(value.getBabyItem())});
        Log.d("lalala", "deleteItem: "+"have a  day");
        db.close();
    }
    public int getcount(){
        SQLiteDatabase db=this.getReadableDatabase();
        String countquery="SELECT * FROM "+utilclass.TABLE_NAME;
        Cursor cursor=db.rawQuery(countquery,null);
        return cursor.getCount();
    }
}
