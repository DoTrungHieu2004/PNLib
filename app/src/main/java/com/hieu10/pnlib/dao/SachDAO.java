package com.hieu10.pnlib.dao;

import static com.hieu10.pnlib.sqlite.DBHelper.BANG_SACH;
import static com.hieu10.pnlib.sqlite.DBHelper.COT_GIA_THUE;
import static com.hieu10.pnlib.sqlite.DBHelper.COT_MA_LOAI_SACH;
import static com.hieu10.pnlib.sqlite.DBHelper.COT_MA_SACH;
import static com.hieu10.pnlib.sqlite.DBHelper.COT_TEN_SACH;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hieu10.pnlib.models.Sach;
import com.hieu10.pnlib.sqlite.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    private SQLiteDatabase db;
    private DBHelper helper;

    public SachDAO(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    // Tạo đầu sách mới
    public long insertSach(Sach objSach) {
        ContentValues values = new ContentValues();
        values.put(COT_MA_SACH, objSach.getMaSach());
        values.put(COT_TEN_SACH, objSach.getTenSach());
        values.put(COT_GIA_THUE, objSach.getGiaThue());
        values.put(COT_MA_LOAI_SACH, objSach.getMaLoai());

        return db.insert(BANG_SACH, null, values);
    }

    // Sửa thông tin đầu sách
    public int updateSach(Sach objSach) {
        ContentValues values = new ContentValues();
        values.put(COT_TEN_SACH, objSach.getTenSach());
        values.put(COT_GIA_THUE, objSach.getGiaThue());
        values.put(COT_MA_LOAI_SACH, objSach.getMaLoai());

        return db.update(BANG_SACH, values, COT_MA_SACH + " = ?", new String[]{objSach.getMaSach()});
    }

    // Xóa đầu sách
    public int xoaSach(String idSach) {
        return db.delete(BANG_SACH, COT_MA_SACH + " = ?", new String[]{idSach});
    }

    // Lấy tất cả đầu sách
    public List<Sach> getAllSach() {
        String sql = "SELECT * FROM " + BANG_SACH;
        return getData(sql);
    }

    // Lấy đầu sách theo mã
    public Sach getID(String idSach) {
        String sql = "SELECT * FROM " + BANG_SACH + " WHERE " + COT_MA_SACH + " = ?";
        List<Sach> list = getData(sql, idSach);
        return list.get(0);
    }

    // Lấy đầu sách theo nhiều tham số
    private List<Sach> getData(String sql, String...selectionArgs) {
        List<Sach> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            Sach objSach = new Sach();
            objSach.maSach = cursor.getString(cursor.getColumnIndexOrThrow(COT_MA_SACH));
            objSach.tenSach = cursor.getString(cursor.getColumnIndexOrThrow(COT_TEN_SACH));
            objSach.giaThue = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COT_GIA_THUE)));
            objSach.maLoai = cursor.getString(cursor.getColumnIndexOrThrow(COT_MA_SACH));
            list.add(objSach);
        }
        return list;
    }
}