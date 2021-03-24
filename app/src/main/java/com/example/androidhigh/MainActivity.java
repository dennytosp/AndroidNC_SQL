package com.example.androidhigh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.androidhigh.Fragment.CourseFragment;
import com.example.androidhigh.Fragment.HomeFragment;
import com.example.androidhigh.Fragment.MapsFragment;
import com.example.androidhigh.Fragment.NewsFragment;
import com.example.androidhigh.Fragment.ProfileFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {
    ChipNavigationBar chipNavigationBar;
    FragmentManager fragmentManager;
    private static final String TAG = MainActivity.class.getSimpleName();
    public static String tenface,mail,anh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chipNavigationBar = findViewById(R.id.chipNavigation);
        isPermissionGranted();
        Bundle i = getIntent().getExtras();
        if (i!=null){
            tenface =i.getString("name");
            mail = i.getString("email");
            anh = i.getString("profile_pic");
        }

        if (savedInstanceState == null) {
            chipNavigationBar.setItemSelected(R.id.three, true);
            fragmentManager = getSupportFragmentManager();
            HomeFragment homeFragment = new HomeFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
        }
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {

            @Override
            public void onItemSelected(int id) {
                Fragment fragment = null;
                switch (id) {
                    case R.id.one:
                        fragment = new CourseFragment();
                        break;
                    case R.id.two:
                        fragment = new NewsFragment();
                        break;
                    case R.id.three:
                        fragment = new HomeFragment();
                        break;
                    case R.id.four:
                        fragment = new MapsFragment();
                        break;
                    case R.id.five:
                        fragment = new ProfileFragment();
                        break;
                }
                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
                } else {
                    Log.e(TAG, "Error in creating fragment");
                }
            }
        });

    }
    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {

                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {


        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
            //do your specific task after read phone state granted
        } else {
            Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
        return;
    }
}