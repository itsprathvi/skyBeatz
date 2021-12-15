package com.example.listview2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomClass extends BaseAdapter {
    Context context;
    String CountryList[];
    int flags[];
    LayoutInflater inflater;

    public CustomClass(Context context, String[] countryList, int[] flags) {
        this.context = context;
        CountryList = countryList;
        this.flags = flags;
        this.inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return CountryList.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item, null);
        TextView country = convertView.findViewById(R.id.text_view);
        ImageView icon = convertView.findViewById(R.id.img_view);
        country.setText(CountryList[position]);
        icon.setImageResource(flags[position]);
        return convertView;
    }
}
