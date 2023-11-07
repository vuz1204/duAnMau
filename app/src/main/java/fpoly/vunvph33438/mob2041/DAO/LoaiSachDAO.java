package fpoly.vunvph33438.mob2041.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.vunvph33438.mob2041.Database.DbHelper;
import fpoly.vunvph33438.mob2041.Model.LoaiSach;

public class LoaiSachDAO {
    private static final String TABLE_NAME = "LoaiSach";
    private static final String COLUMN_MA_LOAI = "maLoai";
    private static final String COLUMN_TEN_LOAI = "tenLoai";
    DbHelper dbHelper;

    public LoaiSachDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public boolean insertData(LoaiSach obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TEN_LOAI, obj.getTenLoai());
        long check = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        obj.setMaLoai((int) check);
        return check != -1;
    }

    public boolean Delete(LoaiSach obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getMaLoai())};
        long check = sqLiteDatabase.delete(TABLE_NAME, COLUMN_MA_LOAI + "=?", dk);
        return check != -1;
    }

    public boolean Update(LoaiSach obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getMaLoai())};
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TEN_LOAI, obj.getTenLoai());
        long check = sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_MA_LOAI + "=?", dk);
        return check != -1;
    }

    private ArrayList<LoaiSach> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<LoaiSach> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.moveToFirst()) {
            do {
                int maLoai = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MA_LOAI));
                String tenLoai = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN_LOAI));
                list.add(new LoaiSach(maLoai, tenLoai));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<LoaiSach> selectAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return getAll(sql);
    }

    public LoaiSach selectID(String id) {
        String sql = "SELECT * FROM LoaiSach WHERE id = ?";
        ArrayList<LoaiSach> list = getAll(sql, id);
        return list.get(0);
    }
}
