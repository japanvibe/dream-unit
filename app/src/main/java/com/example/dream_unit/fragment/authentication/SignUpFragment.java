package com.example.dream_unit.fragment.authentication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dream_unit.AuthenticationActivity;
import com.example.dream_unit.R;
import com.example.dream_unit.api.Api;
import com.example.dream_unit.api.NetworkService;
import com.example.dream_unit.entity.User;
import com.example.dream_unit.helper.ValidationHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;

public class SignUpFragment extends Fragment {
    private TextInputEditText etSignUpEmail, etSignUpPassword, etConfirmPassword;
    private Button btnSignUp;
    private Api api;
    private Call<Integer> call;

    public SignUpFragment() {
        api=NetworkService.getInstance().getApi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sign_up, container, false);
        etSignUpEmail=view.findViewById(R.id.etSignUpEmail);
        etSignUpPassword=view.findViewById(R.id.etSignUpPassword);
        etConfirmPassword=view.findViewById(R.id.etConfirmPassword);
        btnSignUp=view.findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user=new User();
                if(ValidationHelper.validateEmail(etSignUpEmail.getText())
                &&!etSignUpPassword.getText().toString().isEmpty()
                &&etSignUpPassword.getText().toString().equals(etConfirmPassword.getText().toString())){
                    user.setEmail(etSignUpEmail.getText().toString());
                    user.setPassword(etSignUpPassword.getText().toString());
                    call=api.createUser(user);
                    AuthenticationActivity.sendCall(call,getActivity(),R.string.registration_message, R.string.email_already_exists);

                }
                else{
                    Snackbar.make(requireView(), R.string.fields_incorrect_message,Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}