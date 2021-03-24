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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androidhigh.Adapter.KhoaHoc_Adapter;
import com.example.androidhigh.DAO.KhoaHocDAO;
import com.example.androidhigh.Model.MyCourse;
import com.example.androidhigh.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static androidx.core.content.ContextCompat.checkSelfPermission;
import static com.example.androidhigh.TabFragment.TabCourse.rv_khoahoc;


public class Bottom_Sheet_Course extends BottomSheetDialogFragment {
    EditText edt_tenkhoahoc, edt_tien;
    Spinner spinner;
    Button btnInsert;
    KhoaHocDAO dao;
    ArrayList<MyCourse> ds_theloai;
    KhoaHoc_Adapter adapter;
    CircleImageView imghinhshow;
    final int REQUEST_CODE_GALLERY = 999;

    public Bottom_Sheet_Course() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_course, container, false);
        edt_tenkhoahoc = view.findViewById(R.id.edt_tenkhoahoc);
        spinner = view.findViewById(R.id.sp_vitri);
        imghinhshow = view.findViewById(R.id.imgStudent);
        edt_tien = view.findViewById(R.id.edt_tien);
        btnInsert = view.findViewById(R.id.btnInsert_course);
        dao = new KhoaHocDAO(getContext());

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

        edt_tien.addTextChangedListener(onTextChangedListener());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenkh = edt_tenkhoahoc.getText().toString();
                String thoigian = spinner.getSelectedItem().toString();
                String str = edt_tien.getText().toString().trim();

                DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                format.setParseBigDecimal(true);
                BigDecimal number = null;
                try {
                    number = (BigDecimal) format.parse(str);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                double tien = Double.parseDouble(number + "");

                MyCourse pl = new MyCourse(null, tenkh, tien, thoigian, imageViewToByte(imghinhshow));
                if (dao.create(pl)) {
                    Toast.makeText(getContext(), "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
                    update();
                    dismissAllowingStateLoss();
                } else {
                    Toast.makeText(getContext(), "Không thêm được dữ liệu",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }


    public void update() {
        ds_theloai = new ArrayList<>();
        ds_theloai = dao.readAll();
        adapter = new KhoaHoc_Adapter(ds_theloai, getContext());
        rv_khoahoc.setAdapter(adapter);
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


    private TextWatcher onTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edt_tien.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    edt_tien.setText(formattedString);
                    edt_tien.setSelection(edt_tien.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                edt_tien.addTextChangedListener(this);
            }
        };

    }

}
