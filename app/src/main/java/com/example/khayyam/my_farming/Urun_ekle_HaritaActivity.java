package com.example.khayyam.my_farming;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.google.android.gms.maps.model.PolylineOptions;
import com.reginald.editspinner.EditSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Urun_ekle_HaritaActivity extends Fragment implements GoogleMap.OnMapClickListener,
        GoogleMap.OnMapLongClickListener, OnMapReadyCallback {

    DatePickerDialog.OnDateSetListener hasat_dateSetListener;
    DatePickerDialog.OnDateSetListener ekim_dateSetListener;
    Context context;
    private ImageView harita_urun_ekle;
    private TextView polygon_ciz_btn_yazi;
    private Button polygon_ciz_btn_resim;
    private RelativeLayout harita_btn;
    private GoogleMap googleMap;
    private ArrayList<LatLng> arrayPoints = null;
    PolylineOptions polylineOptions;
    private boolean ilk_tiklama = false;
    private int sayac = 0;
    private boolean cizildimi = false;
    private LatLng mark_baslangic_konum;
    private EditSpinner spnr_urun_adi;
    private EditSpinner spnr_miktar_turu;
    private EditSpinner spnr_para_birimi;
    private Button btn_ekle;
    private Button btn_getir;
    private TextView tv_birim_fiyat;
    private EditText et_birim_fiyat;
    private EditText et_miktar;
    private EditText hasat_tarihi;
    private EditText ekim_tarihi;
    private ImageButton img_bt_hasat_tarihi;
    private ImageButton img_bt_ekim_tarihi;
    private FloatingActionButton fab;
    private firebase_service veri_ekle;
    private sqlLite_service sqlLite_service;


    private BitmapDrawable sinir_marker, merkez_marker;
    private Bitmap sinir_bitmap, merkez_bitmap;
    private int sinir_marker_yukseklik, merkez_marker_yukseklik;
    private int sinir_marker_genislik, merkez_marker_genislik;
    private Bitmap sinir_marker_ciz, merkez_marker_ciz;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_harita, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        arrayPoints = new ArrayList<LatLng>();
        context = getActivity();
        sqlLite_service = new sqlLite_service(getActivity());

        polygon_ciz_btn_yazi = view.findViewById(R.id.harita_btn_yazi);
        polygon_ciz_btn_resim = view.findViewById(R.id.harita_btn_resim);
        harita_btn = view.findViewById(R.id.harita_btn);
        harita_urun_ekle = view.findViewById(R.id.harita_urun_ekle_btn);

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


        set_harita_btn(false, 4);
        set_harita_urun_ekle_btn(false, 4);


        return view;
    }


    private void get_calendar_data(DatePickerDialog.OnDateSetListener dateSetListener) {
        Calendar cal = Calendar.getInstance();
        int yil = cal.get(Calendar.YEAR);
        int ay = cal.get(Calendar.MONTH);
        int gun = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.Theme_AppCompat_Dialog_MinWidth
                , dateSetListener, yil, ay, gun);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


    private void setDefault_EkimTarihi() {
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        ekim_tarihi.setText(date);
    }

    private void spinner_doldur(int string_dizisi, int layout, EditSpinner spinner) {
        ArrayAdapter<CharSequence> urun_adi_spnr_adapter = ArrayAdapter.createFromResource(getActivity(),
                string_dizisi, layout);
        urun_adi_spnr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(urun_adi_spnr_adapter);

    }


    private void dateListeners() {
        hasat_dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String Date = dayOfMonth + "-" + month + "-" + year;
                hasat_tarihi.setText(Date);
            }
        };
        ekim_dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String Date = dayOfMonth + "-" + month + "-" + year;
                ekim_tarihi.setText(Date);
            }
        };

        img_bt_hasat_tarihi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_calendar_data(hasat_dateSetListener);
            }
        });
        img_bt_ekim_tarihi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_calendar_data(ekim_dateSetListener);
            }
        });
    }

    private void set_harita_btn(boolean durum, int gorunum) {
        harita_btn.setEnabled(durum);
        harita_btn.setClickable(durum);
        harita_btn.setVisibility(gorunum);

    }

    private void set_harita_urun_ekle_btn(boolean durum, int gorunum) {
        harita_urun_ekle.setEnabled(durum);
        harita_urun_ekle.setClickable(durum);
        harita_urun_ekle.setVisibility(gorunum);
    }

    @Override
    public void onMapClick(LatLng konum) {
        if (cizildimi == false) {


            sayac++;
            if (sayac >= 3) {
                set_harita_btn(true, 0);
            } else {
                set_harita_btn(false, 4);

            }


            Marker marker = googleMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f).position(konum).icon(BitmapDescriptorFactory.fromBitmap(sinir_marker_ciz)));
            marker.showInfoWindow();
            arrayPoints.add(konum);
        }
    }

    public void map_clear() {
        sayac = 0;
        googleMap.clear();
        arrayPoints.clear();
    }

    @Override
    public void onMapLongClick(LatLng point) {

    }

    public void countPolygonPoints() {


        if (arrayPoints.size() >= 3 && cizildimi == false) {

//                checkClick = true;
            PolygonOptions polygonOptions = new PolygonOptions();
            polygonOptions.addAll(arrayPoints);
            polygonOptions.strokeColor(Color.parseColor("#00897B"));
            polygonOptions.strokeWidth(2);
            polygonOptions.fillColor(Color.argb(100, 0, 105, 92));
            Polygon polygon = googleMap.addPolygon(polygonOptions);
        }

//        }

    }

    public void polygon_merkez_bul_ciz(ArrayList<LatLng> konumlar) {

        double enlem = 0;
        double boylam = 0;
        int konum_sayisi = konumlar.size();

        for (LatLng konum : konumlar) {
            enlem += konum.latitude;
            boylam += konum.longitude;
        }

        LatLng konum = new LatLng(enlem / konum_sayisi, boylam / konum_sayisi);

        Marker marker = googleMap.addMarker(new MarkerOptions().position(konum).title("Merkez").icon(BitmapDescriptorFactory.fromBitmap(merkez_marker_ciz)));
        marker.showInfoWindow();


    }

    private void toast_goster(String text)
    {
     Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        this.googleMap = googleMap;
        googleMap = googleMap;
        LatLng ayder_yaylasi = new LatLng(40.952833, 41.096152);
//        googleMap.addMarker(new MarkerOptions().position(ayder_yaylasi).title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ayder_yaylasi));
        float zoomLevel = 6.0f; //This goes up to 21
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ayder_yaylasi, zoomLevel));


        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMapClickListener(this);
        googleMap.setOnMapLongClickListener(this);
        harita_listeners();


    }

    public void harita_listeners() {
        harita_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cizildimi == false) {
                    set_harita_urun_ekle_btn(true, 0);//goster
                    countPolygonPoints();
                    polygon_merkez_bul_ciz(arrayPoints);


                    cizildimi = true;
                    polygon_ciz_btn_resim.setBackgroundResource(R.mipmap.ic_harita_delete_drawing);
                    polygon_ciz_btn_yazi.setText("Temizle");

                } else {
                    set_harita_urun_ekle_btn(false, 4);//gizle
                    map_clear();
                    cizildimi = false;
                    polygon_ciz_btn_resim.setBackgroundResource(R.mipmap.ic_harita_cicek_btn);
                    polygon_ciz_btn_yazi.setText("Alanı Çiz");
                    set_harita_btn(false, 4);
                }

            }
        });


        harita_urun_ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.urun_ekle_layout);

                dialog.setTitle("Urun Ekle");

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;

                dialog.getWindow().setAttributes(lp);

                hasat_tarihi = (EditText) dialog.findViewById(R.id.et_hasat_tarihi);
                ekim_tarihi = (EditText) dialog.findViewById(R.id.et_ekim_tarihi);
                img_bt_hasat_tarihi = (ImageButton) dialog.findViewById(R.id.img_bt_hasat_tarihi);
                img_bt_ekim_tarihi = (ImageButton) dialog.findViewById(R.id.img_bt_ekim_tarihi);
                tv_birim_fiyat = (TextView) dialog.findViewById(R.id.tv_birim_fiyat);
                et_birim_fiyat = (EditText) dialog.findViewById(R.id.et_birim_fiyat);
                et_miktar = (EditText) dialog.findViewById(R.id.et_miktar);
                btn_ekle = (Button) dialog.findViewById(R.id.btn_ekle);
                btn_getir = (Button) dialog.findViewById(R.id.btn_getir);

                spnr_urun_adi = (EditSpinner) dialog.findViewById(R.id.spnr_urun_adi);
                spnr_miktar_turu = (EditSpinner) dialog.findViewById(R.id.spnr_miktar_turu);
                spnr_para_birimi = (EditSpinner) dialog.findViewById(R.id.spnr_para_birimi);
                spnr_urun_adi.setText("Mısır");
                spnr_miktar_turu.setText("KG");
                spnr_para_birimi.setText("Tl");


                setDefault_EkimTarihi();
                dateListeners();
                spinner_doldur(R.array.urun_adi_dizisi, android.R.layout.simple_spinner_item, spnr_urun_adi);
                spinner_doldur(R.array.miktar_birimi, android.R.layout.simple_spinner_item, spnr_miktar_turu);
                spinner_doldur(R.array.para_birimi, android.R.layout.simple_spinner_item, spnr_para_birimi);

                btn_ekle.setOnClickListener(new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {


                        if (TextUtils.isEmpty(spnr_urun_adi.getText().toString().trim())) {
                            toast_goster("Ürün boş girilemez.");
                        } else if (TextUtils.isEmpty(et_miktar.getText().toString())) {
                            toast_goster("Miktar boş girilemez.");
                        } else if (TextUtils.isEmpty(spnr_miktar_turu.getText().toString().trim())) {
                            toast_goster("Miktar Türü boş girilemez.");
                        } else if (TextUtils.isEmpty(ekim_tarihi.getText().toString().trim())) {
                            toast_goster("Ekim Tarihi boş girilemez.");
                        } else if (TextUtils.isEmpty(hasat_tarihi.getText().toString().trim())) {
                            toast_goster("Hasat tarihi boş girilemez.");
                        } else if (TextUtils.isEmpty(et_birim_fiyat.getText().toString().trim())) {
                            toast_goster("Birim fiyatı boş girilemez.");
                        } else if (TextUtils.isEmpty(spnr_para_birimi.getText().toString().trim())) {
                            toast_goster("Para birimi boş girilemez.");
                        } else {
                            String urun_turu = spnr_urun_adi.getText().toString();
                            float urun_miktari = Float.parseFloat(et_miktar.getText().toString());
                            String urun_miktar_turu = spnr_miktar_turu.getText().toString().trim();
                            String urun_ekim_tarihi = ekim_tarihi.getText().toString().trim();
                            String urun_hasat_tarihi = hasat_tarihi.getText().toString().trim();
                            float urun_birim_fiyat = Float.parseFloat(et_birim_fiyat.getText().toString());
                            String urun_birim_turu = spnr_para_birimi.getText().toString();
                            float urun_toplam_fiyat=urun_miktari*urun_birim_fiyat;



                            veri_ekle = new firebase_service(urun_turu, String.valueOf(urun_miktari), urun_miktar_turu, urun_hasat_tarihi, urun_ekim_tarihi,String.valueOf( urun_birim_fiyat), urun_birim_turu);
                            int urun_id= sqlLite_service.insert_urun(new Urun(urun_turu, urun_miktari, urun_miktar_turu, urun_hasat_tarihi, urun_ekim_tarihi, urun_birim_fiyat, urun_birim_turu,urun_toplam_fiyat));

                            for (int i = 0; i < arrayPoints.size(); i++) {

                            veri_ekle.urun_konum_ekle(String.valueOf(arrayPoints.get(i).latitude),String.valueOf(arrayPoints.get(i).longitude));
                                sqlLite_service.insert_konum(urun_id,arrayPoints.get(i));
                            }
                            spnr_urun_adi.setText("Mısır");
                            spnr_miktar_turu.setText("KG");
                            spnr_para_birimi.setText("Tl");
                            et_miktar.setText("");
                            hasat_tarihi.setText("");
                            ekim_tarihi.setText("");
                            et_birim_fiyat.setText("");
                            map_clear();
                            cizildimi=false;
                            set_harita_btn(false, 4);
                            set_harita_urun_ekle_btn(false, 4);
                            toast_goster("İşlem başarılı.");
                            dialog.dismiss();
                        }


                    }
                });


                dialog.show();


            }
        });


    }

}
