package com.example.androidhigh.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, "MyCourse.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        sql = "CREATE TABLE KHOAHOC(MaKH integer primary key autoincrement," +
                "TenKH text, TienKH Double, ThoigianKH text, ImageKH BLOB)";
        db.execSQL(sql);
//        sql = "INSERT INTO KHOAHOC (TenKH, TienKH, ThoigianKH) VALUES ('Lập trình PHP nâng cao','25000 $','2018/01/01')";
//        db.execSQL(sql);
//        sql = "INSERT INTO KHOAHOC (TenKH, TienKH, ThoigianKH) VALUES ('Lập trình Android nâng cao','35000 $','2018/03/03')";
//        db.execSQL(sql);
//        sql = "INSERT INTO KHOAHOC (TenKH, TienKH, ThoigianKH) VALUES ('Làm quen với Node Javascript cơ bản','45000 $','2018/05/05')";
//        db.execSQL(sql);
//        sql = "INSERT INTO KHOAHOC (TenKH, TienKH, ThoigianKH) VALUES ('Thiết kế giao diện với Adobe Photoshop','55000 $', '2018/07/07')";
//        db.execSQL(sql);
//        sql = "INSERT INTO KHOAHOC (TenKH, TienKH, ThoigianKH) VALUES ('Chỉnh sửa màu với Lightroom','65000 $', '2018/09/09')";
//        db.execSQL(sql);
//        sql = "INSERT INTO KHOAHOC (TenKH, TienKH, ThoigianKH) VALUES ('Thiết kế video với Adobe Premiere','75000 $','2018/11/11')";
//        db.execSQL(sql);
//        sql = "INSERT INTO KHOAHOC (TenKH, TienKH, ThoigianKH) VALUES ('Hướng dẫn trở thành một Rapper','85000 $', '2019/01/01')";
//        db.execSQL(sql);

        sql = "CREATE TABLE SINHVIEN(MaSV integer primary key autoincrement," +
                "TenSV text, Nganh text, ImageSV BLOB," +
                "MaKH text references KHOAHOC(MaKH))";
        db.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS KHOAHOC");
        db.execSQL("DROP TABLE IF EXISTS SINHVIEN");
        onCreate(db);

    }
}
