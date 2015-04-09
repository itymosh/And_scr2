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
	public static final String TABLE_NAME = "Main";
//	private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+ TABLE_NAME+" ( Code Integer, Name STRING,Value Integer);";
//	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "+ TABLE_NAME;
    private Context mycontext;
	private static final String DB_NAME = "Ukrainian_Explanatory_Dictionary.db"; 
    public SQLiteDatabase myDataBase;
	private String DB_PATH = "/data/data/com.nd2/databases/";

	
    public DataBaseHelper(Context context) throws IOException {
        super(context, DB_NAME, null, 1);
        this.mycontext=context;
        boolean dbexist = checkdatabase();
        if (dbexist) {
            System.out.println("���� ������ ����������");
            opendatabase(); 
            
        } else {
            System.out.println("���� ������ �� ����������!");
            createdatabase();
        }
    }
 
    public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        if(dbexist) {
            //System.out.println("���� ������ ����������");
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
            System.out.println("���� ������ �� ����������!");
        }
        return checkdb;
    }
 
    private void copydatabase()
    {
        Log.i("Database",
                "����� ���� ������ ���������� �� ����������!");
        byte[] buffer = new byte[1024];
        OutputStream myOutput = null;
        int length;
        // ��������� ��������� �� ��� �������� �����
        InputStream myInput = null;
        try
        {
            myInput = mycontext.getAssets().open(DB_NAME);
            // �������� ������ �� inputfile � outputfile
            myOutput = new FileOutputStream(DB_PATH + DB_NAME);
            while((length = myInput.read(buffer)) > 0)
            {
                myOutput.write(buffer, 0, length);
            }
            myOutput.close();
            myOutput.flush();
            myInput.close();
            Log.i("Database",
                    "����� ���� ������ ����������� �� ����������");
 
 
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
 
    private void copynewdatabase()
    {
        Log.i("Database",
                "����� ���� ������ ���������� �� ����������!");
        byte[] buffer = new byte[1024];
        OutputStream myOutput = null;
        int length;
        // ��������� ��������� �� ��� �������� �����
        InputStream myInput = null;
        try
        {
            myInput = mycontext.getAssets().open(DB_NAME);
            // �������� ������ �� inputfile � outputfile
            myOutput = new FileOutputStream(DB_PATH + DB_NAME);
            while((length = myInput.read(buffer)) > 0)
            {
                myOutput.write(buffer, 0, length);
            }
            myOutput.close();
            myOutput.flush();
            myInput.close();
            Log.i("Database",
                    "����� ���� ������ ����������� �� ����������");
 
 
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public void opendatabase() throws SQLException {
        // ��������� ���� ������
        String mypath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
       // ATTACH "database2file" AS db2
    }
 
    public synchronized void close() {
        if(myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }
 
	@Override
	public void onCreate(SQLiteDatabase arg0) {
	//	arg0.execSQL(SQL_CREATE_ENTRIES);
 //
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int oldVersion, int newVersion) {
		Log.w("LOG_TAG", "���������� ���� ������ � ������ " + oldVersion
				+ " �� ������ " + newVersion + ", ������� ������ ��� ������ ������");
	//	arg0.execSQL(SQL_DELETE_ENTRIES);
		onCreate(arg0);
        File dbfile = new File(DB_PATH + DB_NAME);
		SQLiteDatabase.deleteDatabase(dbfile);  //deleteDatabase = API 16 or higher LEVEL ONLY !!
        copynewdatabase();
	}
 
}
