package com.example.khayyam.my_farming;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by KHAYYAM on 11/22/2017.
 */

class UrunAdapter extends ArrayAdapter<Urun> implements View.OnClickListener {

    private ArrayList<Urun> dataSet;
    Context mContext;
    DecimalFormat df = new DecimalFormat("#.##");
    firebase_service firebase_service=new firebase_service();



    // View lookup cache
    private static class ViewHolder {
        TextView urun_turu, urun_miktari, urun_hasat_tarihi, urun_ekim_tarihi, urun_birim_fiyat,urun_toplam_fiyat;
    }

    public UrunAdapter(ArrayList<Urun> data, Context context) {
        super(context, R.layout.custom_hasatlarim_list, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public void onClick(View v) {

//        int position=(Integer) v.getTag();
//        Object object= getItem(position);
//        Urun urun=(Urun)object;
//
//        switch (v.getId())
//        {
//            case R.id.item_info:
//                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
//        }

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Urun dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_hasatlarim_list, parent, false);
            viewHolder.urun_turu = (TextView) convertView.findViewById(R.id.custom_tv_urun_adi);
            viewHolder.urun_miktari = (TextView) convertView.findViewById(R.id.custom_tv_miktar);
            viewHolder.urun_hasat_tarihi = (TextView) convertView.findViewById(R.id.custom_tv_hasilat_tarihi);
            viewHolder.urun_ekim_tarihi = (TextView) convertView.findViewById(R.id.custom_tv_ekim_tarihi);
            viewHolder.urun_toplam_fiyat=convertView.findViewById(R.id.custom_tv_toplam_fiyat);


            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

//        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.urun_turu.setText(dataModel.getUrun_turu());
        viewHolder.urun_miktari.setText(dataModel.getUrun_miktari()+ " " + dataModel.getUrun_miktar_turu());
        viewHolder.urun_toplam_fiyat.setText(String.valueOf(dataModel.getUrun_toplam_fiyat())+dataModel.getUrun_birim_turu());
        viewHolder.urun_hasat_tarihi.setText(String.valueOf(dataModel.getUrun_hasat_tarihi()));
        viewHolder.urun_ekim_tarihi.setText(String.valueOf(dataModel.getUrun_ekim_tarihi()));
//

//
//        if(dataModel.getUrun_miktari()!=0) {
//            float birim_fiyat = Float.valueOf(dataModel.getUrun_birim_fiyat());
//            float miktar = Float.valueOf(dataModel.getUrun_miktari());
//            float toplam_fiyat = birim_fiyat * miktar;
//            toplam_fiyat=Float.valueOf(df.format(toplam_fiyat));
//            viewHolder.urun_birim_fiyat.setText(String.valueOf(toplam_fiyat) + " " + dataModel.getUrun_birim_turu());
//        }
//        else
//        {
//            viewHolder.urun_birim_fiyat.setText("Belirsiz");
//        }

        return convertView;
    }


    public int sil(int position){
        int veri_id=dataSet.get(position).getUrun_id();
        dataSet.remove(dataSet.get(position));
//        firebase_service.urun_sil(veri_id);

//       Log.d("Silinen urun id",dataSet.get(position).getUrun_id());
//        String tmp="";
//        for ( int i=0;i<dataSet.size();i++  )
//        {
//            tmp=tmp+"{index:"+i+"  data:"+dataSet.get(i)+"}";
//            Log.d("array",tmp);
//
//        }

        return  veri_id;
    }
    public Urun guncelle(int position)
    {
        Urun veri=dataSet.get(position);

        return veri;
    }

}