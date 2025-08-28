package com.hieu10.pnlib.dao;

import static com.hieu10.pnlib.sqlite.DBHelper.BANG_LOAI_SACH;
import static com.hieu10.pnlib.sqlite.DBHelper.COT_MA_LOAI_SACH;
import static com.hieu10.pnlib.sqlite.DBHelper.COT_TEN_LOAI_SACH;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hieu10.pnlib.models.LoaiSach;
import com.hieu10.pnlib.sqlite.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDAO {
    private SQLiteDatabase db;
    private DBHelper helper;

    public LoaiSachDAO(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    // Tạo loại sách mới
    public long insertLoaiSach(LoaiSach objLoai) {
        ContentValues values = new ContentValues();
        values.put(COT_MA_LOAI_SACH, objLoai.getMaLoai());
        values.put(COT_TEN_LOAI_SACH, objLoai.getTenLoai());

        return db.insert(BANG_LOAI_SACH, null, values);
    }

    // Cập nhật thông tin loại sách
    public int updateLoaiSach(LoaiSach objLoai) {
        ContentValues values = new ContentValues();
        values.put(COT_TEN_LOAI_SACH, objLoai.getTenLoai());

        return db.update(BANG_LOAI_SACH, values, COT_MA_LOAI_SACH + " = ?", new String[]{String.valueOf(objLoai.getMaLoai())});
    }

    // Xóa loại sách
    public int xoaLoaiSach(String idLoai) {
        return db.delete(BANG_LOAI_SACH, COT_MA_LOAI_SACH + " = ?", new String[]{idLoai});
    }

    // Lấy tất cả loại sách
    public List<LoaiSach> getAllLoaiSach() {
        String sql = "SELECT * FROM " + BANG_LOAI_SACH;
        return getData(sql);
    }

    // Lấy loại sách theo mã
    public LoaiSach getID(String idLoai) {
        String sql = "SELECT * FROM " + BANG_LOAI_SACH + " WHERE " + COT_MA_LOAI_SACH + " = ?";
        List<LoaiSach> list = getData(sql, idLoai);
        return list.get(0);
    }

    // Lấy loại sách theo nhiều them số
    private List<LoaiSach> getData(String sql, String...selectionArgs) {
        List<LoaiSach> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            LoaiSach objLoai = new LoaiSach();
            objLoai.maLoai = cursor.getString(cursor.getColumnIndexOrThrow(COT_MA_LOAI_SACH));
            objLoai.tenLoai = cursor.getString(cursor.getColumnIndexOrThrow(COT_TEN_LOAI_SACH));
            list.add(objLoai);
        }
        return list;
    }
}