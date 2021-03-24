package com.example.androidhigh.Model;

public class MyStudents {
    public String maSV;
    public String tenSV;
    public String nganhHoc;
    byte[] _image;
    public String maKH;

    public MyStudents() {
    }

    public MyStudents(String maSV, String tenSV, String nganhHoc, byte[] image, String maKH) {
        this.maSV = maSV;
        this.tenSV = tenSV;
        this.nganhHoc = nganhHoc;
        this._image = image;
        this.maKH = maKH;

    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getTenSV() {
        return tenSV;
    }

    public void setTenSV(String tenSV) {
        this.tenSV = tenSV;
    }

    public String getNganhHoc() {
        return nganhHoc;
    }

    public void setNganhHoc(String nganhHoc) {
        this.nganhHoc = nganhHoc;
    }

    public byte[] getImage() {
        return _image;
    }

    public void setImage(byte[] image) {
        this._image = image;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }


}
