package fpoly.vunvph33438.mob2041.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.vunvph33438.mob2041.Model.LoaiSach;
import fpoly.vunvph33438.mob2041.R;

public class LoaiSachSpinner extends BaseAdapter {
    Context context;
    ArrayList<LoaiSach> list;

    public LoaiSachSpinner(Context context, ArrayList<LoaiSach> list) {
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

    private static class LoaiSachViewHolder {
        TextView tvMaLoai;
        TextView tvTenLoai;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LoaiSachViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_loaisach_spinner, parent, false);
            viewHolder = new LoaiSachViewHolder();
            viewHolder.tvMaLoai = convertView.findViewById(R.id.tvMaLoaiSpinner);
            viewHolder.tvTenLoai = convertView.findViewById(R.id.tvTenLoaiSpinner);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (LoaiSachViewHolder) convertView.getTag();
        }
        LoaiSach loaiSach = list.get(position);
        viewHolder.tvMaLoai.setText(String.valueOf(loaiSach.getMaLoai()));
        viewHolder.tvTenLoai.setText(loaiSach.getTenLoai());
        return convertView;
    }
}
