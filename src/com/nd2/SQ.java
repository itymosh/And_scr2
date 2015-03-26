package com.nd2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQ extends SQLiteOpenHelper  {
	

	private static final String DATABASE_NAME = "SQ.db";
	private static final int DATABASE_VERSION = 1;
	public static final String TABLE_NAME = "contact_table";
	public static final String UID = "_id";
	public static final String CATNAME = "catname";
	public static String _ID = "_id";
	private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
			+ TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ CATNAME + " VARCHAR(255));";

	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
			+ TABLE_NAME;
	

	
	public SQ(Context context) {
		// TODO Auto-generated constructor stub
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
		

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("LOG_TAG", "ќбновление базы данных с версии " + oldVersion
				+ " до версии " + newVersion + ", которое удалит все старые данные");
		// ”дал€ем предыдущую таблицу при апгрейде
		db.execSQL(SQL_DELETE_ENTRIES);
		// —оздаЄм новый экземпл€р таблицы
		onCreate(db);

	}

}
