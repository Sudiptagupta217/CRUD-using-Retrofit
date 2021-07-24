package app.riju.retrofit_crud.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import app.riju.retrofit_crud.Fragment.DashboardFragment;
import app.riju.retrofit_crud.Fragment.ProfileFragment;
import app.riju.retrofit_crud.Fragment.UsersFragment;
import app.riju.retrofit_crud.ModelResponse.RegisterResponse;
import app.riju.retrofit_crud.R;
import app.riju.retrofit_crud.RetrofitClient;
import app.riju.retrofit_crud.SharedPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    SharedPreferenceManager sharedPreferenceManager;
    int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        loadFragment(new DashboardFragment());
        sharedPreferenceManager = new SharedPreferenceManager(getApplicationContext());
        userid = sharedPreferenceManager.getUser().getId();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.dashboard:
                fragment = new DashboardFragment();
                break;
            case R.id.users:
                fragment = new UsersFragment();
                break;
            case R.id.profile:
                fragment = new ProfileFragment();
                break;
        }
        if (fragment != null) {
            loadFragment(fragment);
        }

        return true;
    }

    void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, fragment).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                logoutUser();
                break;
            case R.id.deleteAccount:
                deleteAccount();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAccount() {

        Call<RegisterResponse> call = RetrofitClient.getInstance().getApi().deleteAccount(userid);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse deleteresponse = response.body();
                if (response.isSuccessful()) {
                    if (deleteresponse.getError().equals("200")) {
                       logoutUser();
                        Toast.makeText(HomeActivity.this, deleteresponse.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(HomeActivity.this,deleteresponse.getMsg(),Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void logoutUser() {
        sharedPreferenceManager.loggedOut();
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        Toast.makeText(this, "Loged Out", Toast.LENGTH_SHORT).show();
    }
}