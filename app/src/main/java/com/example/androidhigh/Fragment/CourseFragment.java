package com.example.androidhigh.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.androidhigh.Adapter.TabAdapter;
import com.example.androidhigh.R;
import com.example.androidhigh.TabFragment.TabCourse;
import com.example.androidhigh.TabFragment.TabStudents;
import com.google.android.material.tabs.TabLayout;


public class CourseFragment extends Fragment {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course,container,false);
        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tabs);
        adapter = new TabAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new TabCourse(), "Course");
        adapter.addFragment(new TabStudents(), "Students");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.getTabAt(0).setIcon(R.drawable.tab_khoahoc);
//        tabLayout.getTabAt(1).setIcon(R.drawable.tab_sinhvien);
        return view;

    }
}
