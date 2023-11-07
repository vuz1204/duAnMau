package fpoly.vunvph33438.mob2041.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.vunvph33438.mob2041.DAO.LoaiSachDAO;
import fpoly.vunvph33438.mob2041.Interface.ItemClickListener;
import fpoly.vunvph33438.mob2041.Model.LoaiSach;
import fpoly.vunvph33438.mob2041.R;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.LoaiSachViewHolder> {
    private static final String TAG = "LoaiSachAdapter";
    Context context;
    ArrayList<LoaiSach> list;
    LoaiSachDAO loaiSachDAO;
    private ItemClickListener itemClickListener;

    public LoaiSachAdapter(Context context, ArrayList<LoaiSach> list) {
        this.context = context;
        this.list = list;
        loaiSachDAO = new LoaiSachDAO(context);
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public LoaiSachAdapter.LoaiSachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_loaisach, parent, false);
        return new LoaiSachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSachAdapter.LoaiSachViewHolder holder, int position) {
        holder.itemView.setOnLongClickListener(v -> {
            try {
                if (itemClickListener != null) {
                    itemClickListener.UpdateItem(position);
                }
            } catch (Exception e) {
                Log.e(TAG, "onBindViewHolder: " + e);
            }
            return false;
        });
        holder.tvMaLS.setText("Mã loại : " + list.get(position).getMaLoai());
        holder.tvTenLoai.setText("Tên loại : " + list.get(position).getTenLoai());
        holder.imgDeleteLS.setOnClickListener(v -> {
            showDeleteAlert(position);
        });
    }

    public void showDeleteAlert(int position) {
        LoaiSach loaiSach = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.warning);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa " + loaiSach.getTenLoai() + " không?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (loaiSachDAO.Delete(loaiSach)) {
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        list.remove(loaiSach);
                        notifyItemChanged(position);
                        notifyItemRemoved(position);
                    } else {
                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class LoaiSachViewHolder extends RecyclerView.ViewHolder {

        TextView tvMaLS, tvTenLoai;

        ImageView imgDeleteLS;

        public LoaiSachViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaLS = itemView.findViewById(R.id.tvMaLS);
            tvTenLoai = itemView.findViewById(R.id.tvTenLoai);
            imgDeleteLS = itemView.findViewById(R.id.imgDeleteLS);
        }
    }
}
