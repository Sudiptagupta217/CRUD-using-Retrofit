package app.riju.retrofit_crud.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.riju.retrofit_crud.R;
import app.riju.retrofit_crud.SharedPreferenceManager;

public class DashboardFragment extends Fragment {
    TextView showName;
    TextView showEmail;
    SharedPreferenceManager sharedPreferenceManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        showName = view.findViewById(R.id.showName);
        showEmail = view.findViewById(R.id.showEmail);

        sharedPreferenceManager=new SharedPreferenceManager(getActivity());

        showName.setText("Name: "+sharedPreferenceManager.getUser().getUsername());;
        showEmail.setText("Email: "+sharedPreferenceManager.getUser().getEmail());

        return view;
    }
}