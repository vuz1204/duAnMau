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

import fpoly.vunvph33438.mob2041.Adapter.LoaiSachAdapter;
import fpoly.vunvph33438.mob2041.DAO.LoaiSachDAO;
import fpoly.vunvph33438.mob2041.Interface.ItemClickListener;
import fpoly.vunvph33438.mob2041.Model.LoaiSach;
import fpoly.vunvph33438.mob2041.R;

public class LoaiSachFragment extends Fragment {
    public static final String TAG = "LoaiSachFragment";
    View view;
    RecyclerView rcvLoaiSach;
    LoaiSachDAO loaiSachDAO;
    EditText edMaLS, edTenLoai;
    ArrayList<LoaiSach> list = new ArrayList<>();
    LoaiSachAdapter loaiSachAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_loai_sach, container, false);
        rcvLoaiSach = view.findViewById(R.id.rcvLoaiSach);
        loaiSachDAO = new LoaiSachDAO(getContext());
        list = loaiSachDAO.selectAll();
        loaiSachAdapter = new LoaiSachAdapter(getContext(), list);
        rcvLoaiSach.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvLoaiSach.setAdapter(loaiSachAdapter);
        view.findViewById(R.id.fabLoaiSach).setOnClickListener(v -> {
            showAddOrUpdateDialog(getContext(), 0, null);
        });
        loaiSachAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void UpdateItem(int position) {
                LoaiSach loaiSach = list.get(position);
                showAddOrUpdateDialog(getContext(), 1, loaiSach);
            }
        });
        return view;
    }

    public void showAddOrUpdateDialog(Context context, int type, LoaiSach loaiSach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loaisach, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        edMaLS = view.findViewById(R.id.edMaLS);
        edTenLoai = view.findViewById(R.id.edTenLoai);
        edMaLS.setEnabled(false);
        if (type != 0) {
            if (loaiSach != null) {
                edMaLS.setText(String.valueOf(loaiSach.getMaLoai()));
                edTenLoai.setText(String.valueOf(loaiSach.getTenLoai()));
            }
        }
        view.findViewById(R.id.btnSaveLS).setOnClickListener(v -> {
            String tenLoai = edTenLoai.getText().toString();
            if (tenLoai.isEmpty()) {
                Toast.makeText(context, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                if (type == 0) {
                    LoaiSach loaiSachNew = new LoaiSach();
                    loaiSachNew.setTenLoai(tenLoai);
                    try {
                        if (loaiSachDAO.insertData(loaiSachNew)) {
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            list.add(loaiSachNew);
                            loaiSachAdapter.notifyDataSetChanged();
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    loaiSach.setTenLoai(tenLoai);
                    try {
                        if (loaiSachDAO.Update(loaiSach)) {
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
        view.findViewById(R.id.btnCancelLS).setOnClickListener(v -> {
            alertDialog.dismiss();
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    public void update() {
        list.clear();
        list.addAll(loaiSachDAO.selectAll());
        loaiSachAdapter.notifyDataSetChanged();
    }
}