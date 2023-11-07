package fpoly.vunvph33438.mob2041.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import fpoly.vunvph33438.mob2041.DAO.ThongKeDAO;
import fpoly.vunvph33438.mob2041.R;

public class DoanhThuFragment extends Fragment {
    View view;
    EditText edStartDay, edEndDay;
    TextView tvDoanhThu;
    ThongKeDAO thongKeDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_doanh_thu, container, false);
        edStartDay = view.findViewById(R.id.edStartDay);
        edEndDay = view.findViewById(R.id.edEndDay);
        tvDoanhThu = view.findViewById(R.id.tvDoanhThu);
        thongKeDAO = new ThongKeDAO(getContext());
        view.findViewById(R.id.btnStartDay).setOnClickListener(v -> {
            showDatePickerDialog(edStartDay);
        });
        view.findViewById(R.id.btnEndDay).setOnClickListener(v -> {
            showDatePickerDialog(edEndDay);
        });
        view.findViewById(R.id.btnDoanhThu).setOnClickListener(v -> {
            String startDay = edStartDay.getText().toString();
            String endDay = edEndDay.getText().toString();

            if (!startDay.isEmpty() || !endDay.isEmpty()) {
                tvDoanhThu.setText(thongKeDAO.DoanhThu(startDay, endDay) + " VND");
            } else {
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ ngày", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void showDatePickerDialog(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, yearSelected, monthOfYear, dayOfMonthSelected) -> {
            Calendar selectedDateCalendar = Calendar.getInstance();
            selectedDateCalendar.set(yearSelected, monthOfYear, dayOfMonthSelected);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String selectedDate = sdf.format(selectedDateCalendar.getTime());
            editText.setText(selectedDate);
        }, year, month, dayOfMonth);
        datePickerDialog.show();
    }
}