package com.example.khayyam.my_farming;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by KHAYYAM on 11/22/2017.
 */

public class Urun {

    private int urun_id;
    private String urun_turu;
    private float urun_miktari;
    private String urun_miktar_turu;
    private String urun_hasat_tarihi;
    private String urun_ekim_tarihi;
    private float urun_birim_fiyat;
    private String urun_birim_turu;
    private float urun_toplam_fiyat;
    private ArrayList<LatLng> urun_konum;


//    public Urun(String urun_turu, String urun_miktari, String urun_miktar_turu, String urun_hasat_tarihi, String urun_ekim_tarihi, String urun_birim_fiyat, String urun_birim_turu) {
//
//        this.urun_turu = urun_turu;
//        this.urun_miktari = urun_miktari;
//        this.urun_miktar_turu = urun_miktar_turu;
//        this.urun_hasat_tarihi = urun_hasat_tarihi;


//        this.urun_ekim_tarihi = urun_ekim_tarihi;
//        this.urun_birim_fiyat = urun_birim_fiyat;
//        this.urun_birim_turu = urun_birim_turu;
//    }

    public Urun(int urun_id, String urun_turu, float urun_miktari, String urun_miktar_turu, String urun_hasat_tarihi, String urun_ekim_tarihi, float urun_birim_fiyat, String urun_birim_turu, float urun_toplam_fiyat, ArrayList<LatLng> urun_konum) {
        this.urun_id = urun_id;
        this.urun_turu = urun_turu;
        this.urun_miktari = urun_miktari;
        this.urun_miktar_turu = urun_miktar_turu;
        this.urun_hasat_tarihi = urun_hasat_tarihi;
        this.urun_ekim_tarihi = urun_ekim_tarihi;
        this.urun_birim_fiyat = urun_birim_fiyat;
        this.urun_birim_turu = urun_birim_turu;
        this.urun_toplam_fiyat = urun_toplam_fiyat;
        this.urun_konum = urun_konum;
    }

    public Urun(int urun_id, String urun_turu, float urun_miktari, String urun_miktar_turu, String urun_hasat_tarihi, String urun_ekim_tarihi, float urun_birim_fiyat, String urun_birim_turu, float urun_toplam_fiyat) {
        this.urun_id = urun_id;
        this.urun_turu = urun_turu;
        this.urun_miktari = urun_miktari;
        this.urun_miktar_turu = urun_miktar_turu;
        this.urun_hasat_tarihi = urun_hasat_tarihi;
        this.urun_ekim_tarihi = urun_ekim_tarihi;
        this.urun_birim_fiyat = urun_birim_fiyat;
        this.urun_birim_turu = urun_birim_turu;
        this.urun_toplam_fiyat = urun_toplam_fiyat;
    }


    public Urun(String urun_turu, float urun_miktari, String urun_miktar_turu, String urun_hasat_tarihi, String urun_ekim_tarihi, float urun_birim_fiyat, String urun_birim_turu, float urun_toplam_fiyat) {
        this.urun_turu = urun_turu;
        this.urun_miktari = urun_miktari;
        this.urun_miktar_turu = urun_miktar_turu;
        this.urun_hasat_tarihi = urun_hasat_tarihi;
        this.urun_ekim_tarihi = urun_ekim_tarihi;
        this.urun_birim_fiyat = urun_birim_fiyat;
        this.urun_birim_turu = urun_birim_turu;
        this.urun_toplam_fiyat = urun_toplam_fiyat;


    }

    public void setUrun_konum(ArrayList<LatLng> urun_konum) {
        this.urun_konum = urun_konum;
    }

    public String getUrun_turu() {
        return urun_turu;
    }

    public float getUrun_miktari() {
        return urun_miktari;
    }

    public String getUrun_miktar_turu() {
        return urun_miktar_turu;
    }

    public ArrayList<LatLng> getUrun_konum() {
        return urun_konum;
    }

    public String getUrun_hasat_tarihi() {
        return urun_hasat_tarihi;
    }

    public String getUrun_ekim_tarihi() {
        return urun_ekim_tarihi;
    }

    public float getUrun_birim_fiyat() {
        return urun_birim_fiyat;
    }

    public String getUrun_birim_turu() {
        return urun_birim_turu;
    }

    public float getUrun_toplam_fiyat() {
        return urun_toplam_fiyat;
    }

    public int getUrun_id() {
        return urun_id;
    }


}
