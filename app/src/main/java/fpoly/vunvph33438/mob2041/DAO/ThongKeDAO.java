package fpoly.vunvph33438.mob2041.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import fpoly.vunvph33438.mob2041.Database.DbHelper;
import fpoly.vunvph33438.mob2041.Model.Sach;
import fpoly.vunvph33438.mob2041.Model.Top;

public class ThongKeDAO {
    DbHelper dbHelper;
    Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public ThongKeDAO(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public ArrayList<Top> getTop() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String sql = "SELECT maSach ,Count(maSach) as soLuong FROM PhieuMuon GROUP BY maSach ORDER BY soLuong DESC LIMIT 10";
        ArrayList<Top> list = new ArrayList<>();
        SachDAO sachDao = new SachDAO(context);
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Top top = new Top();
                Sach sach = sachDao.selectID(cursor.getString(cursor.getColumnIndexOrThrow("maSach")));
                top.tenSach = sach.getTenSach();
                top.soLuong = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("soLuong")));
                list.add(top);
            } while (cursor.moveToNext());

        }
        return list;
    }

    public int DoanhThu(String startDay, String endDay) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String sql = "SELECT SUM(tienThue) as doanhThu FROM PhieuMuon WHERE ngay BETWEEN ? AND ?";
        String dk[] = {startDay, endDay};
        int doanhThu = 0;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, dk);
        if (cursor.moveToFirst()) {
            try {
                doanhThu = cursor.getInt(cursor.getColumnIndexOrThrow("doanhThu"));
            } catch (Exception e) {
                doanhThu = 0;
            }
        }
        cursor.close();
        return doanhThu;
    }
}
