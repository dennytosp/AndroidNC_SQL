package com.example.androidhigh.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhigh.DAO.KhoaHocDAO;
import com.example.androidhigh.Model.MyCourse;
import com.example.androidhigh.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.example.androidhigh.TabFragment.TabCourse.rv_khoahoc;

public class KhoaHoc_Adapter extends RecyclerView.Adapter<KhoaHoc_Adapter.MyViewHolder> {
    ArrayList<MyCourse> ds_MyCourse;
    Context context;
    KhoaHocDAO dao;
    KhoaHoc_Adapter adapter;
    EditText tenKH, tienKH, thoigianKH;
    CircleImageView imghinhshow;
    final int REQUEST_CODE_GALLERY = 999;

    public KhoaHoc_Adapter(ArrayList<MyCourse> ds_MyCourse, Context context) {
        this.ds_MyCourse = ds_MyCourse;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_tenkh, tv_tienKH, tv_thoigiankh;
        private ImageView ic_more;
        ImageView item_course_img;

        public MyViewHolder(View view) {
            super(view);
            tv_tenkh = view.findViewById(R.id.tvKhoaHoc);
            tv_tienKH = view.findViewById(R.id.tvTienKH);
            tv_thoigiankh = view.findViewById(R.id.tvThoigianKH);
            ic_more = view.findViewById(R.id.iv_more);
            item_course_img = view.findViewById(R.id.item_course_img);
        }
    }

    @NonNull
    @Override
    public KhoaHoc_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tv_tenkh.setText(ds_MyCourse.get(position).getTenKH());
        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decimalFormat.applyPattern("#,###,###,###");
        holder.tv_tienKH.setText(decimalFormat.format(ds_MyCourse.get(position).getTienKH()) + " $");
        holder.tv_thoigiankh.setText(ds_MyCourse.get(position).getThoigianKH());
        byte[] image = ds_MyCourse.get(position).getImage();
        final Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.item_course_img.setImageBitmap(bitmap);

//        if (position % 6 == 0) {
//            holder.item_course_img.setImageResource(R.mipmap.z3);
//        } else if (position % 6 == 1) {
//            holder.item_course_img.setImageResource(R.mipmap.z4);
//        } else if (position % 6 == 2) {
//            holder.item_course_img.setImageResource(R.mipmap.z2);
//        } else if (position % 6 == 3) {
//            holder.item_course_img.setImageResource(R.mipmap.z5);
//        } else if (position % 6 == 4) {
//            holder.item_course_img.setImageResource(R.mipmap.z);
//        } else {
//            holder.item_course_img.setImageResource(R.mipmap.z1);
//        }


        holder.ic_more.setOnClickListener(new View.OnClickListener() {
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
                                View dialogView = inflater.inflate(R.layout.dialog_course, null);
                                dialogBuilder.setView(dialogView);
                                tenKH = (EditText) dialogView.findViewById(R.id.edt_tenkhoahoc);
                                tienKH = (EditText) dialogView.findViewById(R.id.edt_tien);
                                thoigianKH = (EditText) dialogView.findViewById(R.id.edt_thoigiankh);
                                imghinhshow = dialogView.findViewById(R.id.imgCourseAdapter);
                                imghinhshow.setImageBitmap(bitmap);

                                final Calendar calendar_date = Calendar.getInstance();
                                final int year = calendar_date.get(calendar_date.YEAR);
                                final int month = calendar_date.get(calendar_date.MONTH);
                                final int day = calendar_date.get(calendar_date.DAY_OF_MONTH);

                                thoigianKH.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        DatePickerDialog datePickerDialog = new DatePickerDialog(
                                                context, new DatePickerDialog.OnDateSetListener() {
                                            @Override
                                            public void onDateSet(DatePicker view, int i, int i1, int i2) {

                                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                                                calendar_date.set(i, i1, i2);
                                                String date = dateFormat.format(calendar_date.getTime());
                                                thoigianKH.setText(date);
                                            }
                                        }, year, month, day);
                                        datePickerDialog.show();
                                    }
                                });

                                Button btn_edit = dialogView.findViewById(R.id.btnEdit_course);
                                tenKH.setText(ds_MyCourse.get(position).getTenKH());
                                tienKH.setText(decimalFormat.format(ds_MyCourse.get(position).getTienKH()));
                                tienKH.addTextChangedListener(onTextChangedListener());
                                thoigianKH.setText(ds_MyCourse.get(position).getThoigianKH());

                                final AlertDialog alertDialog = dialogBuilder.create();
                                alertDialog.show();

                                btn_edit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String ma = String.valueOf(ds_MyCourse.get(position).getMaKH());
                                        String ten = tenKH.getText().toString();
                                        String thoigian = thoigianKH.getText().toString();
                                        String str = tienKH.getText().toString().trim();
//
                                        DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                                        format.setParseBigDecimal(true);
                                        BigDecimal number = null;
                                        try {
                                            number = (BigDecimal) format.parse(str);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        double abc = Double.parseDouble(number + "");

                                        dao = new KhoaHocDAO(context);
                                        dao.update(ma, ten, abc, thoigian, imageViewToByte(imghinhshow));
                                        Toast.makeText(context, "Edit successfully", Toast.LENGTH_SHORT).show();
                                        updateDelete();
                                        alertDialog.dismiss();
                                    }
                                });

                                break;
                            case R.id.two:
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Course Delete");
                                builder.setMessage("Do you want to delete this data ?");
                                builder.setIcon(R.drawable.beane);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dao = new KhoaHocDAO(context);
                                        dao.delete(ds_MyCourse.get(position).getMaKH());
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
                        }
                        return true;
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return ds_MyCourse.size();
    }

    public void updateDelete() {
        ds_MyCourse = dao.readAll();
        adapter = new KhoaHoc_Adapter(ds_MyCourse, context);
        rv_khoahoc.setAdapter(adapter);
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
                tienKH.removeTextChangedListener(this);

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
                    tienKH.setText(formattedString);
                    tienKH.setSelection(tienKH.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                tienKH.addTextChangedListener(this);
            }
        };

    }

    public byte[] imageViewToByte(ImageView image) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, stream);
        byte imageInByte[] = stream.toByteArray();
        return imageInByte;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
//            Uri uri = data.getData();
//
//            try {
//                InputStream inputStream = context.getContentResolver().openInputStream(uri);
//
//                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                imghinhshow.setImageBitmap(bitmap);
//
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inJustDecodeBounds = true;
//
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }

}

