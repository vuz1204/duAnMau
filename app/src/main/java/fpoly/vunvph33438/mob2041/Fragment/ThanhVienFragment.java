package fpoly.vunvph33438.mob2041.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.vunvph33438.mob2041.Adapter.ThanhVienAdapter;
import fpoly.vunvph33438.mob2041.DAO.ThanhVienDAO;
import fpoly.vunvph33438.mob2041.Interface.ItemClickListener;
import fpoly.vunvph33438.mob2041.Model.ThanhVien;
import fpoly.vunvph33438.mob2041.R;

public class ThanhVienFragment extends Fragment {
    View view;

    RecyclerView rcvThanhVien;

    ThanhVienDAO thanhVienDAO;

    EditText edMaTV, edHoTenTV, edNamSinhTV;

    String strHoten, strNamSinh;

    ArrayList<ThanhVien> list = new ArrayList<>();

    ThanhVienAdapter thanhVienAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_thanh_vien, container, false);
        rcvThanhVien = view.findViewById(R.id.rcvThanhVien);
        thanhVienDAO = new ThanhVienDAO(getContext());
        list = thanhVienDAO.selectAll();
        thanhVienAdapter = new ThanhVienAdapter(getContext(), list);
        rcvThanhVien.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvThanhVien.setAdapter(thanhVienAdapter);
        view.findViewById(R.id.fabThanhVien).setOnClickListener(v -> {
            showAddOrUpdateDialog(getContext(), 0, null);
        });
        thanhVienAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void UpdateItem(int position) {
                ThanhVien thanhVien = list.get(position);
                showAddOrUpdateDialog(getContext(), 1, thanhVien);
            }
        });
        return view;
    }

    public void showAddOrUpdateDialog(Context context, int type, ThanhVien thanhVien) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_thanhvien, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        edMaTV = view.findViewById(R.id.edMaTV);
        edHoTenTV = view.findViewById(R.id.edHoTenTV);
        edNamSinhTV = view.findViewById(R.id.edNamSinhTV);
        edMaTV.setEnabled(false);
        if (type != 0) {
            if (thanhVien != null) {
                edMaTV.setText(String.valueOf(thanhVien.getMaTV()));
                edHoTenTV.setText(thanhVien.getHoTen());
                edNamSinhTV.setText(String.valueOf(thanhVien.getNamSinh()));
            }
        }
        view.findViewById(R.id.btnSaveTV).setOnClickListener(v -> {
            strHoten = edHoTenTV.getText().toString().trim();
            strNamSinh = edNamSinhTV.getText().toString().trim();
            if (validate(strHoten, strNamSinh)) {
                if (type == 0) {
                    ThanhVien thanhVienNew = new ThanhVien();
                    thanhVienNew.setHoTen(strHoten);
                    thanhVienNew.setNamSinh(strNamSinh);
                    try {
                        if (thanhVienDAO.insertData(thanhVienNew)) {
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            list.add(thanhVienNew);
                            thanhVienAdapter.notifyDataSetChanged();
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    thanhVien.setHoTen(strHoten);
                    thanhVien.setNamSinh(strNamSinh);
                    try {
                        if (thanhVienDAO.Update(thanhVien)) {
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            update();
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        view.findViewById(R.id.btnCancelTV).setOnClickListener(v -> {
            alertDialog.dismiss();
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    private boolean validate(String hoTen, String namSinh) {
        try {
            if (hoTen.isEmpty() || namSinh.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void update() {
        list.clear();
        list.addAll(thanhVienDAO.selectAll());
        thanhVienAdapter.notifyDataSetChanged();
    }
}
