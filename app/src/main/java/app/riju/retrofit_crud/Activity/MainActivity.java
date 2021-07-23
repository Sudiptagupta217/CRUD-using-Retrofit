package app.riju.retrofit_crud.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import app.riju.retrofit_crud.ModelResponse.RegisterResponse;
import app.riju.retrofit_crud.R;
import app.riju.retrofit_crud.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name, email, password;
    TextView loginLink;
    Button RegisterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        name = findViewById(R.id.name_ET);
        email = findViewById(R.id.email_ET);
        password = findViewById(R.id.passwword_ET);
        loginLink = findViewById(R.id.loginLink);
        RegisterBtn = findViewById(R.id.submitBtn);

        loginLink.setOnClickListener(this);
        RegisterBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submitBtn:
                registerUser();
                break;
            case R.id.loginLink:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void registerUser() {
        String userName = name.getText().toString();
        String userPassword = password.getText().toString();
        String userEmail = email.getText().toString();


        if (userName.isEmpty()) {
            name.requestFocus();
            name.setError("please enter your name");
            return;
        }

        if (userPassword.isEmpty()) {
            password.requestFocus();
            password.setError("please enter your password");
            return;
        }
        if (userPassword.length() < 5) {
            password.requestFocus();
            password.setError("please enter 5 digit password");
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

        Call<RegisterResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .register(userName, userEmail, userPassword);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                if (response.isSuccessful()) {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    Toast.makeText(MainActivity.this, registerResponse.getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, registerResponse.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}