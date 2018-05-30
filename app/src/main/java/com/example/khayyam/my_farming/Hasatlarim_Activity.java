package com.example.khayyam.my_farming;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reginald.editspinner.EditSpinner;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Khayyam on 10/25/2017.
 */

public class Hasatlarim_Activity extends Fragment {


    DatePickerDialog.OnDateSetListener hasat_dateSetListener;
    DatePickerDialog.OnDateSetListener ekim_dateSetListener;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference urunler_referans = (DatabaseReference) database.getReference().child("urunler");

    private ListView list_view;
    public UrunAdapter adapter;
    private EditSpinner spnr_urun_adi, spnr_miktar_turu, spnr_para_birimi;
    private Button btn_ekle, btn_getir;
    private TextView tv_birim_fiyat;
    private EditText et_birim_fiyat, et_miktar, hasat_tarihi, ekim_tarihi;
    private ImageButton img_bt_hasat_tarihi, img_bt_ekim_tarihi;

    static private ArrayList<LatLng> urunun_konumu = null;
    static private ArrayList<Urun> urun_bilgileri = null;

    SwipeRefreshLayout mSwipeRefreshLayout;

    sqlLite_service sqlLite_service;
    String urun_id, urun_turu, urun_miktari, urun_miktar_turu,
            urun_ekim_tarihi, urun_hasat_tarihi,
            urun_birim_fiyat, urun_birim_turu;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View View = inflater.inflate(R.layout.hasatlarim_layout, container, false);

        list_view = (ListView) View.findViewById(R.id.list_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) View.findViewById(R.id.activity_main_swipe_refresh_layout);


        urun_bilgileri = new ArrayList<Urun>();
        urunun_konumu = new ArrayList<LatLng>();

        try {
            sqlLite_service = new sqlLite_service(getActivity());

            if(sqlLite_service.getUrunList().size()>0)
            urun_bilgileri = sqlLite_service.getUrunList();

            adapter = new UrunAdapter(urun_bilgileri, getActivity());
            list_view.setAdapter(adapter);
        } catch (NullPointerException e) {
            Log.d("HATA", e.toString());
        }



        adapter.notifyDataSetChanged();

        registerForContextMenu(list_view);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        urun_bilgileri = sqlLite_service.getUrunList();
                        adapter = new UrunAdapter(urun_bilgileri, getActivity());
                        list_view.setAdapter(adapter);
                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                }, 500);


            }

        });



        urunler_referans.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String key = dataSnapshot.getKey();
                urunler_referans.child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot veri : dataSnapshot.getChildren()) {
                            urunun_konumu = new ArrayList<LatLng>();
                            urun_id = veri.getRef().getParent().getKey();
                            urun_turu = veri.child("urun_turu").getValue(String.class);
                            urun_miktari = veri.child("urun_miktari").getValue(String.class);
                            urun_miktar_turu = veri.child("urun_miktar_turu").getValue(String.class);
                            urun_ekim_tarihi = veri.child("urun_ekim_tarihi").getValue(String.class);
                            urun_hasat_tarihi = veri.child("urun_hasat_tarihi").getValue(String.class);
                            urun_birim_fiyat = veri.child("urun_birim_fiyat").getValue(String.class);
                            urun_birim_turu = veri.child("urun_birim_turu").getValue(String.class);
//                            Log.d("urun_turu", String.valueOf(urun_turu));

                            if (veri.child("konum").hasChildren()) {
                                for (DataSnapshot konum : veri.child("konum").getChildren()) {
                                    String enlem = konum.child("enlem").getValue(String.class);
                                    String boylam = konum.child("boylam").getValue(String.class);
                                    LatLng konumlar = new LatLng(Double.valueOf(enlem), Double.valueOf(boylam));
                                    urunun_konumu.add(konumlar);
                                }
//                                urun_bilgileri.add(new Urun(urun_id, urun_turu, urun_miktari, urun_miktar_turu, urun_ekim_tarihi, urun_hasat_tarihi, urun_birim_fiyat, urun_birim_turu, urunun_konumu));
                                adapter.notifyDataSetChanged();
                            }

                            Toast.makeText(getActivity(),urun_turu,Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//



        return View;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Update");//groupId, itemId, order, title
        menu.add(0, v.getId(), 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;


        if (item.getTitle() == "Update") {

            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.urun_ekle_layout);

            dialog.setTitle("Urun Ekle");
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

            final Urun guncellenecek_veri = adapter.guncelle(position);
            Log.d("Urun", guncellenecek_veri.getUrun_turu());

            hasat_tarihi.setText(guncellenecek_veri.getUrun_hasat_tarihi());
            ekim_tarihi.setText(guncellenecek_veri.getUrun_ekim_tarihi());
            et_miktar.setText(String.valueOf(guncellenecek_veri.getUrun_miktari()));
            et_birim_fiyat.setText(String.valueOf(guncellenecek_veri.getUrun_birim_fiyat()));

            spnr_urun_adi.setText(guncellenecek_veri.getUrun_turu());
            spnr_miktar_turu.setText(guncellenecek_veri.getUrun_miktar_turu());
            spnr_para_birimi.setText(guncellenecek_veri.getUrun_birim_turu());


//            setDefault_EkimTarihi();
            dateListeners();
            spinner_doldur(R.array.urun_adi_dizisi, android.R.layout.simple_spinner_item, spnr_urun_adi);
            spinner_doldur(R.array.miktar_birimi, android.R.layout.simple_spinner_item, spnr_miktar_turu);
            spinner_doldur(R.array.para_birimi, android.R.layout.simple_spinner_item, spnr_para_birimi);


            btn_ekle.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {


                    String urun_turu = spnr_urun_adi.getText().toString();

                    float urun_miktari = Float.parseFloat(et_miktar.getText().toString());
                    String urun_miktar_turu = spnr_miktar_turu.getText().toString();
                    String urun_hasat_tarihi = hasat_tarihi.getText().toString().trim();
                    String urun_ekim_tarihi = ekim_tarihi.getText().toString().trim();
                    float urun_birim_fiyat = Float.parseFloat(et_birim_fiyat.getText().toString());
                    String urun_birim_turu = spnr_para_birimi.getText().toString();
                    float urun_toplam_fiyat=urun_miktari*urun_birim_fiyat;
                    sqlLite_service.guncelle(new Urun(guncellenecek_veri.getUrun_id(), urun_turu, urun_miktari, urun_miktar_turu, urun_hasat_tarihi, urun_ekim_tarihi, urun_birim_fiyat, urun_birim_turu,urun_toplam_fiyat));
//                    veri_ekle = new firebase_service(urun_turu, urun_miktari, urun_miktar_turu, urun_hasat_tarihi, urun_ekim_tarihi, urun_birim_fiyat, urun_birim_turu);


//                    firebase_service guncelle=new firebase_service();
//                    guncelle.urun_guncelle(guncellenecek_veri.getUrun_id(),urun_turu, urun_miktari, urun_miktar_turu, urun_hasat_tarihi, urun_ekim_tarihi, urun_birim_fiyat, urun_birim_turu);
                    spnr_urun_adi.setText("");
                    spnr_para_birimi.setSelection(0);
                    spnr_miktar_turu.setSelection(0);
                    et_miktar.setText("");
                    hasat_tarihi.setText("");
                    ekim_tarihi.setText("");
                    et_birim_fiyat.setText("");

                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Işlem Gerçekleştirildi.", Toast.LENGTH_SHORT).show();


                }
            });


            dialog.show();

            adapter.notifyDataSetChanged();
        } else if (item.getTitle() == "Delete") {
//            Toast.makeText(getActivity(),"calling code",Toast.LENGTH_LONG).show();
            int urun_id = adapter.sil(position);
            sqlLite_service.delete(urun_id);
            adapter.notifyDataSetChanged();
        } else {
            return false;
        }

        return true;
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

    @Override
    public void onStart() {
        super.onStart();


    }

}
