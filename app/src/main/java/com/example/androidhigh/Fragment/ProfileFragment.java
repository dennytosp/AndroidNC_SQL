package com.example.androidhigh.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.androidhigh.DarkMode.DarkModePrefManager;
import com.example.androidhigh.MainActivity;
import com.example.androidhigh.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {
    private Switch darkModeSwitch;
    CircleImageView profileCircleImageView;
    TextView usernameTextView, email, logout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profileCircleImageView = view.findViewById(R.id.profileCircleImageView);
        usernameTextView = view.findViewById(R.id.usernameTextView);
        logout = view.findViewById(R.id.logout);
        darkModeSwitch = view.findViewById(R.id.darkModeSwitch);
        if (new DarkModePrefManager(getActivity()).isNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }
        setDarkModeSwitch();

        email = view.findViewById(R.id.email);
        email.setText(MainActivity.mail);
        usernameTextView.setText(MainActivity.tenface);
        Glide.with(getActivity()).load(MainActivity.anh).into(profileCircleImageView);



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent endMain = new Intent(Intent.ACTION_MAIN);
                endMain.addCategory(Intent.CATEGORY_HOME);
                startActivity(endMain);
                getActivity().finish();
                Toast.makeText(getActivity(), "See you later", Toast.LENGTH_SHORT).show();

            }
        });

        return view;

    }

    private void setDarkModeSwitch() {

        darkModeSwitch.setChecked(new DarkModePrefManager(getActivity()).isNightMode());
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DarkModePrefManager darkModePrefManager = new DarkModePrefManager(getActivity());
                darkModePrefManager.setDarkMode(!darkModePrefManager.isNightMode());
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                getActivity().recreate();
            }

        });

    }
}