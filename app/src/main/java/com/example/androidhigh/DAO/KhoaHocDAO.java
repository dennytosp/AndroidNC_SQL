package com.example.androidhigh.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.androidhigh.Database.DbHelper;
import com.example.androidhigh.Model.MyCourse;

import java.util.ArrayList;

public class KhoaHocDAO {
    DbHelper helper;

    public KhoaHocDAO(Context context) {
        helper = new DbHelper(context);
    }

    public ArrayList<MyCourse> readAll() {
        ArrayList<MyCourse> data = new ArrayList<>();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cs = db.rawQuery("SELECT * FROM KHOAHOC ORDER BY ThoigianKH ASC", null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            String ma = cs.getString(0);
            String ten = cs.getString(1);
            Double tien = cs.getDouble(2);
            String thoigian = cs.getString(3);
            byte[] img = cs.getBlob(4);
            data.add(new MyCourse(ma, ten, tien, thoigian, img));
            cs.moveToNext();
        }
        cs.close();
        return data;
    }

    public String getTen(String id) {
        String ten = "";
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cs = db.rawQuery("SELECT ThoigianKH FROM KHOAHOC Where MaKH='" + id + "' ", null);
        if (cs.moveToFirst()) {
            do {
                ten = cs.getString(0);
                break;
            } while (cs.moveToNext());
        }
        return ten;
    }

    public boolean create(MyCourse pl) {
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenKH", pl.getTenKH());
        values.put("TienKH", pl.getTienKH());
        values.put("ThoigianKH", pl.getThoigianKH());
        values.put("ImageKH", pl.getImage());
        long row = db.insert("KHOAHOC", null, values);
        return (row > 0);
    }

    public boolean update(String ma, String ten, Double TienKH, String ThoigianKH, byte[] img) {
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenKH", ten);
        values.put("TienKH", TienKH);
        values.put("ThoigianKH", ThoigianKH);
        values.put("ImageKH", img);
        int row = db.update("KHOAHOC", values, "MaKH=?", new String[]{ma});
        return row > 0;
    }

    public boolean delete(String ma) {
        SQLiteDatabase db = helper.getReadableDatabase();
        int row = db.delete("KHOAHOC", "MaKH=?", new String[]{ma});
        return row > 0;
    }
}
