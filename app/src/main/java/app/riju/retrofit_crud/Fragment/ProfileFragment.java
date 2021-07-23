package app.riju.retrofit_crud.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.riju.retrofit_crud.ModelResponse.LoginResponse;
import app.riju.retrofit_crud.R;
import app.riju.retrofit_crud.RetrofitClient;
import app.riju.retrofit_crud.SharedPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    EditText userName, userEmail;
    Button updateUserButton;
    SharedPreferenceManager sharedPreferenceManager;
    int userId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        userName = view.findViewById(R.id.userName);
        userEmail = view.findViewById(R.id.userEmail);
        updateUserButton = view.findViewById(R.id.btnUpdateAccount);

        sharedPreferenceManager = new SharedPreferenceManager(getActivity());

        userId = sharedPreferenceManager.getUser().getId();

        updateUserButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnUpdateAccount:
                updatdeUserAccount();
                break;

        }
    }

    private void updatdeUserAccount() {
        String Name = userName.getText().toString();
        String Email = userEmail.getText().toString();

        if (Name.isEmpty()) {
            userName.requestFocus();
            userName.setError("Please Enter User Name");
        }
        if (Email.isEmpty()) {
            userEmail.requestFocus();
            userEmail.setError("Please Enter Your Email");
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            userEmail.requestFocus();
            userEmail.setError("Please enter correct email");
            return;
        }

        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().updateUserAccount(userId, Name, Email);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse updateResponse=response.body();
                if (response.isSuccessful()) {
                    if (updateResponse.getError().equals("200")) {
                        sharedPreferenceManager.saveUser(updateResponse.getUser());
                        Toast.makeText(getActivity(), updateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), updateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), updateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}