package com.nd2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {
    private Context mycontext;
 
   // ContextWrapper cw =new ContextWrapper(getActivty().getApplicationContext());
 
	private static final String DB_NAME = "Ukrainian_Explanatory_Dictionary.db"; 
    public SQLiteDatabase myDataBase;
 
	private String DB_PATH = "/data/data/com.nd2/databases/";
 
 
 
    public DataBaseHelper(Context context) throws IOException {
        super(context, DB_NAME, null, 1);
        this.mycontext=context;
        boolean dbexist = checkdatabase();
        if (dbexist) {
            System.out.println("База данных существует");
            opendatabase(); 
        } else {
            System.out.println("База данных не существует!");
            createdatabase();
        }
    }
 
    public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        if(dbexist) {
            //System.out.println("База данных существует");
        } else {
            this.getReadableDatabase();
            copydatabase();
        }
    }   
 
    private boolean checkdatabase() {
        //SQLiteDatabase checkdb = null;
        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch(SQLiteException e) {
            System.out.println("База данных не существует!");
        }
        return checkdb;
    }
 
    private void copydatabase()
    {
        Log.i("Database",
                "Новая база данных копируется на устройство!");
        byte[] buffer = new byte[1024];
        OutputStream myOutput = null;
        int length;
        // Открываем локальную БД как входящий поток
        InputStream myInput = null;
        try
        {
            myInput = mycontext.getAssets().open(DB_NAME);
            // Передаем данные из inputfile в outputfile
            myOutput = new FileOutputStream(DB_PATH + DB_NAME);
            while((length = myInput.read(buffer)) > 0)
            {
                myOutput.write(buffer, 0, length);
            }
            myOutput.close();
            myOutput.flush();
            myInput.close();
            Log.i("Database",
                    "Новая база данных скопирована на устройство");
 
 
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
 
    public void opendatabase() throws SQLException {
        // Открываем базу данных
        String mypath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }
 
    public synchronized void close() {
        if(myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }
 
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
 
	}
 
}
