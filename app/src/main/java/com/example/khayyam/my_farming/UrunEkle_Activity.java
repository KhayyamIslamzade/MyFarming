package com.example.khayyam.my_farming;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Khayyam on 10/25/2017.
 */


public class UrunEkle_Activity extends Fragment {
    DatePickerDialog.OnDateSetListener hasat_dateSetListener;
    DatePickerDialog.OnDateSetListener ekim_dateSetListener;

//    firebase_service firebase_service;


    private Spinner spnr_urun_adi;
    private Spinner spnr_miktar_turu;
    private Spinner spnr_para_birimi;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View View = inflater.inflate(R.layout.urun_ekle_layout, container, false);

        hasat_tarihi = (EditText) View.findViewById(R.id.et_hasat_tarihi);
        ekim_tarihi = (EditText) View.findViewById(R.id.et_ekim_tarihi);
        img_bt_hasat_tarihi = (ImageButton) View.findViewById(R.id.img_bt_hasat_tarihi);
        img_bt_ekim_tarihi = (ImageButton) View.findViewById(R.id.img_bt_ekim_tarihi);
        tv_birim_fiyat = (TextView) View.findViewById(R.id.tv_birim_fiyat);
        et_birim_fiyat = (EditText) View.findViewById(R.id.et_birim_fiyat);
        et_miktar = (EditText) View.findViewById(R.id.et_miktar);
        btn_ekle = (Button) View.findViewById(R.id.btn_ekle);
        btn_getir = (Button) View.findViewById(R.id.btn_getir);

        spnr_urun_adi = (Spinner) View.findViewById(R.id.spnr_urun_adi);
        spnr_miktar_turu = (Spinner) View.findViewById(R.id.spnr_miktar_turu);
        spnr_para_birimi = (Spinner) View.findViewById(R.id.spnr_para_birimi);


//        fab = (FloatingActionButton)View.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        setDefault_EkimTarihi();
        dateListeners();
        urun_adi_spinner_doldur();
        miktar_birimi_spinner_doldur();
        para_birimi_spinner_doldur();
//        ekle_OnClick();

        btn_ekle.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {


                String urun_turu = spnr_urun_adi.getSelectedItem().toString();
                String urun_miktari=et_miktar.getText().toString().trim();
                String urun_miktar_turu=spnr_miktar_turu.getSelectedItem().toString();
                String urun_hasat_tarihi=hasat_tarihi.getText().toString().trim();
                String urun_ekim_tarihi=ekim_tarihi.getText().toString().trim();
                String urun_birim_fiyat=et_birim_fiyat.getText().toString().trim();
                String urun_birim_turu=spnr_para_birimi.getSelectedItem().toString();
                veri_ekle=new firebase_service(urun_turu , urun_miktari , urun_miktar_turu,urun_hasat_tarihi,urun_ekim_tarihi,urun_birim_fiyat,urun_birim_turu);
            }
        });


        return View;
    }

    private void urun_adi_spinner_doldur() {
        ArrayAdapter<CharSequence> urun_adi_spnr_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.urun_adi_dizisi, android.R.layout.simple_spinner_item);
        urun_adi_spnr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr_urun_adi.setAdapter(urun_adi_spnr_adapter);

    }
    private void miktar_birimi_spinner_doldur() {
        ArrayAdapter<CharSequence> miktar_birimi_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.miktar_birimi, android.R.layout.simple_spinner_item);
        miktar_birimi_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr_miktar_turu.setAdapter(miktar_birimi_adapter);

    }
    private void para_birimi_spinner_doldur() {
        ArrayAdapter<CharSequence> para_birimi_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.para_birimi, android.R.layout.simple_spinner_item);
        para_birimi_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr_para_birimi.setAdapter(para_birimi_adapter);

    }


//
//    @Override
//    public void onStart() {
//
//        super.onStart();
//
//
//        my_child_Ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
////                String message =dataSnapshot.getValue(String.class);
////                tv_birim_fiyat.setText(message);
//
//                Map<String, String> map = dataSnapshot.getValue(Map.class);
//                String isim = map.get("isim");
//                String yas = map.get("yas");
//                String cinsiyet = map.get("cinsiyet");
//                tv_birim_fiyat.setText("Isim:" + isim.toString() + " Yas:" + yas.toString() + " cinsiyet:" + cinsiyet.toString());
////                Toast.makeText(getActivity(),"Isim:"+isim+" Yas:"+yas+" cinsiyet:"+cinsiyet,Toast.LENGTH_LONG).show();
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//    }

    private void setDefault_EkimTarihi() {
        String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        ekim_tarihi.setText(date);
    }

    private void dateListeners() {
        hasat_dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String Date = dayOfMonth + "/" + month + "/" + year;
                hasat_tarihi.setText(Date);
            }
        };
        ekim_dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String Date = dayOfMonth + "/" + month + "/" + year;
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


//
//    private void ekle_OnClick() {
//        btn_ekle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String data=et_birim_fiyat.getText().toString();
//                Toast.makeText(getActivity(),"Eklendi:"+data,Toast.LENGTH_LONG).show();
//                my_child_Ref.setValue(data);
//
//
//
//
//            }
//        });
//    }

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


}
