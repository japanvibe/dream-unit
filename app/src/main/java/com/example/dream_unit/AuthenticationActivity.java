package com.example.dream_unit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dream_unit.fragment.authentication.SignInFragment;
import com.example.dream_unit.fragment.authentication.SignUpFragment;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationActivity extends AppCompatActivity {
    private SignUpFragment signUpFragment;
    private SignInFragment signInFragment;
    private Button btnSignUp;
    private Button btnSignIn;
    public static final String USER_DATA="UserData";
    public static final String USER_ID="UserId";
    public static final String STATUS="Status";
    public static final String LOGGED="Logged in";
    public static final String LOGGED_OUT="Logged out";
    private SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private String connectionError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        signUpFragment=new SignUpFragment();
        signInFragment=new SignInFragment();
        sharedPreferences=getSharedPreferences(AuthenticationActivity.USER_DATA, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        connectionError=getString(R.string.connection_error);
        btnSignUp=findViewById(R.id.btnSignUp);
        btnSignIn=findViewById(R.id.btnSignIn);
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutAuthenticationFrame,signInFragment).commit();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.layoutAuthenticationFrame,signUpFragment).commit();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.layoutAuthenticationFrame,signInFragment).commit();
            }
        });
    }
    public static void sendCall(Call<Integer> call, FragmentActivity activity, int message, int messageFailure){
        if(InitialActivity.checkConnection()) {
            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful() && response.body() != 0) {
                        editor.putInt(USER_ID, response.body());
                        editor.putString(STATUS, LOGGED);
                        editor.apply();
                        InitialActivity.userId=response.body();
                        Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
                        activity.startActivity(intent);
                        Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        activity.finish();
                    } else {
                        Snackbar.make(activity.getWindow().findViewById(R.id.authenticationActivityLayout), messageFailure, Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {}
            });
        }
        else
            Snackbar.make(activity.getWindow().findViewById(R.id.authenticationActivityLayout), R.string.connection_error, Snackbar.LENGTH_SHORT).show();

    }
}