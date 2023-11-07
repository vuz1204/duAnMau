package fpoly.vunvph33438.mob2041.Adapter;

import android.content.Context;
import android.content.DialogInterface;
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

import fpoly.vunvph33438.mob2041.DAO.ThanhVienDAO;
import fpoly.vunvph33438.mob2041.Interface.ItemClickListener;
import fpoly.vunvph33438.mob2041.Model.ThanhVien;
import fpoly.vunvph33438.mob2041.R;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ThanhVienViewHolder> {
    public ItemClickListener itemClickListener;
    Context context;
    ArrayList<ThanhVien> list;
    ThanhVienDAO thanhVienDAO;

    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> list) {
        thanhVienDAO = new ThanhVienDAO(context);
        this.context = context;
        this.list = list;
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public ThanhVienAdapter.ThanhVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thanhvien, parent, false);
        return new ThanhVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienAdapter.ThanhVienViewHolder holder, int position) {
        ThanhVien thanhVien = list.get(position);
        if (thanhVien != null) {
            holder.tvMaTV.setText("Mã TV : " + thanhVien.getMaTV());
            holder.tvHoTenTV.setText("Họ tên : " + thanhVien.getHoTen());
            holder.tvNamSinhTV.setText("Năm sinh : " + thanhVien.getNamSinh());
        }
        holder.imgDeleteTV.setOnClickListener(v -> {
            showDeleteAlert(position);
        });
        holder.itemView.setOnLongClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.UpdateItem(position);
            }
            return false;
        });
    }

    public void showDeleteAlert(int position) {
        ThanhVien thanhVien = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.warning);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa thành viên " + thanhVien.getHoTen() + " không?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (thanhVienDAO.Delete(thanhVien)) {
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        list.remove(thanhVien);
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

    class ThanhVienViewHolder extends RecyclerView.ViewHolder {

        TextView tvMaTV, tvHoTenTV, tvNamSinhTV;

        ImageView imgDeleteTV;

        public ThanhVienViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaTV = itemView.findViewById(R.id.tvMaTV);
            tvHoTenTV = itemView.findViewById(R.id.tvHoTenTV);
            tvNamSinhTV = itemView.findViewById(R.id.tvNamSinhTV);
            imgDeleteTV = itemView.findViewById(R.id.imgDeleteTV);
        }
    }
}
