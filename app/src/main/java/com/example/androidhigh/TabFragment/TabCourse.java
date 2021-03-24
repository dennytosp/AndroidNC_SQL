package com.example.androidhigh.TabFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhigh.Adapter.KhoaHoc_Adapter;
import com.example.androidhigh.DAO.KhoaHocDAO;
import com.example.androidhigh.Dialog.Bottom_Sheet_Course;
import com.example.androidhigh.Model.MyCourse;
import com.example.androidhigh.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class TabCourse extends Fragment {

    FloatingActionButton floating_kh;
    public static KhoaHoc_Adapter adapter;
    KhoaHocDAO dao;
    public static RecyclerView rv_khoahoc;
    ArrayList<MyCourse> ds_theloai;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_course, container, false);
        floating_kh = view.findViewById(R.id.floating_kh);
        rv_khoahoc = view.findViewById(R.id.rv_khoahoc);

        rv_khoahoc.setLayoutManager(new LinearLayoutManager(getContext()));
        ds_theloai = new ArrayList<>();
        dao = new KhoaHocDAO(getContext());


        floating_kh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bottom_Sheet_Course bottomSheet = new Bottom_Sheet_Course();
                bottomSheet.show(getFragmentManager(), "TAG");
            }
        });
        ds_theloai = dao.readAll();
        adapter = new KhoaHoc_Adapter(ds_theloai, getContext());
        rv_khoahoc.setAdapter(adapter);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        ds_theloai.clear();
        ds_theloai.addAll(dao.readAll());
        adapter.notifyDataSetChanged();
    }
}