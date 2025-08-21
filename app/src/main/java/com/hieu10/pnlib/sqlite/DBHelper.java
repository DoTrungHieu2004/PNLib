package com.hieu10.pnlib.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "PNLib.db";
    private static final int DB_VERSION = 1;

    // Thực thể Thành viên
    public static final String BANG_THANH_VIEN = "tb_thanh_vien";
    public static final String COT_MA_THANH_VIEN = "ma_tv";
    public static final String COT_HO_TEN_THANH_VIEN = "ho_ten";
    public static final String COT_NAM_SINH = "nam_sinh";

    // Thực thể Thủ thư
    public static final String BANG_THU_THU = "tb_thu_thu";
    public static final String COT_MA_THU_THU = "ma_tt";
    public static final String COT_HO_TEN_THU_THU = "ho_ten";
    public static final String COT_MAT_KHAU = "mat_khau";

    // Thực thể Loại sách
    public static final String BANG_LOAI_SACH = "tb_loai_sach";
    public static final String COT_MA_LOAI_SACH = "ma_loai";
    public static final String COT_TEN_LOAI_SACH = "ten_loai";

    // Thực thể Sách
    public static final String BANG_SACH = "tb_sach";
    public static final String COT_MA_SACH = "ma_sach";
    public static final String COT_TEN_SACH = "ten_sach";
    public static final String COT_GIA_THUE = "gia_thue";

    // Thực thể Phiếu mượn
    public static final String BANG_PHIEU_MUON = "tb_phieu_muon";
    public static final String COT_MA_PHIEU_MUON = "ma_pm";
    public static final String COT_TIEN_THUE = "tien_thue";
    public static final String COT_NGAY = "ngay";
    public static final String COT_TRA_SACH = "tra_sach";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng thành viên
        String create_table_thanh_vien = "CREATE TABLE IF NOT EXISTS " + BANG_THANH_VIEN + "("
                + COT_MA_THANH_VIEN + " TEXT PRIMARY KEY,"
                + COT_HO_TEN_THANH_VIEN + " TEXT NOT NULL,"
                + COT_NAM_SINH + " INTEGER NOT NULL"
                + ");";
        db.execSQL(create_table_thanh_vien);

        // Tạo bảng thủ thư
        String create_table_thu_thu = "CREATE TABLE IF NOT EXISTS " + BANG_THU_THU + "("
                + COT_MA_THU_THU + " TEXT PRIMARY KEY,"
                + COT_HO_TEN_THU_THU + " TEXT NOT NULL,"
                + COT_MAT_KHAU + " TEXT NOT NULL"
                + ");";
        db.execSQL(create_table_thu_thu);

        // Tạo bảng loại sách
        String create_table_loai_sach = "CREATE TABLE IF NOT EXISTS " + BANG_LOAI_SACH + "("
                + COT_MA_LOAI_SACH + " TEXT PRIMARY KEY,"
                + COT_TEN_LOAI_SACH + " TEXT NOT NULL"
                + ");";
        db.execSQL(create_table_loai_sach);

        // Tạo bảng sách
        String create_table_sach = "CREATE TABLE IF NOT EXISTS " + BANG_SACH + "("
                + COT_MA_SACH + " TEXT PRIMARY KEY,"
                + COT_TEN_SACH + " TEXT NOT NULL,"
                + COT_GIA_THUE + " INTEGER NOT NULL,"
                + COT_MA_LOAI_SACH + " TEXT REFERENCES " + BANG_LOAI_SACH + "(" + COT_MA_LOAI_SACH + ")"
                + ");";
        db.execSQL(create_table_sach);

        // Tạo bảng phiếu mượn
        String create_table_phieu_muon = "CREATE TABLE IF NOT EXISTS " + BANG_PHIEU_MUON + "("
                + COT_MA_PHIEU_MUON + " TEXT PRIMARY KEY,"
                + COT_MA_THU_THU + " TEXT REFERENCES " + BANG_THU_THU + "(" + COT_MA_THU_THU + "),"
                + COT_MA_THANH_VIEN + " TEXT REFERENCES " + BANG_THANH_VIEN + "(" + COT_MA_THANH_VIEN + "),"
                + COT_MA_SACH + " TEXT REFERENCES " + BANG_SACH + "(" + COT_MA_SACH + "),"
                + COT_TIEN_THUE + " DATE NOT NULL,"
                + COT_NGAY + " DATE NOT NULL,"
                + COT_TRA_SACH + " INTEGER NOT NULL"
                + ");";
        db.execSQL(create_table_phieu_muon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + BANG_THANH_VIEN);
        db.execSQL("DROP TABLE IF EXISTS " + BANG_THU_THU);
        db.execSQL("DROP TABLE IF EXISTS " + BANG_LOAI_SACH);
        db.execSQL("DROP TABLE IF EXISTS " + BANG_SACH);
        db.execSQL("DROP TABLE IF EXISTS " + BANG_PHIEU_MUON);
    }
}