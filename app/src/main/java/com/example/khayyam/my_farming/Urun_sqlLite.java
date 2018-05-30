package com.example.khayyam.my_farming;

/**
 * Created by KHAYYAM on 12/9/2017.
 */

public class Urun_sqlLite {

    // Labels table name
    public static final String URUN_TABLE = "Urunler";
    public static final String KONUM_TABLE = "Konumlar";

    // Labels Table Columns names
    public static final String KEY_urun_ID = "urun_id";
    public static final String KEY_urun_adi = "urun_adi";
    public static final String KEY_urun_miktari = "urun_miktar";
    public static final String KEY_urun_miktari_turu = "urun_miktar_turu";
    public static final String KEY_urun_fiyat = "urun_fiyat";
    public static final String KEY_urun_fiyat_turu = "urun_fiyat_turu";
    public static final String KEY_urun_ekim_tarihi = "urun_ekim_tarihi";
    public static final String KEY_urun_hasat_tarihi = "urun_hasat_tarihi";
    public static final String KEY_urun_toplam_fiyat = "urun_toplam_fiyat";


    public static final String KEY_konum_ID = "konum_id";
    public static final String KEY_konum_urun_ID = "konum_urun_id";
    public static final String KEY_konum_enlem = "konum_enlem";
    public static final String KEY_konum_boylam = "konum_boylam";

    // property help us to keep data
    public int urun_ID;
    public String urun_adi;
    public String urun_miktari;
    public String urun_fiyati;

}
