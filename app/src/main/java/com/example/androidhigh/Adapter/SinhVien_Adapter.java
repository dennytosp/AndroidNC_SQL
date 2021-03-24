package com.example.androidhigh.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhigh.DAO.SinhVienDAO;
import com.example.androidhigh.DAO.KhoaHocDAO;
import com.example.androidhigh.Model.MyStudents;
import com.example.androidhigh.Model.MyCourse;
import com.example.androidhigh.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static androidx.core.content.ContextCompat.checkSelfPermission;
import static com.example.androidhigh.TabFragment.TabStudents.rv_sinhvien;


public class SinhVien_Adapter extends RecyclerView.Adapter<SinhVien_Adapter.MyViewHolder> {
    private ArrayList<MyStudents> data;
    private Context context;
    SinhVienDAO dao;
    KhoaHocDAO dao_kh;
    private ArrayList<MyCourse> data_khoahoc;
    SinhVien_Adapter adapter;
    EditText tensv, nganh;
    String show;
    Spinner maKH;
    Button btnUpdateImage;
    CircleImageView imghinhshow;
    final int REQUEST_CODE_GALLERY = 999;

    public SinhVien_Adapter(ArrayList<MyStudents> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTenSV, tvMaKhoaHoc, tvNganhKH;
        private ImageView iv_more;
        ConstraintLayout itemClick;
        ImageView item_students_img;


        public MyViewHolder(View view) {
            super(view);
            itemClick = (ConstraintLayout) view.findViewById(R.id.itemClick);
            tvTenSV = view.findViewById(R.id.tvTenSV);
            tvNganhKH = view.findViewById(R.id.tvNganhKH);
            tvMaKhoaHoc = view.findViewById(R.id.tvMaKhoaHoc);
            iv_more = view.findViewById(R.id.iv_more);
            item_students_img = view.findViewById(R.id.item_students_img);

        }
    }

    @NonNull
    @Override
    public SinhVien_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_students, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tvTenSV.setText(data.get(position).getTenSV());
        holder.tvNganhKH.setText(data.get(position).getNganhHoc());
        holder.tvMaKhoaHoc.setText(data.get(position).getMaKH());
        byte[] image = data.get(position).getImage();
        final Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.item_students_img.setImageBitmap(bitmap);

//        if (position % 6 == 0) {
//            holder.item_students_img.setImageResource(R.mipmap.phong);
//        } else if (position % 6 == 1) {
//            holder.item_students_img.setImageResource(R.mipmap.huy);
//        } else if (position % 6 == 2) {
//            holder.item_students_img.setImageResource(R.mipmap.danh);
//        } else if (position % 6 == 3) {
//            holder.item_students_img.setImageResource(R.mipmap.vinh);
//        } else if (position % 6 == 4) {
//            holder.item_students_img.setImageResource(R.mipmap.tam);
//        } else {
//            holder.item_students_img.setImageResource(R.mipmap.nhi);
//        }

        dao_kh = new KhoaHocDAO(context);
//        holder.itemClick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Dialog myDialog = new Dialog(context);
//                myDialog.setContentView(R.layout.alert_info_khoan);
//                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                TextView dialog_tensach = (TextView) myDialog.findViewById(R.id.dialog_tensach);
//                TextView dialog_tacgia = (TextView) myDialog.findViewById(R.id.dialog_tacgia);
//                TextView dialog_giabia = (TextView) myDialog.findViewById(R.id.dialog_giabia);
//                TextView dialog_makhoahoc = (TextView) myDialog.findViewById(R.id.dialog_makhoahoc);
//                TextView dialog_ngayxuatban = (TextView) myDialog.findViewById(R.id.dialog_ngayxuatban);
//                TextView dialog_soluong = (TextView) myDialog.findViewById(R.id.dialog_soluong);
//                dialog_tensach.setText(data.get(position).getTenSach());
//                dialog_tacgia.setText(data.get(position).getTacGia());
//                dialog_giabia.setText(decimalFormat.format(data.get(position).getGiaBia()) + " $");
//                dialog_giabia.addTextChangedListener(onTextChangedListener());
//                int index = Integer.parseInt((data.get(position).getmakhoahoc()));
//                dialog_makhoahoc.setText(data_khoahoc.get(index - 1).getTenLoai());
//                dialog_ngayxuatban.setText(data.get(position).getNXB());
//                dialog_soluong.setText(data.get(position).getSoLuong() + " quyển");
//
//                myDialog.show();
//
//            }
//        });
        holder.iv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.one:
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                                View dialogView = inflater.inflate(R.layout.dialog_students, null);
                                dialogBuilder.setView(dialogView);

                                tensv = dialogView.findViewById(R.id.edt_tensv);
                                nganh = dialogView.findViewById(R.id.edt_nganhhoc);
                                maKH = (Spinner) dialogView.findViewById(R.id.sp_vitri);
                                imghinhshow = dialogView.findViewById(R.id.imgStudentAdapter);
                                imghinhshow.setImageBitmap(bitmap);

                                Button btn_update = dialogView.findViewById(R.id.btnEdit_students);
                                tensv.setText(data.get(position).getTenSV());
                                nganh.setText(data.get(position).getNganhHoc());


                                dao_kh = new KhoaHocDAO(context);
                                data_khoahoc = dao_kh.readAll();
//                                adapter_spinner = new Adapter_Spinner(context, data_khoahoc);
//                                maKH.setAdapter(adapter_spinner);
//                                int vitri = Integer.parseInt(((data.get(position).getMaKH())));
//                                maKH.setSelection(vitri - 1);
//
                                KhoaHocDAO khoaHocDAO = new KhoaHocDAO(context);
                                data_khoahoc = khoaHocDAO.readAll();
                                ArrayAdapter<MyCourse> arrayAdapter = new ArrayAdapter<MyCourse>(context, android.R.layout.simple_list_item_1, data_khoahoc);
                                maKH.setAdapter(arrayAdapter);
                                maKH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        show = data_khoahoc.get(maKH.getSelectedItemPosition()).getTenKH();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                                for (int i = 0; i < data_khoahoc.size(); i++) {
                                    if (data_khoahoc.get(i).getTenKH().matches(data.get(position).getMaKH())) {
                                        maKH.setSelection(i);
                                    }
                                }
                                final AlertDialog alertDialog = dialogBuilder.create();
                                alertDialog.show();

                                btn_update.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String ma = data.get(position).getMaSV();
                                        String tenSV = tensv.getText().toString();
                                        String tenNganh = nganh.getText().toString();

                                        int index = maKH.getSelectedItemPosition();
                                        String makhoahoc = show;

                                        dao = new SinhVienDAO(context);
                                        dao.update(ma, tenSV, tenNganh, makhoahoc, imageViewToByte(imghinhshow));

                                        Toast.makeText(context, "Edit successfully", Toast.LENGTH_SHORT).show();

                                        updateDelete();
                                        alertDialog.dismiss();
                                    }
                                });

                                break;
                            case R.id.two:
//                                Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Students Delete");
                                builder.setMessage("Do you want to delete this data ?");
                                builder.setIcon(R.drawable.beane);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dao = new SinhVienDAO(context);
                                        dao.delete(data.get(position).getMaSV());
                                        updateDelete();
                                        Toast.makeText(context, "Data deleted successfully", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                builder.show();
                                break;
                            case R.id.three:
                                Dialog myDialog = new Dialog(context);
                                myDialog.setContentView(R.layout.alert_info);
                                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                TextView dialog_sinhvien = (TextView) myDialog.findViewById(R.id.dialog_sinhvien);
                                TextView dialog_nganhhoc = (TextView) myDialog.findViewById(R.id.dialog_nganhhoc);
                                TextView dialog_khoahoc = (TextView) myDialog.findViewById(R.id.dialog_khoahoc);
                                CircleImageView dialog_imgAlert = myDialog.findViewById(R.id.imgAlert);
                                dialog_sinhvien.setText(data.get(position).getTenSV());
                                dialog_nganhhoc.setText(data.get(position).getNganhHoc());
                                dialog_khoahoc.setText(data.get(position).getMaKH());
                                dialog_imgAlert.setImageBitmap(bitmap);

                                myDialog.show();
                        }
                        return true;
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateDelete() {
        data = dao.readAll();
        adapter = new SinhVien_Adapter(data, context);
        rv_sinhvien.setAdapter(adapter);
    }

    public byte[] imageViewToByte(ImageView image) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, stream);
        byte imageInByte[] = stream.toByteArray();
        return imageInByte;
    }


}