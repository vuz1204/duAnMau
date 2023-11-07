package fpoly.vunvph33438.mob2041.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
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

public class ChangePasswordFragment extends Fragment {
    View view;
    EditText edOldPassWord, edNewPassword, edReNewPassword;
    SharedPreferences sharedPreferences;
    Button btnSaveChangePass, btnCancelChangePass;
    ThuThuDAO thuThuDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_change_password, container, false);
        edOldPassWord = view.findViewById(R.id.edOldPassWord);
        edNewPassword = view.findViewById(R.id.edNewPassword);
        edReNewPassword = view.findViewById(R.id.edReNewPassword);
        btnSaveChangePass = view.findViewById(R.id.btnSaveChangePass);
        btnCancelChangePass = view.findViewById(R.id.btnCancelChangePass);
        thuThuDAO = new ThuThuDAO(getActivity());
        btnCancelChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edOldPassWord.setText("");
                edNewPassword.setText("");
                edReNewPassword.setText("");
            }
        });
        btnSaveChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String user = sharedPreferences.getString("USERNAME", "");
                if (validate() > 0) {
                    ThuThu thuThu = thuThuDAO.SelectID(user);
                    thuThu.setMatKhau(edNewPassword.getText().toString());
                    if (thuThuDAO.updatePass(thuThu)) {
                        Toast.makeText(getActivity(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        edOldPassWord.setText("");
                        edNewPassword.setText("");
                        edReNewPassword.setText("");
                    } else {
                        Toast.makeText(getActivity(), "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

    public int validate() {
        int check = 1;
        if (edOldPassWord.getText().length() == 0 || edNewPassword.getText().length() == 0 || edReNewPassword.getText().length() == 0) {
            Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String passOld = sharedPreferences.getString("PASSWORD", "");
            String pass = edNewPassword.getText().toString();
            String rePass = edReNewPassword.getText().toString();
            if (!passOld.equals(edOldPassWord.getText().toString())) {
                Toast.makeText(getActivity(), "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (pass.equals(passOld)) {
                Toast.makeText(getActivity(), "Mật khẩu mới không được giống với mật khẩu cũ", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (!pass.equals(rePass)) {
                Toast.makeText(getActivity(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}