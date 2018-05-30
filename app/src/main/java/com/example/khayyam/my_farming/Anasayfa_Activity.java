package com.example.khayyam.my_farming;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

/**
 * Created by Khayyam on 10/25/2017.
 */

public class Anasayfa_Activity extends Fragment {
    Button map;
    PieChart pie_chart;
    TextView toplam_fiyat,toplam_miktar;
    SwipeRefreshLayout anasayfa_yenile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.anasayfa_layout, container, false);

//        map=view.findViewById(R.id.google_maps);

        final sqlLite_service sqlLite_service=new sqlLite_service(getActivity());
        anasayfa_yenile = (SwipeRefreshLayout) view.findViewById(R.id.anasayfa_yenile);


        pie_chart = (PieChart) view.findViewById(R.id.pie_chart);
        toplam_fiyat=view.findViewById(R.id.anasayfa_toplam_fiyat);
        toplam_miktar=view.findViewById(R.id.anasayfa_toplam_miktar);

        toplam_fiyat.setText(sqlLite_service.getToplam_fiyat_miktar().get("toplam_fiyat")+" Tl");
        toplam_miktar.setText(sqlLite_service.getToplam_fiyat_miktar().get("toplam_miktar")+" KG");




        anasayfa_yenile.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toplam_fiyat.setText(sqlLite_service.getToplam_fiyat_miktar().get("toplam_fiyat")+" Tl");
                        toplam_miktar.setText(sqlLite_service.getToplam_fiyat_miktar().get("toplam_miktar")+" KG");
                        addDataSet(sqlLite_service);
                        anasayfa_yenile.setRefreshing(false);

                    }
                }, 500);


            }

        });

        Description description = new Description();
        description.setText("");
        pie_chart.setDescription(description);
        pie_chart.setRotationEnabled(false);
        pie_chart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pie_chart.setHoleRadius(12f);
        pie_chart.setTransparentCircleAlpha(0);
        pie_chart.setCenterTextSize(10);
        pie_chart.setEntryLabelColor(Color.parseColor("#222222"));
        //pieChart.setDrawEntryLabels(true);
        //pieChart.setEntryLabelTextSize(20);
        //More options just check out the documentation!


        addDataSet(sqlLite_service);
//        map.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                for(int i=0;i<sqlLite_service.getUrunSayi().size();i++)
//                {
//
//                    Log.d("urun_id", String.valueOf(sqlLite_service.getUrunSayi().get(i).get("urun_adi")));
//                    Log.d("urun_sayi", String.valueOf(sqlLite_service.getUrunSayi().get(i).get("urun_sayi")));
//
//
//
//
//
//
//
//
//
////                    Log.d("urun_id", String.valueOf(sqlLite_service.getUrunList().get(i).getUrun_id()));
////                    Log.d("urun_adi",sqlLite_service.getUrunList().get(i).getUrun_turu().toString());
////
////                    Log.d("----","----------------KONUMU----------------");
////
////
////                    for(int j=0;j<sqlLite_service.getKonumById(Integer.valueOf(sqlLite_service.getUrunList().get(i).getUrun_id())).size();j++)
////                    {
////
////                        Log.d("LatLng",sqlLite_service.getKonumById(Integer.valueOf(sqlLite_service.getUrunList().get(i).getUrun_id())).get(j).toString());
////                    }
//
//
//
//
////                    for(int j=0 ; j<sqlLite_service.getUrunKonumList().size();j++)
////                    {
////                        Log.d("konum_id",sqlLite_service.getUrunKonumList().get(i).get("konum_id").toString());
////                        Log.d("urun_id",sqlLite_service.getUrunKonumList().get(i).get("urun_id").toString());
////                        Log.d("konum_enlem",sqlLite_service.getUrunKonumList().get(i).get("konum_enlem").toString());
////                        Log.d("konum_boylam",sqlLite_service.getUrunKonumList().get(i).get("konum_boylam").toString());
////
////                    }
//
//
//                }
//
//            }
//        });

        return view;
    }


    private void addDataSet( sqlLite_service sqlLite_service) {
//        Log.d(TAG, "addDataSet started");

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i=0;i<sqlLite_service.getUrunSayi().size();i++) {

            xEntrys.add(sqlLite_service.getUrunSayi().get(i).get("urun_adi"));
            yEntrys.add(new PieEntry(Float.valueOf(sqlLite_service.getUrunSayi().get(i).get("urun_sayi")) , sqlLite_service.getUrunSayi().get(i).get("urun_adi")));
        }



        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys,"");
        pieDataSet.setSliceSpace(1);
        pieDataSet.setValueTextSize(12);



        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#EF5350"));
        colors.add(Color.parseColor("#42A5F5"));
        colors.add(Color.parseColor("#26A69A"));
        colors.add(Color.parseColor("#66BB6A"));
        colors.add(Color.parseColor("#FFA726"));


        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pie_chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pie_chart.setData(pieData);
        pie_chart.invalidate();
    }

}
