package com.example.timemap.ui.detailedEvent;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.timemap.MainActivity;
import com.example.timemap.R;
import com.example.timemap.controller.UserController;
import com.example.timemap.databinding.FragmentDetailedEventBinding;
import com.example.timemap.model.CustomDateTime;
import com.example.timemap.model.Event;
import com.example.timemap.model.EventList;
import com.example.timemap.utils.ConfirmationDialog;
import com.google.android.material.appbar.AppBarLayout;

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
    private NavController navController;
    ImageButton btnAdd;

    /**
    * onCreateView:
     * Inflates the fragment layout, initializes UI elements, and sets up event handling.
     It returns the root view of the fragment.
    * */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (MainActivity.instance != null) MainActivity.instance.creatingEvent = true;
        DetailedEventViewModel WeekViewModel =
                new ViewModelProvider(this).get(DetailedEventViewModel.class);
        binding = FragmentDetailedEventBinding.inflate(inflater, container, false);
        editDate = binding.editDate;
        editTime = binding.editTime;
        Button btnDelete = binding.getRoot().findViewById(R.id.btnDelete);
        eventCreationMode = true;

        btnAdd = getActivity().findViewById(R.id.action_add);
        navController = NavHostFragment.findNavController(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verificar si se está creando un nuevo evento desde cero
                if (event == null || event.getEventId() == 0) {
                    // Comprobar si se han introducido datos
                    if (!todosLosCamposEstanVacios()) {
                        // Mostrar el diálogo de confirmación
                        ConfirmationDialog.askForConfirmation(requireContext(), "¿Estás seguro de que deseas borrar y crear un nuevo evento?", new ConfirmationDialog.ConfirmationCallback() {
                            @Override
                            public void onConfirmation(boolean confirmed) {
                                if (confirmed) {
                                    // Si el usuario confirma, restablecer el formulario
                                    resetForm();
                                }
                            }
                        });
                    }
                } else { // El usuario está editando un evento existente
                    // Comprobar si los campos son iguales al evento actual
                    if (camposIgualAlEvento()) {
                        // Si los campos son iguales, restablecer el formulario
                        resetForm();
                    } else {
                        // Mostrar el diálogo de confirmación
                        ConfirmationDialog.askForConfirmation(requireContext(), "¿Estás seguro de que deseas perder los cambios y crear un nuevo evento?", new ConfirmationDialog.ConfirmationCallback() {
                            @Override
                            public void onConfirmation(boolean confirmed) {
                                if (confirmed) {
                                    // Si el usuario confirma, restablecer el formulario
                                    resetForm();
                                }
                            }
                        });
                    }
                }
            }
        });




        if (getArguments() != null) {
            Event event = (Event) getArguments().getSerializable("event");
            if (event != null) {
                eventCreationMode = false;
                loadEvent(event);
            }
        }




        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para eliminar el evento
                deleteEvent();
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

        if (eventCreationMode) {
            event = new Event();
            setEnebled(true);
        } else {
            setEnebled(false);
        }

        return binding.getRoot();
    }

    /**
     * Cleans up resources when the fragment's view is destroyed.
     * */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Displays a DatePickerDialog to pick a date.
     * */
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
                        selectedDate.set(Calendar.YEAR, year);
                        selectedDate.set(Calendar.MONTH, monthOfYear);
                        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    /**
     * Displays a TimePickerDialog to pick a time.
     * */
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
                        selectedDate.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        selectedDate.set(Calendar.MINUTE,minute);
                    }
                }, hour, minute, false);
        timePickerDialog.show();
    }
/**
 * Loads event data into UI elements for editing.
 * */
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
/**
 * Enables or disables UI elements based on the provided parameter.
 * */
    public void setEnebled(boolean activo) {
        binding.editTittle.setEnabled(activo);
        binding.editFilter.setEnabled(activo);
        binding.editDescription.setEnabled(activo);
        binding.editDate.setEnabled(activo);
        binding.editTime.setEnabled(activo);
        setEditMode(activo);
    }

    /**
     *Enables or disables edit mode for UI elements based on the provided parameter.
     * */
    public void setEditMode(boolean activo) {
        binding.btnEdit.setEnabled(!activo);
        binding.btnSave.setEnabled(activo);
    }

    /**
     * Validates user input fields.
     * */
    public boolean validate() {
        return !(binding.editTittle.getText().toString().isEmpty() ||
                binding.editFilter.getText().toString().isEmpty() ||
                binding.editDescription.getText().toString().isEmpty() ||
                selectedDate==null);

    }

    /**
     * Saves the event data entered by the user.
     * */
    public boolean saveEvent() {
        if (!validate()) return false;
        if (event == null) return false;
        event.setName(String.valueOf(binding.editTittle.getText()));
        event.setFilters(String.valueOf(binding.editFilter.getText()));
        event.setDescription(String.valueOf(binding.editDescription.getText()));
        event.setEndTime(selectedDate);
        event.setUser(UserController.getInstance().getCurrentUser());
        if (eventCreationMode) {
            return EventList.getInstance().addEvent(event);
        } else {
            return EventList.getInstance().editEvent(event);
        }
    }

    /**
     * Releases resources and handles actions when the fragment is detached from its hosting activity.
     * */
    @Override
    public void onDetach() {
        super.onDetach();
        if (MainActivity.instance != null) MainActivity.instance.creatingEvent = false;
        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MainActivity.instance.clickNewEvent();
            }
        });



    }

    /**
     * Displays a confirmation dialog and deletes the event if confirmed.
     * */
    private void deleteEvent() {
        if (event != null && event.getEventId()!=0) {
            ConfirmationDialog.askForConfirmation(requireContext(), "¿Estás seguro de que deseas eliminar este evento?", new ConfirmationDialog.ConfirmationCallback() {
                @Override
                public void onConfirmation(boolean confirmed) {
                    if (confirmed) {
                        boolean eventDeleted = EventList.getInstance().removeEvent(event);
                        if (eventDeleted) {
                            // Si el evento se eliminó correctamente, puedes cerrar el fragmento o realizar otras acciones necesarias
                            requireActivity().getSupportFragmentManager().popBackStack();
                        } else {
                            // Si no se pudo eliminar el evento, puedes mostrar un mensaje de error o realizar otras acciones necesarias
                            Toast.makeText(requireContext(), "Error al eliminar el evento", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

    /**
     * Checks if all input fields are empty.
     * */
    public boolean todosLosCamposEstanVacios() {
        String title = binding.editTittle.getText().toString().trim();
        String filters = binding.editFilter.getText().toString().trim();
        String description = binding.editDescription.getText().toString().trim();
        String date = binding.editDate.getText().toString().trim();
        String time = binding.editTime.getText().toString().trim();

        return title.isEmpty() && filters.isEmpty() && description.isEmpty() && date.isEmpty() && time.isEmpty();
    }


    /**
     * Compares input fields with the current event to check if they are equal.
     * */
    public boolean camposIgualAlEvento(){

        String descriptionText = binding.editDescription.getText().toString();
        String filterText = binding.editFilter.getText().toString();

        return event.getName().equals(binding.editTittle.getText().toString()) &&
                event.getDescription().equals(descriptionText) &&
                event.getFiltersAsString().equals(filterText) &&
                event.getEndTimeAsCalendar().equals(selectedDate);

    }
    /**
     *  Resets the form to its initial state.
     * */
    public void resetForm(){
        setEditMode(true);
        setEnebled(true);
        eventCreationMode=true;
        binding.editTittle.setText("");
        binding.editFilter.setText("");
        binding.editDescription.setText("");
        binding.editDate.setText("");
        binding.editTime.setText("");
        selectedDate = Calendar.getInstance();
        event=new Event();
    }


}