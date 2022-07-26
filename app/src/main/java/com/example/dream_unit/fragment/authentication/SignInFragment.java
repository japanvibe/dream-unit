package com.example.dream_unit.fragment.authentication;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.dream_unit.AuthenticationActivity;
import com.example.dream_unit.R;
import com.example.dream_unit.api.Api;
import com.example.dream_unit.api.NetworkService;
import com.example.dream_unit.entity.User;
import com.example.dream_unit.helper.ValidationHelper;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;

public class SignInFragment extends Fragment {
    private TextInputEditText etSignInEmail, etSignInPassword;
    private Button btnSignIn;
    private Api api;
    private Call<Integer> call;

    public SignInFragment() {
        api=NetworkService.getInstance().getApi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sign_in, container, false);
        etSignInEmail=view.findViewById(R.id.etSignInEmail);
        etSignInPassword=view.findViewById(R.id.etSignInPassword);
        btnSignIn=view.findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user=new User();
                if(ValidationHelper.validateEmail(etSignInEmail.getText())
                        &&!etSignInPassword.getText().toString().isEmpty()){
                    user.setEmail(etSignInEmail.getText().toString());
                    user.setPassword(etSignInPassword.getText().toString());
                    call=api.checkUser(user);
                    AuthenticationActivity.sendCall(call,getActivity(),R.string.welcome_message,R.string.incorrect_email_password);
                }
                else{
                    Toast.makeText(getContext(),
                            R.string.fields_incorrect_message, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}