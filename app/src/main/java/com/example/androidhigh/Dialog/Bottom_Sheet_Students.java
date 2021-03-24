package com.example.androidhigh.Dialog;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androidhigh.Adapter.Adapter_Spinner;
import com.example.androidhigh.Adapter.SinhVien_Adapter;
import com.example.androidhigh.DAO.SinhVienDAO;
import com.example.androidhigh.DAO.KhoaHocDAO;
import com.example.androidhigh.Model.MyStudents;
import com.example.androidhigh.Model.MyCourse;
import com.example.androidhigh.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static androidx.core.content.ContextCompat.checkSelfPermission;
import static com.example.androidhigh.TabFragment.TabStudents.rv_sinhvien;


public class Bottom_Sheet_Students extends BottomSheetDialogFragment {
    EditText edt_tensv, edt_nganhhoc;
    public static Spinner sp_vitri;
    Button btnInsert;
    CircleImageView imghinhshow;
    SinhVienDAO sinhVienDAO;
    KhoaHocDAO dao_khoahoc;
    ArrayList<MyStudents> ds_sv;
    SinhVien_Adapter adapter;
    ArrayList<MyCourse> khoahoc;
    final int REQUEST_CODE_GALLERY = 999;
    String show;

    public Bottom_Sheet_Students() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_students, container, false);
        edt_tensv = view.findViewById(R.id.edt_tensv);
        edt_nganhhoc = view.findViewById(R.id.edt_nganhhoc);
        sp_vitri = (Spinner) view.findViewById(R.id.sp_vitri);

        imghinhshow = view.findViewById(R.id.imgStudent);
        btnInsert = view.findViewById(R.id.btnInsert_students);
        sinhVienDAO = new SinhVienDAO(getContext());
        dao_khoahoc = new KhoaHocDAO(getContext());
        khoahoc = dao_khoahoc.readAll();

        imghinhshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission((getActivity()), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                    ) {
                        String[] premission = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(premission, REQUEST_CODE_GALLERY);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, REQUEST_CODE_GALLERY);
                    }
                } else {

                }
            }
        });

        final ArrayAdapter<MyCourse> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, khoahoc);
        sp_vitri.setAdapter(arrayAdapter);
        sp_vitri.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                show = khoahoc.get(sp_vitri.getSelectedItemPosition()).getTenKH();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edt_tensv.getText().toString();
                String nganh = edt_nganhhoc.getText().toString();

                int index = sp_vitri.getSelectedItemPosition();
                String makh = show;
                ds_sv = new ArrayList<>();
                sinhVienDAO = new SinhVienDAO(getContext());
                MyStudents sach = new MyStudents(null, ten, nganh, imageViewToByte(imghinhshow), makh);
                if (sinhVienDAO.create(sach)) {
                    Toast.makeText(getContext(), "Thêm dữ liệu thành công!",
                            Toast.LENGTH_SHORT).show();
                    update();
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Không thêm được dữ liệu",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });


        return view;
    }

    public byte[] imageViewToByte(ImageView image) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, stream);
        byte imageInByte[] = stream.toByteArray();
        return imageInByte;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getActivity(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imghinhshow.setImageBitmap(bitmap);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void update() {
        ds_sv = sinhVienDAO.readAll();
        adapter = new SinhVien_Adapter(ds_sv, getContext());
        rv_sinhvien.setAdapter(adapter);
    }
}