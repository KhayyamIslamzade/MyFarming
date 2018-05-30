package com.example.khayyam.my_farming;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.LinkAddress;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

public class sqlLite_service {
    private DBHelper dbHelper;

    public sqlLite_service(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert_urun(Urun urun) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Urun_sqlLite.KEY_urun_adi, urun.getUrun_turu());
        values.put(Urun_sqlLite.KEY_urun_miktari, urun.getUrun_miktari());
        values.put(Urun_sqlLite.KEY_urun_miktari_turu, urun.getUrun_miktar_turu());
        values.put(Urun_sqlLite.KEY_urun_fiyat, urun.getUrun_birim_fiyat());
        values.put(Urun_sqlLite.KEY_urun_fiyat_turu, urun.getUrun_birim_turu());
        values.put(Urun_sqlLite.KEY_urun_ekim_tarihi, urun.getUrun_ekim_tarihi());
        values.put(Urun_sqlLite.KEY_urun_hasat_tarihi, urun.getUrun_hasat_tarihi());
        values.put(Urun_sqlLite.KEY_urun_toplam_fiyat, urun.getUrun_toplam_fiyat());

        // Inserting Row
        long urun_id = db.insert(Urun_sqlLite.URUN_TABLE, null, values);
        db.close(); // Closing database connection
        return (int) urun_id;
    }

    public int insert_konum(int urun_id, LatLng urun) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Urun_sqlLite.KEY_konum_urun_ID, urun_id);
        values.put(Urun_sqlLite.KEY_konum_enlem, String.valueOf(urun.latitude));
        values.put(Urun_sqlLite.KEY_konum_boylam, String.valueOf(urun.longitude));


        // Inserting Row
        long konum_id = db.insert(Urun_sqlLite.KONUM_TABLE, null, values);
        db.close(); // Closing database connection
        return (int) konum_id;
    }


    public void delete(int urun_id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Urun_sqlLite.URUN_TABLE, Urun_sqlLite.KEY_urun_ID + "= ?", new String[]{String.valueOf(urun_id)});
        db.delete(Urun_sqlLite.KONUM_TABLE, Urun_sqlLite.KEY_konum_urun_ID + "= ?", new String[]{String.valueOf(urun_id)});
        db.close(); // Closing database connection
    }

    public void guncelle(Urun urun) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Urun_sqlLite.KEY_urun_adi, urun.getUrun_turu());
        values.put(Urun_sqlLite.KEY_urun_miktari, urun.getUrun_miktari());
        values.put(Urun_sqlLite.KEY_urun_miktari_turu, urun.getUrun_miktar_turu());
        values.put(Urun_sqlLite.KEY_urun_fiyat, urun.getUrun_birim_fiyat());
        values.put(Urun_sqlLite.KEY_urun_fiyat_turu, urun.getUrun_birim_turu());
        values.put(Urun_sqlLite.KEY_urun_ekim_tarihi, urun.getUrun_ekim_tarihi());
        values.put(Urun_sqlLite.KEY_urun_hasat_tarihi, urun.getUrun_hasat_tarihi());


        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Urun_sqlLite.URUN_TABLE, values, Urun_sqlLite.KEY_urun_ID + "= ?", new String[]{String.valueOf(urun.getUrun_id())});
        db.close(); // Closing database connection
    }

    public HashMap<String, String> getToplam_fiyat_miktar() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        HashMap<String, String> urun = new HashMap<String, String>();
        try {
            String selectQuery = "SELECT  "
                    + " SUM ( " + Urun_sqlLite.KEY_urun_miktari + " ) AS toplam_miktar , "
                    + " SUM (" + Urun_sqlLite.KEY_urun_toplam_fiyat + " ) AS toplam_fiyat" +
                    " FROM " + Urun_sqlLite.URUN_TABLE;


            Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list

            if (cursor.moveToFirst()) {
                do {

                    urun.put("toplam_fiyat", cursor.getString(cursor.getColumnIndex("toplam_fiyat")));
                    urun.put("toplam_miktar", cursor.getString(cursor.getColumnIndex("toplam_miktar")));


                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();

        } catch (SQLException e) {
            Log.d("HATA", e.toString());


        }

        return urun;
    }


    public ArrayList<Urun> getUrunList() {
        //Open connection to read only

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Urun_sqlLite.KEY_urun_ID + "," +
                Urun_sqlLite.KEY_urun_adi + "," +
                Urun_sqlLite.KEY_urun_miktari + "," +
                Urun_sqlLite.KEY_urun_miktari_turu + "," +
                Urun_sqlLite.KEY_urun_fiyat + "," +
                Urun_sqlLite.KEY_urun_fiyat_turu + "," +
                Urun_sqlLite.KEY_urun_ekim_tarihi + "," +
                Urun_sqlLite.KEY_urun_hasat_tarihi + "," +
                Urun_sqlLite.KEY_urun_toplam_fiyat +
                " FROM " + Urun_sqlLite.URUN_TABLE + " ORDER BY DATE( " +Urun_sqlLite.KEY_urun_ekim_tarihi+" ) ASC";




        //Student student = new Student();
        ArrayList<Urun> urunList = new ArrayList<Urun>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
//                HashMap<String, String> urun = new HashMap<String, String>();
                String urun_turu, urun_miktar_turu, urun_ekim_tarihi, urun_hasat_tarihi, urun_birim_turu;
                int urun_id;
                float urun_miktari, urun_birim_fiyat, urun_toplam_fiyat;
                urun_id = cursor.getInt(cursor.getColumnIndex(Urun_sqlLite.KEY_urun_ID));
                urun_turu = cursor.getString(cursor.getColumnIndex(Urun_sqlLite.KEY_urun_adi));
                urun_miktari = cursor.getFloat(cursor.getColumnIndex(Urun_sqlLite.KEY_urun_miktari));
                urun_miktar_turu = cursor.getString(cursor.getColumnIndex(Urun_sqlLite.KEY_urun_miktari_turu));
                urun_ekim_tarihi = cursor.getString(cursor.getColumnIndex(Urun_sqlLite.KEY_urun_ekim_tarihi));
                urun_hasat_tarihi = cursor.getString(cursor.getColumnIndex(Urun_sqlLite.KEY_urun_hasat_tarihi));
                urun_birim_fiyat = cursor.getFloat(cursor.getColumnIndex(Urun_sqlLite.KEY_urun_fiyat));
                urun_birim_turu = cursor.getString(cursor.getColumnIndex(Urun_sqlLite.KEY_urun_fiyat_turu));
                urun_toplam_fiyat = cursor.getFloat(cursor.getColumnIndex(Urun_sqlLite.KEY_urun_toplam_fiyat));


//                urun.put("urun_id", cursor.getString(cursor.getColumnIndex(Urun_sqlLite.KEY_urun_ID)));
//                urun.put("urun_adi", cursor.getString(cursor.getColumnIndex(Urun_sqlLite.KEY_urun_adi)));
//                urun.put("urun_miktar", cursor.getString(cursor.getColumnIndex(Urun_sqlLite.KEY_urun_miktari)));
//                urun.put("urun_miktar_turu", cursor.getString(cursor.getColumnIndex(Urun_sqlLite.KEY_urun_miktari_turu)));
//                urun.put("urun_fiyat", cursor.getString(cursor.getColumnIndex(Urun_sqlLite.KEY_urun_fiyat)));
//                urun.put("urun_fiyat_turu", cursor.getString(cursor.getColumnIndex(Urun_sqlLite.KEY_urun_fiyat_turu)));
//                urun.put("urun_ekim_tarihi", cursor.getString(cursor.getColumnIndex(Urun_sqlLite.KEY_urun_ekim_tarihi)));
//                urun.put("urun_hasat_tarihi", cursor.getString(cursor.getColumnIndex(Urun_sqlLite.KEY_urun_hasat_tarihi)));
                urunList.add(new Urun(urun_id, urun_turu, urun_miktari, urun_miktar_turu, urun_hasat_tarihi, urun_ekim_tarihi, urun_birim_fiyat, urun_birim_turu, urun_toplam_fiyat));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return urunList;

    }


    public ArrayList<HashMap<String, String>> getUrunSayi() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        SELECT COUNT(DISTINCT column_name) AS some_alias FROM table_name

//        select distinct columnName, count(columnName) as CountOf from tableName group by columnName
        ArrayList<HashMap<String, String>> urun_sayi = new ArrayList<HashMap<String, String>>();
        try {
            String selectQuery = "SELECT DISTINCT " +
                    Urun_sqlLite.KEY_urun_adi + " , " +
                    "COUNT(" + Urun_sqlLite.KEY_urun_adi + ")"
                    + " AS urun_sayi"
                    + " FROM " + Urun_sqlLite.URUN_TABLE + " GROUP BY " + Urun_sqlLite.KEY_urun_adi;


            Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list

            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> urun = new HashMap<String, String>();
                    urun.put("urun_adi", cursor.getString(cursor.getColumnIndex(Urun_sqlLite.KEY_urun_adi)));
                    urun.put("urun_sayi", cursor.getString(cursor.getColumnIndex("urun_sayi")));
                    urun_sayi.add(urun);

                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();

        } catch (SQLException e) {
            Log.d("HATA", e.toString());


        }

        return urun_sayi;
    }


    public ArrayList<HashMap<String, String>> getUrunKonumList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

//        var command = conn.CreateCommand("SELECT tblWorkers.SomeFieldYouWant, tblWorkers.SomeOtherFieldYouWant, ... ," +
//                " tblTitles.Title FROM tblWorkers LEFT JOIN tblTitles ON tblWorkers.TitleID = tblTitles.id");

        //Student student = new Student();
        ArrayList<HashMap<String, String>> urun_konum_List = new ArrayList<HashMap<String, String>>();
        try {
            String selectQuery = "SELECT  " +
                    Urun_sqlLite.KEY_konum_ID + "," +
                    Urun_sqlLite.KEY_konum_urun_ID + "," +
                    Urun_sqlLite.KEY_konum_enlem + "," +
                    Urun_sqlLite.KEY_konum_boylam + ""
                    + " FROM " + Urun_sqlLite.KONUM_TABLE
                    + " JOIN " + Urun_sqlLite.URUN_TABLE
                    + " ON " + Urun_sqlLite.KONUM_TABLE + "." + Urun_sqlLite.KEY_konum_urun_ID + " = " + Urun_sqlLite.URUN_TABLE + "." + Urun_sqlLite.KEY_urun_ID;


            Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list

            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> urun = new HashMap<String, String>();
                    urun.put("konum_id", cursor.getString(cursor.getColumnIndex(Urun_sqlLite.KEY_konum_ID)));
                    urun.put("urun_id", cursor.getString(cursor.getColumnIndex(Urun_sqlLite.KEY_konum_urun_ID)));
                    urun.put("konum_enlem", cursor.getString(cursor.getColumnIndex(Urun_sqlLite.KEY_konum_enlem)));
                    urun.put("konum_boylam", cursor.getString(cursor.getColumnIndex(Urun_sqlLite.KEY_konum_boylam)));
                    urun_konum_List.add(urun);

                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();

        } catch (SQLException e) {
            Log.d("HATA", e.toString());


        }


//        String selectQuery = "SELECT  " +
//                Urun_sqlLite.KEY_konum_ID + "," +
//                Urun_sqlLite.KEY_konum_enlem + "," +
//                Urun_sqlLite.KEY_konum_urun_ID + "," +
//                Urun_sqlLite.KEY_konum_boylam + ","
//                + " FROM " + Urun_sqlLite.KONUM_TABLE
//                + " LEFT JOIN " + Urun_sqlLite.URUN_TABLE
//                + " ON " + Urun_sqlLite.KEY_konum_urun_ID + " = " + Urun_sqlLite.URUN_TABLE + "." + Urun_sqlLite.KEY_urun_ID;


        return urun_konum_List;

    }


    public Urun_sqlLite getUrunById(int Id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Urun_sqlLite.KEY_urun_ID + "," +
                Urun_sqlLite.KEY_urun_adi + "," +
                Urun_sqlLite.KEY_urun_miktari + "," +
                Urun_sqlLite.KEY_urun_fiyat +
                " FROM " + Urun_sqlLite.URUN_TABLE
                + " WHERE " +
                Urun_sqlLite.KEY_urun_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount = 0;
        Urun_sqlLite urun = new Urun_sqlLite();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});

        if (cursor.moveToFirst()) {
            do {
                urun.urun_ID = cursor.getInt(cursor.getColumnIndex(Urun_sqlLite.KEY_urun_ID));
                urun.urun_adi = cursor.getString(cursor.getColumnIndex(Urun_sqlLite.KEY_urun_adi));
                urun.urun_miktari = cursor.getString(cursor.getColumnIndex(Urun_sqlLite.KEY_urun_miktari));
                urun.urun_fiyati = cursor.getString(cursor.getColumnIndex(Urun_sqlLite.KEY_urun_fiyat));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return urun;
    }


    public ArrayList<LatLng> getKonumById(int Id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Urun_sqlLite.KEY_konum_ID + "," +
                Urun_sqlLite.KEY_konum_urun_ID + "," +
                Urun_sqlLite.KEY_konum_enlem + "," +
                Urun_sqlLite.KEY_konum_boylam + ""
                + " FROM " + Urun_sqlLite.KONUM_TABLE
                + " WHERE " +
                Urun_sqlLite.KEY_konum_urun_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount = 0;
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});

        ArrayList<LatLng> konum = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String enlem, boylam;
//
//                HashMap<String, String> urun = new HashMap<String, String>();
//                urun.put("konum_id", cursor.getString(cursor.getColumnIndex(Urun_sqlLite.KEY_konum_ID)));
//                urun.put("urun_id", cursor.getString(cursor.getColumnIndex(Urun_sqlLite.KEY_konum_urun_ID)));
//                urun.put("konum_enlem", cursor.getString(cursor.getColumnIndex(Urun_sqlLite.KEY_konum_enlem)));
//                urun.put("konum_boylam", cursor.getString(cursor.getColumnIndex(Urun_sqlLite.KEY_konum_boylam)));

                enlem = cursor.getString(cursor.getColumnIndex(Urun_sqlLite.KEY_konum_enlem));
                boylam = cursor.getString(cursor.getColumnIndex(Urun_sqlLite.KEY_konum_boylam));
                LatLng konum_olustur = new LatLng(Double.valueOf(enlem), Double.valueOf(boylam));
                konum.add(konum_olustur);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return konum;
    }
}