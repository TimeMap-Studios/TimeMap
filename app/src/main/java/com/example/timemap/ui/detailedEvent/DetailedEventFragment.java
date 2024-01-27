package com.example.timemap.ui.detailedEvent;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.timemap.databinding.FragmentDetailedEventBinding;
import com.example.timemap.databinding.FragmentWeekBinding;
import com.example.timemap.ui.currentWeek.WeekViewModel;

import java.util.Calendar;
import java.util.Locale;

/**
 * (View) Events of the week
 **/
public class DetailedEventFragment extends Fragment {
    FragmentDetailedEventBinding binding;
    private EditText editDate;
    private EditText editTime;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DetailedEventViewModel WeekViewModel =
                new ViewModelProvider(this).get(DetailedEventViewModel.class);
        binding = FragmentDetailedEventBinding.inflate(inflater, container, false);
        editDate = binding.editDate;
        editTime = binding.editTime;

        // Asigna el onClickListener para mostrar el DatePickerDialog
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        return binding.getRoot();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Formatear el año a dos dígitos
                        String formattedYear = String.valueOf(year % 100);
                        // Mostrar la fecha seleccionada en el EditText
                        editDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + formattedYear);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Aquí puedes hacer algo con la hora seleccionada, por ejemplo, mostrarla en el EditText
                        editTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute));
                    }
                }, hour, minute, false);
        timePickerDialog.show();
    }
}