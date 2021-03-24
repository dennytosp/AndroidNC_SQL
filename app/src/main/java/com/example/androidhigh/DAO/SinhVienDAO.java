package com.example.androidhigh.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.androidhigh.Database.DbHelper;
import com.example.androidhigh.Model.MyStudents;

import java.util.ArrayList;


public class SinhVienDAO {
    DbHelper helper;

    public SinhVienDAO(Context context) {
        helper = new DbHelper(context);
    }

    public ArrayList<MyStudents> readAll() {
        ArrayList<MyStudents> data = new ArrayList<>();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cs = db.rawQuery("SELECT * FROM SINHVIEN", null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            String masv = cs.getString(0);
            String ten = cs.getString(1);
            String nganh = cs.getString(2);
            byte[] img = cs.getBlob(3);
            String makh = cs.getString(4);
            data.add(new MyStudents(masv, ten, nganh, img, makh));
            cs.moveToNext();
        }
        cs.close();
        return data;
    }

    public boolean create(MyStudents ktc) {
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenSV", ktc.getTenSV());
        values.put("Nganh", ktc.getNganhHoc());
        values.put("ImageSV",ktc.getImage());
        values.put("MaKH", ktc.getMaKH());

        long row = db.insert("SINHVIEN", null, values);
        return (row > 0);
    }

    public boolean update(String ma, String ten, String nganh, String makh, byte[] image) {
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenSV", ten);
        values.put("Nganh", nganh);
        values.put("ImageSV",image);
        values.put("MaKH", makh);
        int row = db.update("SINHVIEN", values, "MaSV=?", new String[]{ma});
        return row > 0;
    }

    public boolean delete(String ma) {
        SQLiteDatabase db = helper.getReadableDatabase();
        int row = db.delete("SINHVIEN", "MaSV=?", new String[]{ma});
        return row > 0;
    }
}
