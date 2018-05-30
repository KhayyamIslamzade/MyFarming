package com.example.khayyam.my_farming;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Ekim_alanlari_HaritaActivity extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap googleMap;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference urunler_referans = (DatabaseReference) database.getReference().child("urunler");

    String urun_id, urun_turu, urun_miktari, urun_miktar_turu, urun_ekim_tarihi, urun_hasat_tarihi, urun_birim_fiyat, urun_birim_turu;
    String key;


    static private ArrayList<LatLng> urunun_konumu = null;
    static private ArrayList<Urun> urun_bilgileri = null;

    private BitmapDrawable sinir_marker, merkez_marker;
    private Bitmap sinir_bitmap, merkez_bitmap;
    private int sinir_marker_yukseklik, merkez_marker_yukseklik;
    private int sinir_marker_genislik, merkez_marker_genislik;
    private Bitmap sinir_marker_ciz, merkez_marker_ciz;
    private FloatingActionButton fab_yenile;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ekim_alanlari_, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        fab_yenile = view.findViewById(R.id.btn_yenile);


        urun_bilgileri = new ArrayList<Urun>();
        urunun_konumu = new ArrayList<LatLng>();


        sinir_marker_yukseklik = 15;
        sinir_marker_genislik = 15;

        merkez_marker_yukseklik = 60;
        merkez_marker_genislik = 60;

        sinir_marker = (BitmapDrawable) getResources().getDrawable(R.mipmap.ic_map_point);
        sinir_bitmap = sinir_marker.getBitmap();
        sinir_marker_ciz = Bitmap.createScaledBitmap(sinir_bitmap, sinir_marker_genislik, sinir_marker_yukseklik, false);


        merkez_marker = (BitmapDrawable) getResources().getDrawable(R.mipmap.ic_point_material);
        merkez_bitmap = merkez_marker.getBitmap();
        merkez_marker_ciz = Bitmap.createScaledBitmap(merkez_bitmap, merkez_marker_genislik, merkez_marker_yukseklik, false);




//        urunler_referans.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//                key = dataSnapshot.getKey();
//                urunler_referans.child(key).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot veri : dataSnapshot.getChildren()) {
//                            urunun_konumu = new ArrayList<LatLng>();
//
////                            Log.d("Key:", veri.getRef().getParent().getKey());
//                            urun_id=veri.getRef().getParent().getKey();
//                            urun_turu = veri.child("urun_turu").getValue(String.class);
//                            urun_miktari = veri.child("urun_miktari").getValue(String.class);
//                            urun_miktar_turu = veri.child("urun_miktar_turu").getValue(String.class);
//                            urun_ekim_tarihi = veri.child("urun_ekim_tarihi").getValue(String.class);
//                            urun_hasat_tarihi = veri.child("urun_hasat_tarihi").getValue(String.class);
//                            urun_birim_fiyat = veri.child("urun_birim_fiyat").getValue(String.class);
//                            urun_birim_turu = veri.child("urun_birim_turu").getValue(String.class);
////                            Log.d("urun_turu", String.valueOf(urun_turu));
//
//                            if (veri.child("konum").hasChildren()) {
//                                for (DataSnapshot konum : veri.child("konum").getChildren()) {
//                                    String enlem = konum.child("enlem").getValue(String.class);
//                                    String boylam = konum.child("boylam").getValue(String.class);
//                                    LatLng konumlar = new LatLng(Double.valueOf(enlem), Double.valueOf(boylam));
////                                    Log.d("Konumlar", String.valueOf(konumlar));
//                                    urunun_konumu.add(konumlar);
//                                }
////                                urun_bilgileri.add(new Urun(urun_id,urun_turu, urun_miktari, urun_miktar_turu, urun_ekim_tarihi, urun_hasat_tarihi, urun_birim_fiyat, urun_birim_turu, urunun_konumu));
//                                countPolygonPoints(urunun_konumu, urun_turu);
//                            }
//
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


        sqlLite_service sql_service = new sqlLite_service(getActivity());
        for (int i = 0; i < sql_service.getUrunList().size(); i++) {
            String urun_adi = sql_service.getUrunList().get(i).getUrun_turu().toString();

            Log.d("----", "----------------KONUMU----------------");

            Urun urun = sql_service.getUrunList().get(i);
            urunun_konumu = sql_service.getKonumById(urun.getUrun_id());
            urun_bilgileri.add(new Urun(urun.getUrun_id(), urun.getUrun_turu(), urun.getUrun_miktari(), urun.getUrun_miktar_turu(), urun.getUrun_hasat_tarihi(), urun.getUrun_ekim_tarihi(), urun.getUrun_birim_fiyat(), urun.getUrun_birim_turu(), urun.getUrun_toplam_fiyat(), urunun_konumu));

//            for(int j=0;j<urunun_konumu.size();j++)
//            {
//
//
//
//                Log.d("LatLng",urunun_konumu.get(j).toString());
//            }

//
//            if (urunun_konumu.size() >= 3) {
//                countPolygonPoints(urunun_konumu, urun_adi);
//
//            }


//                    for(int j=0 ; j<sqlLite_service.getUrunKonumList().size();j++)
//                    {
//                        Log.d("konum_id",sqlLite_service.getUrunKonumList().get(i).get("konum_id").toString());
//                        Log.d("urun_id",sqlLite_service.getUrunKonumList().get(i).get("urun_id").toString());
//                        Log.d("konum_enlem",sqlLite_service.getUrunKonumList().get(i).get("konum_enlem").toString());
//                        Log.d("konum_boylam",sqlLite_service.getUrunKonumList().get(i).get("konum_boylam").toString());
//
//                    }


        }


        return view;
    }

    public void countPolygonPoints(ArrayList<LatLng> konum, String urun_turu) {


        if (konum.size() > 0) {

            for (int i = 0; i < konum.size(); i++) {
                try {
                    Marker marker = googleMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f).position(konum.get(i)).icon(BitmapDescriptorFactory.fromBitmap(sinir_marker_ciz)));
                } catch (NullPointerException e) {
                    Log.d("HATA", "countPolygonPoints: " + e.toString());
                }


            }

            PolygonOptions polygonOptions = new PolygonOptions();
            polygonOptions.addAll(konum);
            polygonOptions.strokeColor(Color.parseColor("#00897B"));
            polygonOptions.strokeWidth(2);
            polygonOptions.fillColor(Color.argb(100, 0, 105, 92));
            Polygon polygon = googleMap.addPolygon(polygonOptions);

        } else
            Toast.makeText(getActivity(), "bos", Toast.LENGTH_LONG).show();

        polygon_merkez_bul_ciz(konum, urun_turu);


    }

    public void polygon_merkez_bul_ciz(ArrayList<LatLng> konumlar, String urun_turu) {

        double enlem = 0;
        double boylam = 0;
        int konum_sayisi = konumlar.size();

        for (LatLng konum : konumlar) {
            enlem += konum.latitude;
            boylam += konum.longitude;
        }

        LatLng konum = new LatLng(enlem / konum_sayisi, boylam / konum_sayisi);

        Marker marker = googleMap.addMarker(new MarkerOptions().position(konum).icon(BitmapDescriptorFactory.fromBitmap(merkez_marker_ciz)).title(urun_turu));
        marker.showInfoWindow();


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng ayder_yaylasi = new LatLng(40.952833, 41.096152);
//        googleMap.addMarker(new MarkerOptions().position(ayder_yaylasi).title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ayder_yaylasi));
        float zoomLevel = 6.0f; //This goes up to 21
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ayder_yaylasi, zoomLevel));


        googleMap.setOnMapClickListener(this);

        for (int i = 0; i < urun_bilgileri.size(); i++) {
            String urun_adi = urun_bilgileri.get(i).getUrun_turu();
            countPolygonPoints(urun_bilgileri.get(i).getUrun_konum(), urun_adi);
            Log.d("Urun:", urun_adi + " Konumu" + urun_bilgileri.get(i).getUrun_konum().toString());
        }


        fab_yenile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    sqlLite_service sql_service = new sqlLite_service(getActivity());
                    urun_bilgileri = new ArrayList<Urun>();
                    urunun_konumu = new ArrayList<LatLng>();

                    for (int i = 0; i < sql_service.getUrunList().size(); i++) {

                        googleMap.clear();
                        Urun urun = sql_service.getUrunList().get(i);
                        urunun_konumu = sql_service.getKonumById(urun.getUrun_id());
                        urun_bilgileri.add(new Urun(urun.getUrun_id(), urun.getUrun_turu(), urun.getUrun_miktari(), urun.getUrun_miktar_turu(), urun.getUrun_hasat_tarihi(), urun.getUrun_ekim_tarihi(), urun.getUrun_birim_fiyat(), urun.getUrun_birim_turu(), urun.getUrun_toplam_fiyat(), urunun_konumu));


                    }

                    for (int i = 0; i < urun_bilgileri.size(); i++) {
                        String urun_adi = urun_bilgileri.get(i).getUrun_turu();
                        countPolygonPoints(urun_bilgileri.get(i).getUrun_konum(), urun_adi);
                        Log.d("Urun:", urun_adi + " Konumu" + urun_bilgileri.get(i).getUrun_konum().toString());
                    }


                } catch (NullPointerException e) {
                    Log.d("HATA", e.toString());
                }


            }
        });


    }

    @Override
    public void onMapClick(LatLng latLng) {


//        for (int i = 0; i < urun_bilgileri.size(); i++) {
//            Log.d("urun",urun_bilgileri.get(i).getUrun_turu());
//            countPolygonPoints(urun_bilgileri.get(i).getUrun_konum(),urun_bilgileri.get(i).getUrun_turu());
//
//        }

    }
}
