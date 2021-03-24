package com.example.androidhigh.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.androidhigh.Model.MyCourse;
import com.example.androidhigh.R;

import java.util.ArrayList;

public class Adapter_Spinner extends BaseAdapter {
    Context context;
    ArrayList<MyCourse> KhoaHoc;

    public Adapter_Spinner(Context context, ArrayList<MyCourse> KhoaHoc) {
        this.context = context;
        this.KhoaHoc = KhoaHoc;
    }

    @Override
    public int getCount() {
        return KhoaHoc.size();
    }

    @Override
    public Object getItem(int position) {
        return KhoaHoc.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.item_spinner, null);
        TextView spinner = convertView.findViewById(R.id.tv_spiner);
        MyCourse myCourse = KhoaHoc.get(position);
        spinner.setText(myCourse.getTenKH());
        return convertView;
    }
}
