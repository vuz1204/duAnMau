package fpoly.vunvph33438.mob2041.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import fpoly.vunvph33438.mob2041.Adapter.TopAdapter;
import fpoly.vunvph33438.mob2041.DAO.ThongKeDAO;
import fpoly.vunvph33438.mob2041.Model.Top;
import fpoly.vunvph33438.mob2041.R;

public class TopFragment extends Fragment {
    View view;
    ListView listView;
    ThongKeDAO thongKeDAO;

    TopAdapter topAdapter;
    ArrayList<Top> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_top, container, false);
        listView = view.findViewById(R.id.lvTop);
        thongKeDAO = new ThongKeDAO(getContext());
        list = thongKeDAO.getTop();
        topAdapter = new TopAdapter(getContext(), list);
        listView.setAdapter(topAdapter);
        return view;
    }
}