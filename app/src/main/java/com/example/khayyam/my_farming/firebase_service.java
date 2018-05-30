package com.example.khayyam.my_farming;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Khayyam on 11/6/2017.
 */

public class firebase_service {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference veritabani_referans = database.getReference();
    DatabaseReference urunler_referans= veritabani_referans.child("urunler").push();
    DatabaseReference bilgiler_referans;
    DatabaseReference konum_referans;
    DatabaseReference guncellenecek_veri_referans;

    private String urun_turu , urun_miktari , urun_miktar_turu,urun_hasat_tarihi,urun_ekim_tarihi,urun_birim_fiyat,urun_birim_turu;

    public firebase_service() {
    }
    public firebase_service(String urun_turu , String urun_miktari , String urun_miktar_turu, String urun_hasat_tarihi, String urun_ekim_tarihi, String urun_birim_fiyat, String urun_birim_turu)
    {
        this.urun_turu=urun_turu;
        this.urun_miktari=urun_miktari;
        this.urun_miktar_turu=urun_miktar_turu;
        this.urun_hasat_tarihi=urun_hasat_tarihi;
        this.urun_ekim_tarihi=urun_ekim_tarihi;
        this.urun_birim_fiyat=urun_birim_fiyat;
        this.urun_birim_turu=urun_birim_turu;


        bilgiler_referans=urunler_referans.child("bilgiler");


        bilgiler_referans.child("urun_turu").setValue(urun_turu);
        bilgiler_referans.child("urun_miktari").setValue(urun_miktari);
        bilgiler_referans.child("urun_miktar_turu").setValue(urun_miktar_turu);
        bilgiler_referans.child("urun_ekim_tarihi").setValue(urun_ekim_tarihi);
        bilgiler_referans.child("urun_hasat_tarihi").setValue(urun_hasat_tarihi);
        bilgiler_referans.child("urun_birim_fiyat").setValue(urun_birim_fiyat);
        bilgiler_referans.child("urun_birim_turu").setValue(urun_birim_turu);



    }



    public void urun_konum_ekle(String enlem , String boylam)
    {konum_referans=bilgiler_referans.child("konum").push();
    konum_referans.child("enlem").setValue(enlem);
    konum_referans.child("boylam").setValue(boylam);

    }
    public void urun_sil(String key)
    {
       veritabani_referans.child("urunler").child(key).removeValue();
    }
    public void urun_guncelle(String key ,String urun_turu , String urun_miktari , String urun_miktar_turu, String urun_hasat_tarihi, String urun_ekim_tarihi, String urun_birim_fiyat, String urun_birim_turu)
    {
        guncellenecek_veri_referans= veritabani_referans.child("urunler").child(key).child("bilgiler");


        guncellenecek_veri_referans.child("urun_turu").setValue(urun_turu);
        guncellenecek_veri_referans.child("urun_miktari").setValue(urun_miktari);
        guncellenecek_veri_referans.child("urun_miktar_turu").setValue(urun_miktar_turu);
        guncellenecek_veri_referans.child("urun_ekim_tarihi").setValue(urun_ekim_tarihi);
        guncellenecek_veri_referans.child("urun_hasat_tarihi").setValue(urun_hasat_tarihi);
        guncellenecek_veri_referans.child("urun_birim_fiyat").setValue(urun_birim_fiyat);
        guncellenecek_veri_referans.child("urun_birim_turu").setValue(urun_birim_turu);
    }



}
