package com.hieu10.pnlib.dao;

import static com.hieu10.pnlib.sqlite.DBHelper.BANG_THANH_VIEN;
import static com.hieu10.pnlib.sqlite.DBHelper.COT_HO_TEN_THANH_VIEN;
import static com.hieu10.pnlib.sqlite.DBHelper.COT_MA_THANH_VIEN;
import static com.hieu10.pnlib.sqlite.DBHelper.COT_NAM_SINH;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hieu10.pnlib.models.ThanhVien;
import com.hieu10.pnlib.sqlite.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDAO {
    private SQLiteDatabase db;
    private DBHelper helper;

    public ThanhVienDAO(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    // Tạo thành viên mới
    public long insertThanhVien(ThanhVien objTV) {
        ContentValues values = new ContentValues();
        values.put(COT_MA_THANH_VIEN, objTV.getMaTV());
        values.put(COT_HO_TEN_THANH_VIEN, objTV.getHoTen());
        values.put(COT_NAM_SINH, objTV.getNamSinh());

        return db.insert(BANG_THANH_VIEN, null, values);
    }

    // Sửa thông tin thành viên
    public int updateThanhVien(ThanhVien objTV) {
        ContentValues values = new ContentValues();
        values.put(COT_HO_TEN_THANH_VIEN, objTV.getHoTen());
        values.put(COT_NAM_SINH, objTV.getNamSinh());

        return db.update(BANG_THANH_VIEN, values, COT_MA_THANH_VIEN + " = ?", new String[]{String.valueOf(objTV.getMaTV())});
    }

    // Xóa thành viên
    public int deleteThanhVien(String idTV) {
        return db.delete(BANG_THANH_VIEN, COT_MA_THANH_VIEN + " = ?", new String[]{idTV});
    }

    // Lấy tất cả thành viên
    public List<ThanhVien> getAllThanhVien() {
        String sql = "SELECT * FROM " + BANG_THANH_VIEN;
        return getData(sql);
    }

    // Lấy thành viên theo mã
    public ThanhVien getID(String idTV) {
        String sql = "SELECT * FROM " + BANG_THANH_VIEN + " WHERE " + COT_MA_THANH_VIEN + " = ?";
        List<ThanhVien> list = getData(sql, idTV);
        return list.get(0);
    }

    // Lấy thành viên theo nhiều tham số
    private List<ThanhVien> getData(String sql, String...selectionArgs) {
        List<ThanhVien> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            ThanhVien objTV = new ThanhVien();
            objTV.maTV = cursor.getString(cursor.getColumnIndexOrThrow(COT_MA_THANH_VIEN));
            objTV.hoTen = cursor.getString(cursor.getColumnIndexOrThrow(COT_HO_TEN_THANH_VIEN));
            objTV.namSinh = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COT_NAM_SINH)));
            list.add(objTV);
        }
        return list;
    }
}