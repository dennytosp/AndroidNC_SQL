package com.example.androidhigh.TabFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhigh.Adapter.SinhVien_Adapter;
import com.example.androidhigh.DAO.SinhVienDAO;
import com.example.androidhigh.Dialog.Bottom_Sheet_Students;
import com.example.androidhigh.Model.MyStudents;
import com.example.androidhigh.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class TabStudents extends Fragment {
    FloatingActionButton floating;
    SinhVien_Adapter adapter;
    SinhVienDAO dao;
    ArrayList<MyStudents> ds_sv;
    public static RecyclerView rv_sinhvien;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_students, container, false);
        floating = view.findViewById(R.id.floating_sv);
        rv_sinhvien = view.findViewById(R.id.rv_sinhvien);
        rv_sinhvien.setLayoutManager(new LinearLayoutManager(getContext()));

        ds_sv = new ArrayList<>();
        dao = new SinhVienDAO(getContext());
        ds_sv = dao.readAll();
        adapter = new SinhVien_Adapter(ds_sv, getActivity());
        rv_sinhvien.setAdapter(adapter);

        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bottom_Sheet_Students bottomSheet = new Bottom_Sheet_Students();
                bottomSheet.show(getFragmentManager(), "TAG");
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ds_sv.clear();
        ds_sv.addAll(dao.readAll());
        adapter.notifyDataSetChanged();
    }

}
