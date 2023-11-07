package fpoly.vunvph33438.mob2041.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "LibMana.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng thủ thư
        String createTableThuThu = "CREATE TABLE ThuThu (" +
                "maTT TEXT PRIMARY KEY, " +
                "hoTen TEXT NOT NULL, " +
                "matKhau TEXT NOT NULL)";
        db.execSQL(createTableThuThu);

        String insertDefaultThuThu = "INSERT INTO ThuThu (maTT, hoTen, matKhau) VALUES ('admin', 'Nguyen Van Vu', 'admin')";
        db.execSQL(insertDefaultThuThu);

        // Tạo bảng thành viên
        String createTableThanhVien = "CREATE TABLE ThanhVien (" +
                "maTV INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "hoTen TEXT NOT NULL, " +
                "namSinh TEXT NOT NULL)";
        db.execSQL(createTableThanhVien);

        // Tạo bảng loại sách
        String createTableLoaiSach = "CREATE TABLE LoaiSach (" +
                "maLoai INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenLoai TEXT NOT NULL)";
        db.execSQL(createTableLoaiSach);

        // Tạo bảng sách
        String createTableSach = "CREATE TABLE Sach (" +
                "maSach INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenSach TEXT NOT NULL, " +
                "giaThue INTEGER NOT NULL, " +
                "maLoai INTEGER REFERENCES LoaiSach(maLoai))";
        db.execSQL(createTableSach);

        // Tạo bảng phiếu mượn
        String createTablePhieuMuon = "CREATE TABLE PhieuMuon (" +
                "maPM INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maTT TEXT REFERENCES ThuThu(maTT), " +
                "maTV INTEGER REFERENCES ThanhVien(maTV), " +
                "maSach INTEGER REFERENCES Sach(maSach), " +
                "tienThue INTEGER NOT NULL, " +
                "ngay DATE NOT NULL, " +
                "traSach INTEGER NOT NULL)";
        db.execSQL(createTablePhieuMuon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS ThuThu");
            db.execSQL("DROP TABLE IF EXISTS ThanhVien");
            db.execSQL("DROP TABLE IF EXISTS LoaiSach");
            db.execSQL("DROP TABLE IF EXISTS Sach");
            db.execSQL("DROP TABLE IF EXISTS PhieuMuon");
            onCreate(db);
        }
    }
}
