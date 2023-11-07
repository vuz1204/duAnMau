package fpoly.vunvph33438.mob2041.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.vunvph33438.mob2041.Database.DbHelper;
import fpoly.vunvph33438.mob2041.Model.ThuThu;

public class ThuThuDAO {
    public static final String TABLE_NAME = "ThuThu";
    public static final String COLUMN_MATT = "maTT";
    public static final String COLUMN_HO_TEN = "hoTen";
    public static final String COLUMN_MATKHAU = "matKhau";
    DbHelper dbHelper;

    public ThuThuDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public boolean insertData(ThuThu obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MATT, obj.getMaTT());
        contentValues.put(COLUMN_HO_TEN, obj.getHoTen());
        contentValues.put(COLUMN_MATKHAU, obj.getMatKhau());
        long check = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return check != -1;
    }

    public boolean delete(ThuThu obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {obj.getMaTT()};
        long check = sqLiteDatabase.delete(TABLE_NAME, COLUMN_MATT + "= ?", dk);
        return check != -1;
    }

    public boolean updatePass(ThuThu obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String dk[] = {obj.getMaTT()};
        contentValues.put(COLUMN_HO_TEN, obj.getHoTen());
        contentValues.put(COLUMN_MATKHAU, obj.getMatKhau());
        long check = sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_MATT + "= ?", dk);
        return check != -1;
    }

    public ArrayList<ThuThu> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<ThuThu> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String maTT = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MATT));
                String hoTen = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HO_TEN));
                String matKhau = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MATKHAU));
                list.add(new ThuThu(maTT, hoTen, matKhau));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<ThuThu> SelectAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return getAll(sql);
    }

    public ThuThu SelectID(String id) {
        String sql = "SELECT * FROM ThuThu WHERE maTT = ?";
        ArrayList<ThuThu> list = getAll(sql, id);
        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM ThuThu WHERE " + COLUMN_MATT + "=? AND " + COLUMN_MATKHAU + " = ?";
        String[] selectionArgs = new String[]{username, password};
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        boolean result = cursor.getCount() > 0;
        return result;
    }
}
