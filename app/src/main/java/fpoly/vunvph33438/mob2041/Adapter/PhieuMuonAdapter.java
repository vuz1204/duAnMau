package fpoly.vunvph33438.mob2041.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.vunvph33438.mob2041.DAO.PhieuMuonDAO;
import fpoly.vunvph33438.mob2041.DAO.SachDAO;
import fpoly.vunvph33438.mob2041.DAO.ThanhVienDAO;
import fpoly.vunvph33438.mob2041.Interface.ItemClickListener;
import fpoly.vunvph33438.mob2041.Model.PhieuMuon;
import fpoly.vunvph33438.mob2041.Model.Sach;
import fpoly.vunvph33438.mob2041.Model.ThanhVien;
import fpoly.vunvph33438.mob2041.R;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.PhieuMuonViewHolder> {
    Context context;

    ArrayList<PhieuMuon> list;

    SachDAO sachDAO;

    ThanhVienDAO thanhVienDAO;

    PhieuMuonDAO phieuMuonDAO;

    private ItemClickListener itemClickListener;

    public PhieuMuonAdapter(Context context, ArrayList<PhieuMuon> list) {
        this.context = context;
        this.list = list;
        sachDAO = new SachDAO(context);
        thanhVienDAO = new ThanhVienDAO(context);
        phieuMuonDAO = new PhieuMuonDAO(context);
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public PhieuMuonAdapter.PhieuMuonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_phieumuon, parent, false);
        return new PhieuMuonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonAdapter.PhieuMuonViewHolder holder, int position) {
        PhieuMuon phieuMuon = list.get(position);
        holder.tvMaPM.setText("Mã PM : " + phieuMuon.getMaPM());
        Sach sach = sachDAO.selectID(String.valueOf(phieuMuon.getMaSach()));
        holder.tvTenSachPM.setText("Sách : " + sach.getTenSach());
        ThanhVien thanhVien = thanhVienDAO.selectID(String.valueOf(phieuMuon.getMaTV()));
        holder.tvThanhVienPM.setText("Thành Viên : " + thanhVien.getHoTen());
        holder.tvNgayThuePM.setText("Ngày Thuê : " + phieuMuon.getNgay());
        holder.tvTienThuePM.setText("Tiền Thuê : " + phieuMuon.getTienThue());
        if (phieuMuon.getTienThue() < 50000) {
            holder.tvTienThuePM.setTextColor(ContextCompat.getColor(context, R.color.blue));
        } else {
            holder.tvTienThuePM.setTextColor(ContextCompat.getColor(context, R.color.red));
        }

        if (phieuMuon.getTraSach() == 0) {
            holder.tvTrangThaiPM.setTextColor(Color.BLUE);
            holder.tvTrangThaiPM.setText("Đã trả sách");
        } else {
            holder.tvTrangThaiPM.setTextColor(Color.RED);
            holder.tvTrangThaiPM.setText("Chưa trả sách");
        }
        holder.imgDeletePM.setOnClickListener(v -> {
            if (phieuMuon.getTraSach() == 0) {
                showDeleteDialog(position);
            } else {
                Toast.makeText(context, "Thành Viên chưa trả sách ", Toast.LENGTH_SHORT).show();
            }

        });
        holder.itemView.setOnLongClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.UpdateItem(position);
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void showDeleteDialog(int position) {
        PhieuMuon phieuMuon = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.warning);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa phiếu mượn " + phieuMuon.getMaPM() + " không?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (phieuMuonDAO.delete(phieuMuon)) {
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        list.remove(phieuMuon);
                        notifyDataSetChanged();
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

    class PhieuMuonViewHolder extends RecyclerView.ViewHolder {

        TextView tvMaPM, tvThanhVienPM, tvTenSachPM, tvNgayThuePM, tvTienThuePM, tvTrangThaiPM;
        ImageView imgDeletePM;

        public PhieuMuonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaPM = itemView.findViewById(R.id.tvMaPM);
            tvThanhVienPM = itemView.findViewById(R.id.tvThanhVienPM);
            tvTenSachPM = itemView.findViewById(R.id.tvTenSachPM);
            tvNgayThuePM = itemView.findViewById(R.id.tvNgayThuePM);
            tvTienThuePM = itemView.findViewById(R.id.tvTienThuePM);
            tvTrangThaiPM = itemView.findViewById(R.id.tvTrangThaiPM);
            imgDeletePM = itemView.findViewById(R.id.imgDeletePM);
        }
    }
}
