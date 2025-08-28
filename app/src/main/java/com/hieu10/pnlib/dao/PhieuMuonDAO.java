package com.hieu10.pnlib.dao;

import static com.hieu10.pnlib.sqlite.DBHelper.BANG_PHIEU_MUON;
import static com.hieu10.pnlib.sqlite.DBHelper.COT_MA_PHIEU_MUON;
import static com.hieu10.pnlib.sqlite.DBHelper.COT_MA_SACH;
import static com.hieu10.pnlib.sqlite.DBHelper.COT_MA_THANH_VIEN;
import static com.hieu10.pnlib.sqlite.DBHelper.COT_MA_THU_THU;
import static com.hieu10.pnlib.sqlite.DBHelper.COT_NGAY;
import static com.hieu10.pnlib.sqlite.DBHelper.COT_TIEN_THUE;
import static com.hieu10.pnlib.sqlite.DBHelper.COT_TRA_SACH;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hieu10.pnlib.models.PhieuMuon;
import com.hieu10.pnlib.sqlite.DBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDAO {
    private SQLiteDatabase db;
    private DBHelper helper;
    SimpleDateFormat sdf;

    public PhieuMuonDAO(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    // Tạo phiếu mượn mới
    public long insertPhieuMuon(PhieuMuon objPM) {
        ContentValues values = new ContentValues();
        values.put(COT_MA_PHIEU_MUON, objPM.getMaPM());
        values.put(COT_MA_THU_THU, objPM.getMaTT());
        values.put(COT_MA_THANH_VIEN, objPM.getMaTV());
        values.put(COT_MA_SACH, objPM.getMaSach());
        values.put(COT_TIEN_THUE, objPM.getTienThue());
        values.put(COT_NGAY, String.valueOf(objPM.getNgay()));
        values.put(COT_TRA_SACH, objPM.getTraSach());

        return db.insert(BANG_PHIEU_MUON, null, values);
    }

    // Sửa thông tin phiếu mượn
    public int updatePhieuMuon(PhieuMuon objPM) {
        ContentValues values = new ContentValues();
        values.put(COT_MA_THU_THU, objPM.getMaTT());
        values.put(COT_MA_THANH_VIEN, objPM.getMaTV());
        values.put(COT_MA_SACH, objPM.getMaSach());
        values.put(COT_TIEN_THUE, objPM.getTienThue());
        values.put(COT_NGAY, String.valueOf(objPM.getNgay()));
        values.put(COT_TRA_SACH, objPM.getTraSach());

        return db.update(BANG_PHIEU_MUON, values, COT_MA_PHIEU_MUON + " = ?", new String[]{String.valueOf(objPM.getMaPM())});
    }

    // Xóa phiếu mượn
    public int deletePhieuMuon(String idPM) {
        return db.delete(BANG_PHIEU_MUON, COT_MA_PHIEU_MUON + " = ?", new String[]{idPM});
    }

    // Lấy danh sách phiếu mượn
    public List<PhieuMuon> getAllPhieuMuon() {
        String sql = "SELECT * FROM " + BANG_PHIEU_MUON;
        return getData(sql);
    }

    // Lấy phiếu mượn theo mã
    public PhieuMuon getID(String idPM) {
        String sql = "SELECT * FROM " + BANG_PHIEU_MUON + " WHERE " + COT_MA_PHIEU_MUON;
        List<PhieuMuon> list = getData(sql, idPM);
        return list.get(0);
    }

    // Lấy phiếu mượn theo nhiều tham số
    public List<PhieuMuon> getData(String sql, String...selectionArgs) {
        List<PhieuMuon> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            PhieuMuon objPM = new PhieuMuon();
            objPM.maPM = cursor.getString(cursor.getColumnIndexOrThrow(COT_MA_PHIEU_MUON));
            objPM.maTT = cursor.getString(cursor.getColumnIndexOrThrow(COT_MA_THU_THU));
            objPM.maTV = cursor.getString(cursor.getColumnIndexOrThrow(COT_MA_THANH_VIEN));
            objPM.maSach = cursor.getString(cursor.getColumnIndexOrThrow(COT_MA_SACH));
            objPM.tienThue = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COT_TIEN_THUE)));
            objPM.traSach = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COT_TRA_SACH)));

            try {
                objPM.setNgay(sdf.parse(cursor.getString(cursor.getColumnIndexOrThrow(COT_NGAY))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            list.add(objPM);
        }
        return list;
    }
}