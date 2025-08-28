package com.hieu10.pnlib.dao;

import static com.hieu10.pnlib.sqlite.DBHelper.BANG_PHIEU_MUON;
import static com.hieu10.pnlib.sqlite.DBHelper.COT_MA_SACH;
import static com.hieu10.pnlib.sqlite.DBHelper.COT_NGAY;
import static com.hieu10.pnlib.sqlite.DBHelper.COT_TIEN_THUE;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hieu10.pnlib.models.Sach;
import com.hieu10.pnlib.models.Top10;
import com.hieu10.pnlib.sqlite.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {
    private SQLiteDatabase db;
    private Context context;
    private DBHelper helper;

    public ThongKeDAO(Context context) {
        this.context = context;
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    // Thống kê top 10 sách
    public List<Top10> getTop10() {
        String sqlTop = "SELECT " + COT_MA_SACH + ", COUNT(" + COT_MA_SACH + ") AS so_luong FROM " + BANG_PHIEU_MUON + " GROUP BY " + COT_MA_SACH + " ORDER BY so_luong DESC LIMIT 10";
        List<Top10> list = new ArrayList<>();
        SachDAO sachDAO = new SachDAO(context);
        Cursor cursor = db.rawQuery(sqlTop, null);
        while (cursor.moveToNext()) {
            Top10 top10 = new Top10();
            Sach sach = sachDAO.getID(cursor.getString(cursor.getColumnIndexOrThrow(COT_MA_SACH)));
            top10.tenSach = sach.tenSach;
            top10.soLuong = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("so_luong")));
            list.add(top10);
        }
        return list;
    }

    // Thống kê doanh thu
    public int getDoanhThu(String tuNgay, String denNgay) {
        String sqlDoanhThu = "SELECT SUM(" + COT_TIEN_THUE + ") AS doanh_thu FROM " + BANG_PHIEU_MUON + " WHERE " + COT_NGAY + " BETWEEN ? AND ?";
        List<Integer> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sqlDoanhThu, new String[]{tuNgay, denNgay});

        while (cursor.moveToNext()) {
            try {
                list.add(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("doanh_thu"))));
            } catch (Exception e) {
                list.add(0);
            }
        }
        return list.get(0);
    }
}