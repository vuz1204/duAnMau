package fpoly.vunvph33438.mob2041.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import fpoly.vunvph33438.mob2041.DAO.ThuThuDAO;
import fpoly.vunvph33438.mob2041.Model.ThuThu;
import fpoly.vunvph33438.mob2041.R;

public class AddUserFragment extends Fragment {
    View view;

    EditText edAddUsername, edAddName, edAddPass, edAddRePass;

    ThuThuDAO thuThuDAO;

    Button btnSaveAdd, btnCancelAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_user, container, false);
        edAddUsername = view.findViewById(R.id.edAddUsername);
        edAddName = view.findViewById(R.id.edAddName);
        edAddPass = view.findViewById(R.id.edAddPass);
        edAddRePass = view.findViewById(R.id.edAddRePass);
        btnSaveAdd = view.findViewById(R.id.btnSaveAdd);
        btnCancelAdd = view.findViewById(R.id.btnCancelAdd);
        thuThuDAO = new ThuThuDAO(getActivity());
        btnCancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edAddUsername.setText("");
                edAddName.setText("");
                edAddPass.setText("");
                edAddRePass.setText("");
            }
        });
        btnSaveAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate() > 0) {
                    ThuThu thuThu = new ThuThu();
                    thuThu.setMaTT(edAddUsername.getText().toString());
                    thuThu.setHoTen(edAddName.getText().toString());
                    thuThu.setMatKhau(edAddPass.getText().toString());
                    if (thuThuDAO.insertData(thuThu)) {
                        Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        edAddUsername.setText("");
                        edAddName.setText("");
                        edAddPass.setText("");
                        edAddRePass.setText("");
                    } else {
                        Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

    public int validate() {
        int check = 1;
        String username = edAddUsername.getText().toString();
        String pass = edAddPass.getText().toString();
        String rePass = edAddRePass.getText().toString();

        if (username.length() == 0 || edAddName.getText().length() == 0 || pass.length() == 0 || rePass.length() == 0) {
            Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            ThuThu existingThuthu = thuThuDAO.SelectID(username);
            if (existingThuthu != null) {
                Toast.makeText(getActivity(), "Mã thủ thư đã tồn tại", Toast.LENGTH_SHORT).show();
                check = -1;
            } else if (!pass.equals(rePass)) {
                Toast.makeText(getActivity(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}