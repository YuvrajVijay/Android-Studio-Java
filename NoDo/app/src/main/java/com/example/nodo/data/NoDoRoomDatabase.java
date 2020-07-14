package com.example.nodo.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.nodo.model.NoDo;

@Database(entities = {NoDo.class},version = 1)
public abstract class NoDoRoomDatabase extends RoomDatabase {

    public static volatile NoDoRoomDatabase INSTANCE;
    public abstract NoDoDao noDoDao();

    public static NoDoRoomDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (NoDoRoomDatabase.class){
                if(INSTANCE==null){
                    //create our db
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),NoDoRoomDatabase.class,"nodo_database")
                            .addCallback(roomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomDatabaseCallback =new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateAsync(INSTANCE).execute();
        }
    };

    private static class PopulateAsync extends AsyncTask<Void,Void,Void> {

        private final NoDoDao nododao;
        public PopulateAsync(NoDoRoomDatabase instance){
            nododao=instance.noDoDao();
        }

        @Override
        protected Void doInBackground(Void... voids){
            nododao.deleteall();
            NoDo noDo=new NoDo("Buy a new Ferrari");
            nododao.insert(noDo);

            noDo=new NoDo("Buy a big house");
            nododao.insert(noDo);

            return null;
        }
    }

}
