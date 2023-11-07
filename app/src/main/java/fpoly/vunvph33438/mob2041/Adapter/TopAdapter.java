package fpoly.vunvph33438.mob2041.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.vunvph33438.mob2041.Model.Top;
import fpoly.vunvph33438.mob2041.R;

public class TopAdapter extends BaseAdapter {
    Context context;
    ArrayList<Top> list;

    public TopAdapter(Context context, ArrayList<Top> list) {
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
        TopViewHolder TopViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_top, parent, false);
            TopViewHolder = new TopViewHolder();
            TopViewHolder.tvTenSachTop = convertView.findViewById(R.id.tvTenSachTop);
            TopViewHolder.tvSoLuongTop = convertView.findViewById(R.id.tvSoLuongTop);
            convertView.setTag(TopViewHolder);
        } else {
            TopViewHolder = (TopViewHolder) convertView.getTag();
        }
        Top top = list.get(position);
        if (top != null) {
            TopViewHolder.tvTenSachTop.setText("Tên Sách : " + list.get(position).tenSach);
            TopViewHolder.tvSoLuongTop.setText("Số lượng : " + list.get(position).soLuong);
        }
        return convertView;
    }

    private class TopViewHolder {
        TextView tvTenSachTop, tvSoLuongTop;
    }
}
