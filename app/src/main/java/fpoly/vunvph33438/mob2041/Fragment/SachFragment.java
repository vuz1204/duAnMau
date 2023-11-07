package fpoly.vunvph33438.mob2041.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.vunvph33438.mob2041.Adapter.LoaiSachSpinner;
import fpoly.vunvph33438.mob2041.Adapter.SachAdapter;
import fpoly.vunvph33438.mob2041.DAO.LoaiSachDAO;
import fpoly.vunvph33438.mob2041.DAO.SachDAO;
import fpoly.vunvph33438.mob2041.Interface.ItemClickListener;
import fpoly.vunvph33438.mob2041.Model.LoaiSach;
import fpoly.vunvph33438.mob2041.Model.Sach;
import fpoly.vunvph33438.mob2041.R;

public class SachFragment extends Fragment {
    private static final String TAG = "SachFragment ";
    View view;
    RecyclerView rcvSach;
    SachDAO sachDAO;
    ArrayList<Sach> list = new ArrayList<>();
    EditText edMaSach, edTenSach, edGiaThue;
    Spinner spinnerLoaiSach;
    LoaiSachDAO loaiSachDAO;
    int selectedPosition;
    LoaiSachSpinner LoaiSachSpinner;
    ArrayList<LoaiSach> listLoaiSach = new ArrayList<>();
    int maLS;
    SachAdapter sachAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sach, container, false);
        rcvSach = view.findViewById(R.id.rcvSach);
        sachDAO = new SachDAO(getContext());
        list = sachDAO.selectAll();
        sachAdapter = new SachAdapter(getContext(), list);
        rcvSach.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvSach.setAdapter(sachAdapter);
        view.findViewById(R.id.fabSach).setOnClickListener(v -> {
            showAddOrUpdateDialog(getContext(), 0, null);
        });
        sachAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void UpdateItem(int position) {
                Sach sach = list.get(position);
                showAddOrUpdateDialog(getContext(), 1, sach);
            }
        });
        return view;
    }

    private void showAddOrUpdateDialog(Context context, int type, Sach sach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_sach, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        edMaSach = view.findViewById(R.id.edMaSach);
        edTenSach = view.findViewById(R.id.edTenSach);
        edGiaThue = view.findViewById(R.id.edGiaThue);
        spinnerLoaiSach = view.findViewById(R.id.spinnerLoaiSach);
        edMaSach.setEnabled(false);

        loaiSachDAO = new LoaiSachDAO(context);
        listLoaiSach = loaiSachDAO.selectAll();
        LoaiSachSpinner = new LoaiSachSpinner(context, listLoaiSach);
        spinnerLoaiSach.setAdapter(LoaiSachSpinner);

        if (type != 0) {
            edMaSach.setText(String.valueOf(sach.getMaSach()));
            edTenSach.setText(sach.getTenSach());
            edGiaThue.setText(String.valueOf(sach.getGiaThue()));
            for (int i = 0; i < listLoaiSach.size(); i++) {
                if (sach.getMaLoai() == listLoaiSach.get(i).getMaLoai()) {
                    selectedPosition = i;
                }
            }
            spinnerLoaiSach.setSelection(selectedPosition);
        }

        spinnerLoaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLS = listLoaiSach.get(position).getMaLoai();//khi click spinner gan ma vao maLoaiSach
                Log.e(TAG, "ClickSpinner: " + maLS);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        view.findViewById(R.id.btnSaveSach).setOnClickListener(v -> {
            String tenSach = edTenSach.getText().toString();
            String giaThue = edGiaThue.getText().toString();
            if (validate(tenSach, giaThue)) {
                if (type == 0) {
                    Sach sachNew = new Sach();
                    sachNew.setTenSach(tenSach);
                    sachNew.setGiaThue(Integer.parseInt(giaThue));
                    sachNew.setMaLoai(maLS);
                    try {
                        if (sachDAO.insertData(sachNew)) {
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            list.add(sachNew);
                            sachAdapter.notifyDataSetChanged();
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Lỗi Database: ", e);
                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    sach.setTenSach(tenSach);
                    sach.setGiaThue(Integer.parseInt(giaThue));
                    sach.setMaLoai(maLS);
                    Log.e(TAG, "showAddOrUpdateDialog: " + maLS);
                    try {
                        if (sachDAO.Update(sach)) {
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            updateList();
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Lỗi Database: ", e);
                        Toast.makeText(context, "sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
        view.findViewById(R.id.btnCancelSach).setOnClickListener(v -> {
            clearFrom();
            alertDialog.dismiss();
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    private boolean validate(String tenSach, String giaSach) {
        try {
            if (tenSach.isEmpty() || giaSach.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void updateList() {
        list.clear();
        list.addAll(sachDAO.selectAll());
        sachAdapter.notifyDataSetChanged();
    }

    private void clearFrom() {
        edMaSach.setText("");
        edGiaThue.setText("");
        edTenSach.setText("");
    }
}