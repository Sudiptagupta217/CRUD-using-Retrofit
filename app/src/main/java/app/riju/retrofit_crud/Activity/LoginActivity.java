package app.riju.retrofit_crud.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import app.riju.retrofit_crud.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText email, password;
    TextView registerLink;
    Button LoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        email = findViewById(R.id.email_ET);
        password = findViewById(R.id.passwword_ET);
        registerLink = findViewById(R.id.registerLink);
        LoginBtn = findViewById(R.id.loginBtn);

        registerLink.setOnClickListener(this);
        LoginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                break;
            case R.id.registerLink:
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}