package com.example.androidhigh.Model;

public class MyCourse {
    public String maKH;
    public String tenKH;
    public double tienKH;
    public String thoigianKH;
    byte[] _image;

    public MyCourse() {
    }

    public MyCourse(String maKH, String tenKH, double tienKH, String thoigianKH, byte[] image) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.tienKH = tienKH;
        this.thoigianKH = thoigianKH;
        this._image = image;

    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public double getTienKH() {
        return tienKH;
    }

    public void setTienKH(double tienKH) {
        this.tienKH = tienKH;
    }

    public String getThoigianKH() {
        return thoigianKH;
    }

    public void setThoigianKH(String thoigianKH) {
        this.thoigianKH = thoigianKH;
    }

    public byte[] getImage() {
        return _image;
    }

    public void setImage(byte[] image) {
        this._image = image;
    }

    @Override
    public String toString() {
        return getTenKH();
    }
}
