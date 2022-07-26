package com.example.dream_unit.picker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.dream_unit.entity.User;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AppDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private User user;
    private TextInputEditText etBirthDate;
    private SimpleDateFormat sdf;
    private Date date;
    private Calendar calendar;
    public AppDatePicker(User user, TextInputEditText etBirthDate) {
        this.user=user;
        this.etBirthDate=etBirthDate;
        sdf=(SimpleDateFormat) SimpleDateFormat.getInstance();
        sdf.applyPattern("dd.MM.yyyy");
        calendar=Calendar.getInstance();
    }
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        if(user.getBirthDate()!=null){
            date=sdf.parse(user.getBirthDate(), new ParsePosition(0));
            calendar.setTimeInMillis(date.getTime());
        }
        DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(),this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        return datePickerDialog;
    }
    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        user.setBirthDate(sdf.format(calendar.getTime()));
        etBirthDate.setText(user.getBirthDate());
    }

}
