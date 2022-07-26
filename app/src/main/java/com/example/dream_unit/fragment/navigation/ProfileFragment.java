package com.example.dream_unit.fragment.navigation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dream_unit.AuthenticationActivity;
import com.example.dream_unit.InitialActivity;
import com.example.dream_unit.R;
import com.example.dream_unit.api.Api;
import com.example.dream_unit.api.NetworkService;
import com.example.dream_unit.entity.User;
import com.example.dream_unit.helper.ValidationHelper;
import com.example.dream_unit.picker.AppDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {
    private ImageButton btnLogOut, btnUpdate;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Api api;
    private Call<User> call;
    private Call<Integer> updateCall;
    private User user;
    private TextInputEditText etName, etEmail, etAddress, etBirthDate, etContactPhone;
    private TextView tvGender;
    private Spinner spinnerGender;
    private String[] genders;
    private ArrayAdapter<String> genderAdapter;
    private AppDatePicker appDatePicker;
    private boolean userUpdated;
    private AlertDialog.Builder builder;
    private AlertDialog saveDialog, connectionErrorDialog;
    private final int NO_CHOICE=0;
    private final int MALE = 1;
    private final int FEMALE = 2;
    private final String DATE_PICKER_TAG="datePicker";
    public ProfileFragment() {
        api = NetworkService.getInstance().getApi();
        user = new User();
        userUpdated = true;
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        builder = new AlertDialog.Builder(getActivity());
        genders = new String[]{getString(R.string.no_chosen_gender), getString(R.string.male), getString(R.string.female)};
        genderAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, genders);
        sharedPreferences = requireActivity().getSharedPreferences(AuthenticationActivity.USER_DATA, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        builder.setMessage(R.string.confidence_save_data_message);
        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                user.setName(etName.getText().toString());
                user.setEmail(etEmail.getText().toString());
                user.setAddress(etAddress.getText().toString());
                user.setContactPhone(etContactPhone.getText().toString());
                updateUserData();
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        saveDialog = builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        etName = view.findViewById(R.id.etName);
        etEmail = view.findViewById(R.id.etEmail);
        etAddress = view.findViewById(R.id.etAddress);
        etContactPhone = view.findViewById(R.id.etContactPhone);
        etBirthDate = view.findViewById(R.id.etBirthday);
        tvGender = view.findViewById(R.id.tvGender);
        spinnerGender = view.findViewById(R.id.spinnerGender);
        spinnerGender.setAdapter(genderAdapter);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != NO_CHOICE) {
                    String gender = parent.getItemAtPosition(position).toString();
                    user.setGender(gender);
                } else user.setGender(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btnLogOut=view.findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString(AuthenticationActivity.STATUS, AuthenticationActivity.LOGGED_OUT);
                editor.apply();
                Intent intent=new Intent(getContext(), AuthenticationActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });
        btnUpdate = view.findViewById(R.id.btnUpdate);
        appDatePicker = new AppDatePicker(user, etBirthDate);
        etBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appDatePicker = new AppDatePicker(user, etBirthDate);
                appDatePicker.show(getChildFragmentManager(), DATE_PICKER_TAG);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ValidationHelper.validateEmail(etEmail.getText()) && !etEmail.getText().toString().isEmpty())
                    saveDialog.show();
                else
                    Toast.makeText(getContext(), R.string.incorrect_email, Toast.LENGTH_SHORT).show();
            }
        });
        if (userUpdated) getUserData();
        return view;
    }

    private void getUserData() {
        call = api.getUser(InitialActivity.userId);
        if(InitialActivity.checkConnection()){
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    user = response.body();
                    userUpdated = false;
                    etName.setText(user.getName());
                    etEmail.setText(user.getEmail());
                    etAddress.setText(user.getAddress());
                    etContactPhone.setText(user.getContactPhone());
                    if (user.getBirthDate() != null) {
                        etBirthDate.setText(user.getBirthDate());
                    } else etBirthDate.setText(R.string.choose_date);
                    if (user.getGender() != null) {
                        if (user.getGender().equals(getString(R.string.male))) {
                            spinnerGender.setSelection(MALE);
                        } else spinnerGender.setSelection(FEMALE);
                    }
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {}
            });
        } else {
            builder.setPositiveButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getUserData();
                }
            }).setMessage(R.string.connection_error);
            connectionErrorDialog = builder.create();
            connectionErrorDialog.show();
        }
    }

    private void updateUserData() {
        updateCall = api.updateUser(InitialActivity.userId, user);
        if(InitialActivity.checkConnection()) {
            updateCall.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    Toast.makeText(getContext(), R.string.save_data_message, Toast.LENGTH_SHORT).show();
                    userUpdated = true;
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {}
            });
        }
        else
            Toast.makeText(getContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
    }
}