package com.hieu10.pnlib.models;

public class Top10 {
    public String tenSach;
    public int soLuong;

    public Top10() {
    }

    public Top10(String tenSach, int soLuong) {
        this.tenSach = tenSach;
        this.soLuong = soLuong;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}