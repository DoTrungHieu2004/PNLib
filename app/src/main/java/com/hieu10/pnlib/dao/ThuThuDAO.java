package com.hieu10.pnlib.dao;

import static com.hieu10.pnlib.sqlite.DBHelper.BANG_THU_THU;
import static com.hieu10.pnlib.sqlite.DBHelper.COT_HO_TEN_THU_THU;
import static com.hieu10.pnlib.sqlite.DBHelper.COT_MAT_KHAU;
import static com.hieu10.pnlib.sqlite.DBHelper.COT_MA_THU_THU;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hieu10.pnlib.models.ThuThu;
import com.hieu10.pnlib.sqlite.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDAO {
    private SQLiteDatabase db;
    private DBHelper helper;

    public ThuThuDAO(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    // Tạo thủ thư mới (chỉ dành cho admin)
    public long insertThuThu(ThuThu objTT) {
        ContentValues values = new ContentValues();
        values.put(COT_MA_THU_THU, objTT.getMaTT());
        values.put(COT_HO_TEN_THU_THU, objTT.getHoTen());
        values.put(COT_MAT_KHAU, objTT.getMatKhau());

        return db.insert(BANG_THU_THU, null, values);
    }

    // Sửa thông tin thủ thư (chỉ dành cho admin)
    public int updateThuThu(ThuThu objTT) {
        ContentValues values = new ContentValues();
        values.put(COT_MA_THU_THU, objTT.getMaTT());
        values.put(COT_HO_TEN_THU_THU, objTT.getHoTen());
        values.put(COT_MAT_KHAU, objTT.getMatKhau());

        return db.update(BANG_THU_THU, values, COT_MA_THU_THU + " = ?", new String[]{String.valueOf(objTT.getMaTT())});
    }

    // Xóa thủ thư (chỉ dành cho admin)
    public int deleteThuThu(String idTT) {
        return db.delete(BANG_THU_THU, COT_MA_THU_THU + " = ?", new String[]{idTT});
    }

    // Lấy tất cả thủ thư
    public List<ThuThu> getAllThuThu() {
        String sql = "SELECT * FROM " + BANG_THU_THU;
        return getData(sql);
    }

    // Lấy thủ thư theo mã
    public ThuThu getID(String idTT) {
        String sql = "SELECT * FROM " + BANG_THU_THU + " WHERE " + COT_MA_THU_THU + " = ?";
        List<ThuThu> list = getData(sql, idTT);
        return list.get(0);
    }

    // Lấy thủ thư theo nhiều tham số
    private List<ThuThu> getData(String sql, String...selectionArgs) {
        List<ThuThu> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            ThuThu objTT = new ThuThu();
            objTT.maTT = cursor.getString(cursor.getColumnIndexOrThrow(COT_MA_THU_THU));
            objTT.hoTen = cursor.getString(cursor.getColumnIndexOrThrow(COT_HO_TEN_THU_THU));
            objTT.matKhau = cursor.getString(cursor.getColumnIndexOrThrow(COT_MAT_KHAU));
            list.add(objTT);
        }
        return list;
    }
}