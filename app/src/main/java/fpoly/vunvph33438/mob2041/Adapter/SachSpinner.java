package fpoly.vunvph33438.mob2041.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.vunvph33438.mob2041.Model.Sach;
import fpoly.vunvph33438.mob2041.R;

public class SachSpinner extends BaseAdapter {
    Context context;
    ArrayList<Sach> list;

    public SachSpinner(Context context, ArrayList<Sach> list) {
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
        SachViewHolder sachViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_sach_spinner, parent, false);
            sachViewHolder = new SachViewHolder();
            sachViewHolder.tvMaSach = convertView.findViewById(R.id.tvMaSachSpinner);
            sachViewHolder.tvTenSach = convertView.findViewById(R.id.tvTenSachSpinner);
            convertView.setTag(sachViewHolder);
        } else {
            sachViewHolder = (SachViewHolder) convertView.getTag();
        }
        Sach sach = list.get(position);
        if (sach != null) {
            sachViewHolder.tvMaSach.setText(String.valueOf(sach.getMaSach()));
            sachViewHolder.tvTenSach.setText(String.valueOf(sach.getTenSach()));
        }
        return convertView;
    }

    private class SachViewHolder {
        TextView tvMaSach, tvTenSach;
    }
}
