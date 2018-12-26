package com.example.hp.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Hp on 5.1.2018.
 */

public class Adaptor extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<kullanici> mKisiListesi;

    public Adaptor(Activity activity, List<kullanici> kisiler) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mKisiListesi = kisiler;
    }

    @Override
    public int getCount() {
        return mKisiListesi.size();
    }

    @Override
    public kullanici getItem(int position) {
        //şöyle de olabilir: public Object getItem(int position)
        return mKisiListesi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satirView;

        satirView = mInflater.inflate(R.layout.satir, null);
        TextView text =
                (TextView) satirView.findViewById(R.id.isimsoyisim);
        text.setTypeface(text.getTypeface(), Typeface.BOLD);
        text.setTextSize(18);

        kullanici kisi = mKisiListesi.get(position);

        text.setText(kisi.getIsim()+" "+kisi.getSoyisim());


        return satirView;
    }
}
