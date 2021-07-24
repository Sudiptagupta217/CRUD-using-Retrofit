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
import app.riju.retrofit_crud.ModelResponse.RegisterResponse;
import app.riju.retrofit_crud.R;
import app.riju.retrofit_crud.RetrofitClient;
import app.riju.retrofit_crud.SharedPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    EditText userName, userEmail, currentPassword, newPassword;
    Button updateUserButton, updateUserPasswordButton;
    SharedPreferenceManager sharedPreferenceManager;
    int userId;
    String userEmailId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //for update account
        userName = view.findViewById(R.id.userName);
        userEmail = view.findViewById(R.id.userEmail);
        updateUserButton = view.findViewById(R.id.btnUpdateAccount);

        //for update password
        currentPassword = view.findViewById(R.id.userPassword);
        newPassword = view.findViewById(R.id.newPassword);
        updateUserPasswordButton = view.findViewById(R.id.btnUpdatePassword);


        sharedPreferenceManager = new SharedPreferenceManager(getActivity());
        userId = sharedPreferenceManager.getUser().getId();
        userEmailId = sharedPreferenceManager.getUser().getEmail();

        updateUserButton.setOnClickListener(this);
        updateUserPasswordButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnUpdateAccount:
                updatdeUserAccount();
                break;
            case R.id.btnUpdatePassword:
                updatePassword();
                break;

        }
    }

    private void updatePassword() {
        String userCurrentPass = currentPassword.getText().toString();
        String userNewPass = newPassword.getText().toString();

        if (userCurrentPass.isEmpty()) {
            currentPassword.requestFocus();
            currentPassword.setError("Enter current password");
            return;
        }
        if (userNewPass.isEmpty()) {
            newPassword.requestFocus();
            newPassword.setError("Enter new password");
            return;
        }

        if (userCurrentPass.length() < 6) {
            currentPassword.requestFocus();
            currentPassword.setError("Enter 6 digit new password");
            return;
        }

        Call<RegisterResponse> call = RetrofitClient.getInstance().getApi().updateUserPassword(userEmailId, userCurrentPass, userNewPass);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse updatePasswordResponse = response.body();

                if (response.isSuccessful()) {
                    if (updatePasswordResponse.getError().equals("200")) {
                        Toast.makeText(getActivity(), updatePasswordResponse.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void updatdeUserAccount() {
        String Name = userName.getText().toString();
        String Email = userEmail.getText().toString();

        if (Name.isEmpty()) {
            userName.requestFocus();
            userName.setError("Please Enter User Name");
            return;
        }
        if (Email.isEmpty()) {
            userEmail.requestFocus();
            userEmail.setError("Please Enter Your Email");
            return;
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
                LoginResponse updateResponse = response.body();
                if (response.isSuccessful()) {
                    if (updateResponse.getError().equals("200")) {
                        sharedPreferenceManager.saveUser(updateResponse.getUser());
                        Toast.makeText(getActivity(), updateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), updateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
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