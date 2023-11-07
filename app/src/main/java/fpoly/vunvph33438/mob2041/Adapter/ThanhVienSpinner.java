package fpoly.vunvph33438.mob2041.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.vunvph33438.mob2041.Model.ThanhVien;
import fpoly.vunvph33438.mob2041.R;

public class ThanhVienSpinner extends BaseAdapter {
    Context context;
    ArrayList<ThanhVien> list;

    public ThanhVienSpinner(Context context, ArrayList<ThanhVien> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ThanhVienViewHolder thanhVienViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_thanhvien_spinner, parent, false);
            thanhVienViewHolder = new ThanhVienViewHolder();
            thanhVienViewHolder.tvMaTV = convertView.findViewById(R.id.tvMaTVSpinner);
            thanhVienViewHolder.tvHoTenTV = convertView.findViewById(R.id.tvHoTenTVSpinner);
            convertView.setTag(thanhVienViewHolder);
        } else {
            thanhVienViewHolder = (ThanhVienViewHolder) convertView.getTag();
        }
        ThanhVien thanhVien = list.get(position);
        if (thanhVien != null) {
            thanhVienViewHolder.tvMaTV.setText(String.valueOf(thanhVien.getMaTV()));
            thanhVienViewHolder.tvHoTenTV.setText(thanhVien.getHoTen());
        }
        return convertView;
    }

    private class ThanhVienViewHolder {
        TextView tvMaTV, tvHoTenTV;
    }
}
