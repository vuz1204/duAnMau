package fpoly.vunvph33438.mob2041.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.vunvph33438.mob2041.Database.DbHelper;
import fpoly.vunvph33438.mob2041.Model.Sach;

public class SachDAO {
    private static final String TABLE_NAME = "Sach";
    private static final String COLUMN_MA_SACH = "maSach";
    private static final String COLUMN_TEN_SACH = "tenSach";
    private static final String COLUMN_GIA_THUE = "giaThue";
    private static final String COLUMN_MA_LOAI = "maLoai";
    DbHelper dbHelper;

    public SachDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public boolean insertData(Sach obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MA_LOAI, obj.getMaLoai());
        contentValues.put(COLUMN_TEN_SACH, obj.getTenSach());
        contentValues.put(COLUMN_GIA_THUE, obj.getGiaThue());
        long check = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        obj.setMaSach((int) check);
        return check != -1;
    }

    public boolean Delete(Sach obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getMaSach())};
        long check = sqLiteDatabase.delete(TABLE_NAME, COLUMN_MA_SACH + "=?", dk);
        return check != -1;
    }

    public boolean Update(Sach obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getMaSach())};
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MA_LOAI, obj.getMaLoai());
        contentValues.put(COLUMN_TEN_SACH, obj.getTenSach());
        contentValues.put(COLUMN_GIA_THUE, obj.getGiaThue());
        long check = sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_MA_SACH + "=?", dk);
        return check != -1;
    }

    private ArrayList<Sach> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<Sach> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int maSach = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MA_SACH));
                String tenSach = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN_SACH));
                int giaThue = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_GIA_THUE));
                int maLoai = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MA_LOAI));
                list.add(new Sach(maSach, tenSach, giaThue, maLoai));
            }
        }
        return list;
    }

    public ArrayList<Sach> selectAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return getAll(sql);
    }

    public Sach selectID(String id) {
        String sql = "SELECT * FROM Sach WHERE maSach = ?";
        ArrayList<Sach> list = getAll(sql, id);

        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
