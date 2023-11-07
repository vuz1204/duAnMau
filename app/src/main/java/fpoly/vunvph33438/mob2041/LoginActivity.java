package fpoly.vunvph33438.mob2041;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fpoly.vunvph33438.mob2041.DAO.ThuThuDAO;

public class LoginActivity extends AppCompatActivity {
    EditText edUsername, edPassword;
    Button btnLogin, btnCancel;
    CheckBox chkRememberPass;
    String strUser, strPass;
    ThuThuDAO thuThuDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edUsername = findViewById(R.id.edUsername);
        edPassword = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnCancel = findViewById(R.id.btnCancel);
        chkRememberPass = findViewById(R.id.chkRememberPass);
        thuThuDAO = new ThuThuDAO(this);
        SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        edUsername.setText(sharedPreferences.getString("USERNAME", ""));
        edPassword.setText(sharedPreferences.getString("PASSWORD", ""));
        chkRememberPass.setChecked(sharedPreferences.getBoolean("REMEMBER", false));
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edUsername.setText("");
                edPassword.setText("");
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }

    public void checkLogin() {
        strUser = edUsername.getText().toString();
        strPass = edPassword.getText().toString();
        if (strUser.isEmpty() || strPass.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
        } else {
            if (thuThuDAO.checkLogin(strUser, strPass)) {
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                rememberUser(strUser, strPass, chkRememberPass.isChecked());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("user", strUser);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void rememberUser(String u, String p, boolean status) {
        SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        if (!status) {
            edit.clear();
        } else {
            edit.putString("USERNAME", u);
            edit.putString("PASSWORD", p);
            edit.putBoolean("REMEMBER", status);
        }
        edit.commit();
    }
}