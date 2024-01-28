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
import com.example.timemap.models.Event;
import com.example.timemap.models.EventList;

import java.util.Calendar;
import java.util.Locale;

/**
 * (View) Events of the week
 **/
public class DetailedEventFragment extends Fragment {
    FragmentDetailedEventBinding binding;
    private EditText editDate;
    private EditText editTime;
    private Calendar selectedDate;
    private Event event;
    private boolean eventCreationMode;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DetailedEventViewModel WeekViewModel =
                new ViewModelProvider(this).get(DetailedEventViewModel.class);
        binding = FragmentDetailedEventBinding.inflate(inflater, container, false);
        editDate = binding.editDate;
        editTime = binding.editTime;

        if (getArguments() != null) {
            Event event = (Event) getArguments().getSerializable("event");
            if (event != null) {
                loadEvent(event);
            }
        }

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

        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEnebled(true);
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saveEvent()) setEnebled(false);
            }
        });

        setEnebled(false);

        return binding.getRoot();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showDatePickerDialog() {
        selectedDate = Calendar.getInstance();
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);
        int day = selectedDate.get(Calendar.DAY_OF_MONTH);

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

    public void loadEvent(Event event) {
        if (event == null) return;
        this.event = event;
        selectedDate = event.getEndTimeAsCalendar();
        String formatYear = String.valueOf(event.getEndTime().getYear());
        binding.editTittle.setText(event.getName());
        binding.editFilter.setText(event.getFiltersAsString());
        binding.editDescription.setText(event.getDescription());
        binding.editDate.setText(event.getEndTime().getDay() + "/" + event.getEndTime().getMonth() + "/" + formatYear.substring(formatYear.length() - 2));
        binding.editTime.setText(event.getEndTime().getHour() + ":" + event.getEndTime().getMinute());
    }

    public void newEvent() {
        eventCreationMode = true;
        event = new Event();
    }

    public void editEvent() {
        if (event == null) newEvent();
        setEnebled(true);
    }

    public void setEnebled(boolean activo) {
        binding.editTittle.setEnabled(activo);
        binding.editFilter.setEnabled(activo);
        binding.editDescription.setEnabled(activo);
        binding.editDate.setEnabled(activo);
        binding.editTime.setEnabled(activo);
        setEditMode(activo);
    }

    public void setEditMode(boolean activo) {
        binding.btnEdit.setEnabled(!activo);
        binding.btnSave.setEnabled(activo);
    }

    public boolean validate() {
        return true;
    }

    public boolean saveEvent() {
        if (!validate()) return false;
        if (event == null) return false;
        event.setName(String.valueOf(binding.editTittle.getText()));
        event.setFilters(String.valueOf(binding.editFilter.getText()));
        event.setDescription(String.valueOf(binding.editDescription.getText()));
        event.setEndTime(selectedDate);
        if (eventCreationMode) EventList.getInstance().addEvent(event);
        else EventList.getInstance().editEvent(event);
        return true;
    }

}