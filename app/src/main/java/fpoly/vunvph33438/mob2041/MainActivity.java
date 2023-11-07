package fpoly.vunvph33438.mob2041;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import fpoly.vunvph33438.mob2041.DAO.ThuThuDAO;
import fpoly.vunvph33438.mob2041.Fragment.AddUserFragment;
import fpoly.vunvph33438.mob2041.Fragment.ChangePasswordFragment;
import fpoly.vunvph33438.mob2041.Fragment.DoanhThuFragment;
import fpoly.vunvph33438.mob2041.Fragment.LoaiSachFragment;
import fpoly.vunvph33438.mob2041.Fragment.PhieuMuonFragment;
import fpoly.vunvph33438.mob2041.Fragment.SachFragment;
import fpoly.vunvph33438.mob2041.Fragment.ThanhVienFragment;
import fpoly.vunvph33438.mob2041.Fragment.TopFragment;
import fpoly.vunvph33438.mob2041.Model.ThuThu;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;

    DrawerLayout drawerLayout;

    NavigationView navigationView;

    ActionBarDrawerToggle drawerToggle;

    View mHeaderView;

    ThuThuDAO thuThuDAO;

    TextView tvUser;

    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbarMain);
        thuThuDAO = new ThuThuDAO(MainActivity.this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thư Viện Phương Nam");
        drawerLayout = findViewById(R.id.drawer_Layout);
        navigationView = findViewById(R.id.nvView);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new PhieuMuonFragment()).commit();

        navigationView = findViewById(R.id.nvView);
        mHeaderView = navigationView.getHeaderView(0);
        tvUser = mHeaderView.findViewById(R.id.tvUser);
        Intent intent = getIntent();
        String user = intent.getStringExtra("user");
        thuThuDAO = new ThuThuDAO(this);
        ThuThu thuThu = thuThuDAO.SelectID(user);
        String username = thuThu.getHoTen();
        SharedPreferences sharedPreferences = getSharedPreferences("user_use", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username_user", thuThu.getMaTT());
        editor.apply();
        tvUser.setText("Welcome " + username + "!");
        if (user.equalsIgnoreCase("admin")) {
            navigationView.getMenu().findItem(R.id.sub_AddUser).setVisible(true);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_PhieuMuon) {
                    getSupportActionBar().setTitle("Quản lý phiếu mượn");
                    PhieuMuonFragment phieuMuon = new PhieuMuonFragment();
                    replaceFragment(phieuMuon);
                }
                if (item.getItemId() == R.id.nav_LoaiSach) {
                    getSupportActionBar().setTitle("Quản lý loại sách");
                    LoaiSachFragment loaiSach = new LoaiSachFragment();
                    replaceFragment(loaiSach);
                }
                if (item.getItemId() == R.id.nav_Sach) {
                    getSupportActionBar().setTitle("Quản lý sách");
                    SachFragment sach = new SachFragment();
                    replaceFragment(sach);
                }
                if (item.getItemId() == R.id.nav_ThanhVien) {
                    getSupportActionBar().setTitle("Quản lý thành viên");
                    ThanhVienFragment thanhVien = new ThanhVienFragment();
                    replaceFragment(thanhVien);
                }
                if (item.getItemId() == R.id.sub_Top) {
                    getSupportActionBar().setTitle("Top 10 sách cho thuê nhiều nhất");
                    TopFragment top = new TopFragment();
                    replaceFragment(top);
                }
                if (item.getItemId() == R.id.sub_DoanhThu) {
                    getSupportActionBar().setTitle("Thống kê doanh thu");
                    DoanhThuFragment doanhThu = new DoanhThuFragment();
                    replaceFragment(doanhThu);
                }
                if (item.getItemId() == R.id.sub_AddUser) {
                    getSupportActionBar().setTitle("Thêm thủ thư");
                    AddUserFragment addUser = new AddUserFragment();
                    replaceFragment(addUser);
                }
                if (item.getItemId() == R.id.sub_Pass) {
                    getSupportActionBar().setTitle("Đổi mật khẩu");
                    ChangePasswordFragment changePass = new ChangePasswordFragment();
                    replaceFragment(changePass);
                }
                if (item.getItemId() == R.id.sub_Logout) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    Toast.makeText(MainActivity.this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
                    finish();
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) drawerLayout.openDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }
}
