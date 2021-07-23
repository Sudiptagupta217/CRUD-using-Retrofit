package app.riju.retrofit_crud.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.riju.retrofit_crud.ModelResponse.LoginResponse;
import app.riju.retrofit_crud.R;
import app.riju.retrofit_crud.RetrofitClient;
import app.riju.retrofit_crud.SharedPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText email, password;
    TextView registerLink;
    Button LoginBtn;
    SharedPreferenceManager sharedPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        email = findViewById(R.id.email_ET);
        password = findViewById(R.id.passwword_ET);
        registerLink = findViewById(R.id.registerLink);
        LoginBtn = findViewById(R.id.loginBtnok);

        registerLink.setOnClickListener(this);
        LoginBtn.setOnClickListener(this);
        sharedPreferenceManager= new SharedPreferenceManager(getApplicationContext());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginBtnok:
                userLogin();
                break;
            case R.id.registerLink:
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void userLogin() {
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (userPassword.isEmpty()) {
            password.requestFocus();
            password.setError("please enter your password");
            return;
        }
        if (userEmail.isEmpty()) {
            email.requestFocus();
            email.setError("please enter your email");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            email.requestFocus();
            email.setError("Please enter correct email");
            return;
        }

        Call<LoginResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .login(userEmail, userPassword);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (response.isSuccessful()) {
                    if (loginResponse.getError().equals("200")) {
                        sharedPreferenceManager.saveUser(loginResponse.getUser());
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // // // // //
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sharedPreferenceManager.isLoggedIn()){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // // // // //
            startActivity(intent);
        }
    }
}