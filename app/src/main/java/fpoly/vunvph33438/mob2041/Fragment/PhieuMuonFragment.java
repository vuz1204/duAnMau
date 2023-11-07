package fpoly.vunvph33438.mob2041.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fpoly.vunvph33438.mob2041.Adapter.PhieuMuonAdapter;
import fpoly.vunvph33438.mob2041.Adapter.SachSpinner;
import fpoly.vunvph33438.mob2041.Adapter.ThanhVienSpinner;
import fpoly.vunvph33438.mob2041.DAO.PhieuMuonDAO;
import fpoly.vunvph33438.mob2041.DAO.SachDAO;
import fpoly.vunvph33438.mob2041.DAO.ThanhVienDAO;
import fpoly.vunvph33438.mob2041.Interface.ItemClickListener;
import fpoly.vunvph33438.mob2041.Model.PhieuMuon;
import fpoly.vunvph33438.mob2041.Model.Sach;
import fpoly.vunvph33438.mob2041.Model.ThanhVien;
import fpoly.vunvph33438.mob2041.R;

public class PhieuMuonFragment extends Fragment {
    private static final String TAG = "PhieuMuonFragment";
    View view;
    PhieuMuonDAO phieuMuonDAO;
    SachDAO sachDAO;
    ThanhVienDAO thanhVienDAO;
    RecyclerView rcvPhieuMuon;
    EditText edMaPM, edSearch;
    CheckBox chkTrangThai;
    TextView tvNgay, tvTienThue;
    Spinner spinnerSach, spinnerThanhVien;
    ArrayList<Sach> listSach = new ArrayList<>();
    ArrayList<ThanhVien> listThanhVien = new ArrayList<>();
    ArrayList<PhieuMuon> listPhieuMuon = new ArrayList<>();
    SachSpinner sachSpinner;
    ThanhVienSpinner thanhVienSpinner;
    int maSach, maThanhVien, tienThueSach, positonSach, positionThanhVien;
    PhieuMuonAdapter phieuMuonAdapter;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_phieu_muon, container, false);
        rcvPhieuMuon = view.findViewById(R.id.rcvPhieuMuon);

        phieuMuonDAO = new PhieuMuonDAO(getContext());
        listPhieuMuon = phieuMuonDAO.selectAll();
        phieuMuonAdapter = new PhieuMuonAdapter(getContext(), listPhieuMuon);
        rcvPhieuMuon.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvPhieuMuon.setAdapter(phieuMuonAdapter);

        view.findViewById(R.id.fabPhieuMuon).setOnClickListener(v -> {
            showAddOrUpdateDialog(getContext(), 0, null);
        });
        phieuMuonAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void UpdateItem(int position) {
                PhieuMuon phieuMuon = listPhieuMuon.get(position);
                showAddOrUpdateDialog(getContext(), 1, phieuMuon);
            }
        });
        return view;
    }

    private void showAddOrUpdateDialog(Context context, int type, PhieuMuon phieuMuon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_phieumuon, null);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        edMaPM = dialogView.findViewById(R.id.edMaPM);
        spinnerSach = dialogView.findViewById(R.id.spinnerSach);
        spinnerThanhVien = dialogView.findViewById(R.id.spinnerThanhVien);
        chkTrangThai = dialogView.findViewById(R.id.chk_status_dialog);
        tvNgay = dialogView.findViewById(R.id.tvNgayThuePMDialog);
        tvTienThue = dialogView.findViewById(R.id.tvTienThuePMDialog);

        edMaPM.setEnabled(false);

        CreateSpinnerSach(context);
        CreateSpinnerThanhVien(context);

        if (type != 0) {
            edMaPM.setText(String.valueOf(phieuMuon.getMaPM()));
            tvNgay.setText("Ngày thuê : " + phieuMuon.getNgay());
            tvTienThue.setText("Tiền thuê : " + phieuMuon.getTienThue());
            if (phieuMuon.getTraSach() == 0) {
                chkTrangThai.setChecked(true);
            } else {
                chkTrangThai.setChecked(false);
            }
            for (int i = 0; i < listSach.size(); i++) {
                if (phieuMuon.getMaSach() == listSach.get(i).getMaSach()) {
                    positonSach = i;
                }
            }
            spinnerSach.setSelection(positonSach);

            for (int i = 0; i < listThanhVien.size(); i++) {
                if (phieuMuon.getMaTV() == listThanhVien.get(i).getMaTV()) {
                    positionThanhVien = i;
                }
            }
            spinnerThanhVien.setSelection(positionThanhVien);
        }

        dialogView.findViewById(R.id.btnSavePM).setOnClickListener(v -> {
            if (type == 0) {
                PhieuMuon phieuMuonNew = new PhieuMuon();
                phieuMuonNew.setMaSach(maSach);
                phieuMuonNew.setMaTV(maThanhVien);
                phieuMuonNew.setTraSach(chkTrangThai.isChecked() ? 0 : 1);
                Date date = new Date();
                String ngay = dateFormat.format(date);
                phieuMuonNew.setNgay(ngay);
                phieuMuonNew.setTienThue(tienThueSach);
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("user_use", getContext().MODE_PRIVATE);
                String maTT = sharedPreferences.getString("username_user", "");
                phieuMuonNew.setMaTT(maTT);
                try {
                    if (phieuMuonDAO.insert(phieuMuonNew)) {
                        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        listPhieuMuon.add(phieuMuonNew);
                        phieuMuonAdapter.notifyDataSetChanged();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "showAddOrEditDialog: ", e);
                    Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            } else {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("user_use", getContext().MODE_PRIVATE);
                String maTT = sharedPreferences.getString("username_user", "");
                phieuMuon.setMaTT(maTT);
                phieuMuon.setMaTV(maThanhVien);
                phieuMuon.setMaSach(maSach);
                phieuMuon.setTraSach(chkTrangThai.isChecked() ? 0 : 1);
                try {
                    if (phieuMuonDAO.update(phieuMuon)) {
                        Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        updateList();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(context, "sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "showAddOrEditDialog: ", e);
                    Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogView.findViewById(R.id.btnCanclePM).setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    private void CreateSpinnerThanhVien(Context context) {
        thanhVienDAO = new ThanhVienDAO(context);
        listThanhVien = thanhVienDAO.selectAll();
        thanhVienSpinner = new ThanhVienSpinner(context, listThanhVien);
        spinnerThanhVien.setAdapter(thanhVienSpinner);

        spinnerThanhVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maThanhVien = listThanhVien.get(position).getMaTV();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void CreateSpinnerSach(Context context) {
        sachDAO = new SachDAO(context);
        listSach = sachDAO.selectAll();
        sachSpinner = new SachSpinner(context, listSach);
        spinnerSach.setAdapter(sachSpinner);
        spinnerSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSach = listSach.get(position).getMaSach();
                tienThueSach = listSach.get(position).getGiaThue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void updateList() {
        listPhieuMuon.clear();
        listPhieuMuon.addAll(phieuMuonDAO.selectAll());
        phieuMuonAdapter.notifyDataSetChanged();
    }
}