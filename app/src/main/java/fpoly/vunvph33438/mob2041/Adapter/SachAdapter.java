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

import fpoly.vunvph33438.mob2041.DAO.SachDAO;
import fpoly.vunvph33438.mob2041.Interface.ItemClickListener;
import fpoly.vunvph33438.mob2041.Model.Sach;
import fpoly.vunvph33438.mob2041.R;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.SachViewHolder> {
    Context context;
    ArrayList<Sach> list;
    SachDAO sachDAO;

    private ItemClickListener itemClickListener;

    public SachAdapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
        sachDAO = new SachDAO(context);
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public SachAdapter.SachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sach, parent, false);
        return new SachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachAdapter.SachViewHolder holder, int position) {
        Sach sach = list.get(position);
        if (sach != null) {
            holder.tvMaSach.setText("Mã sách : " + sach.getMaSach());
            holder.tvTenSach.setText("Tên sách : " + sach.getTenSach());
            holder.tvGiaThue.setText("Giá thuê :" + sach.getGiaThue());
            holder.tvMaLoai.setText("Mã loại : " + sach.getMaLoai());
        }
        holder.imgDeleteSach.setOnClickListener(v -> {
            showDeleteDialog(position);
        });
        holder.itemView.setOnLongClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.UpdateItem(position);
            }
            return false;
        });
    }

    public void showDeleteDialog(int position) {
        Sach sach = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.warning);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa " + sach.getTenSach() + " không?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (sachDAO.Delete(sach)) {
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        list.remove(sach);
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

    class SachViewHolder extends RecyclerView.ViewHolder {

        TextView tvMaSach, tvTenSach, tvGiaThue, tvMaLoai;
        ImageView imgDeleteSach;

        public SachViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaSach = itemView.findViewById(R.id.tvMaSach);
            tvTenSach = itemView.findViewById(R.id.tvTenSach);
            tvGiaThue = itemView.findViewById(R.id.tvGiaThue);
            tvMaLoai = itemView.findViewById(R.id.tvMaLoai);
            imgDeleteSach = itemView.findViewById(R.id.imgDeleteSach);
        }
    }
}
