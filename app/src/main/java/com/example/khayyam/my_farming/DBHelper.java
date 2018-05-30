package com.example.khayyam.my_farming;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper  extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 27;

    // Database Name
    private static final String DATABASE_NAME = "crud.db";

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        //All necessary tables you like to create will create here
        try {
            String CREATE_TABLE_URUN = " CREATE TABLE " + Urun_sqlLite.URUN_TABLE + " ( "
                    + Urun_sqlLite.KEY_urun_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
                    + Urun_sqlLite.KEY_urun_adi + " TEXT NOT NULL, "
                    + Urun_sqlLite.KEY_urun_miktari + " REAL, "
                    + Urun_sqlLite.KEY_urun_miktari_turu + " TEXT, "
                    + Urun_sqlLite.KEY_urun_ekim_tarihi + " DATE NOT NULL, "
                    + Urun_sqlLite.KEY_urun_hasat_tarihi + " DATE, "
                    + Urun_sqlLite.KEY_urun_fiyat + " REAL, "
                    + Urun_sqlLite.KEY_urun_toplam_fiyat + " REAL, "
                    + Urun_sqlLite.KEY_urun_fiyat_turu + " TEXT ) ;";

            db.execSQL(CREATE_TABLE_URUN);


            String CREATE_TABLE_KONUM = "CREATE TABLE " + Urun_sqlLite.KONUM_TABLE + " ( "
                    + Urun_sqlLite.KEY_konum_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
                    + Urun_sqlLite.KEY_konum_urun_ID + " INTEGER, "//FOREIGN KEY
                    + Urun_sqlLite.KEY_konum_enlem + " TEXT, "
                    + Urun_sqlLite.KEY_konum_boylam + " TEXT, "
                    + " FOREIGN KEY ( " +Urun_sqlLite.KEY_konum_urun_ID+" ) REFERENCES "+
                    Urun_sqlLite.URUN_TABLE+" ("+Urun_sqlLite.KEY_urun_ID +") ); ";

            db.execSQL(CREATE_TABLE_KONUM);
            Log.d("Calisti"," True");
        }catch (SQLException e)
        {
            Log.d("HATA",e.toString());
        }



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Urun_sqlLite.URUN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Urun_sqlLite.KONUM_TABLE);

        // Create tables again
        onCreate(db);

    }

}