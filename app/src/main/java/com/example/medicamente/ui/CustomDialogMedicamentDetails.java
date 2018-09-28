package com.example.medicamente.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.medicamente.AlarmBroadcastReceiver;
import com.example.medicamente.R;
import com.example.medicamente.entities.Hour;
import com.example.medicamente.entities.Medicament;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CustomDialogMedicamentDetails extends Dialog implements AdapterView.OnItemSelectedListener, View.OnClickListener, HourAdapter.OnHourClickListener {
    private Spinner spinner;
    private EditText etMedicamentName;
    private EditText etNumarZile;
    private RadioGroup rgDurata;
    private RadioButton rbContinuu;
    private RadioButton rbNrZile;
    private HourAdapter hourAdapter;
    private MedSaveListener listener;
    private Medicament medicament;
    private Mode mode;
    private int intervalZi;
    private Activity activity;
    private String timeSelected;
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView tvStartDate;
    private boolean listmode = true;

    public CustomDialogMedicamentDetails(Context context, Medicament medicament) {
        super(context);
        this.activity = (Activity) context;
        mode = medicament == null ? Mode.SAVE : Mode.UPDATE;
        this.medicament = medicament;
    }

    public void setListener(MedSaveListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        listmode = true;
        setupViews();
        setDateListener();

    }

    private void setCurrentDate() {
        String dateSaved = getCurrentDate();
        tvStartDate.setText(dateSaved);

    }

    @NonNull
    private String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day + "/ " + month + " /" + year;
    }

    private void setDateListener() {
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String dateSaved = day + "/ " + month + " /" + year;
                tvStartDate.setText(dateSaved);
                // medicament.setDate(dateSaved);

            }
        };
    }

    private void setupViews() {
        etMedicamentName = findViewById(R.id.et_medicament_name);
        etNumarZile = findViewById(R.id.et_numar_zile);
        etNumarZile.setVisibility(View.INVISIBLE);
        rgDurata = findViewById(R.id.rg_durata);
        rbContinuu = findViewById(R.id.rb_continuu);
        rbContinuu.setOnClickListener(this);
        rbNrZile = findViewById(R.id.rb_numar_zile);
        rbNrZile.setOnClickListener(this);
        tvStartDate = findViewById(R.id.tv_start_date);
        tvStartDate.setOnClickListener(this);

        setupSpinner();
        setupRvHours();

        findViewById(R.id.btn_save_med_dialog).setOnClickListener(this);

        if (medicament != null) {
            etMedicamentName.setText(medicament.getName());
            hourAdapter.setList(medicament.getHours());
        }

        switch (mode) {
            case UPDATE:
                setViewOnUpdate();
                break;
            case SAVE:
                setCurrentDate();
                break;


        }

    }

    private void setViewOnUpdate() {
        listmode = false;
        spinner.setSelection(medicament.getIntervalZi() - 1);

        hourAdapter.setList(medicament.getHours());
        hourAdapter.notifyDataSetChanged();
        if (medicament.getNrZile() != 0) {
            rbNrZile.setChecked(true);
            etNumarZile.setVisibility(View.VISIBLE);
            etNumarZile.setText(medicament.getNrZile() + "");

        } else {
            rbContinuu.setChecked(true);
        }
        tvStartDate.setText(medicament.getDate());

    }


    private void setupSpinner() {
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        List<String> categories = new ArrayList<>();
        categories.add("o data pe zi");
        for (int i = 2; i < 13; i++) {
            categories.add("de " + i + " ori pe zi");
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    private void setupRvHours() {
        RecyclerView rvHours = findViewById(R.id.rv_hours);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvHours.setLayoutManager(layoutManager);

        hourAdapter = new HourAdapter();
        rvHours.setAdapter(hourAdapter);
        hourAdapter.setListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        intervalZi = i + 1;
        if (listmode) {
            setIntervalOfHours(i);
        }

        if (!listmode) {
            listmode = true;
        }

        if (medicament == null) {
            medicament = new Medicament(etMedicamentName.getText().toString());
            medicament.setDate(getCurrentDate());
        }

        hourAdapter.notifyDataSetChanged();

    }

    private void setIntervalOfHours(int i) {

        hourAdapter.clearList();
        int idHour = 0;
        int inceput = 6;
        int interval = 24 / (i + 1);
        if (i == 0) {
            hourAdapter.addHour(new Hour(inceput + ":00", idHour + ""));
        } else {

            for (int j = 1; j < i + 2; j++) {
                idHour++;
                if (j == 1) {
                    hourAdapter.addHour(new Hour(inceput + ":00", idHour + ""));
                } else {
                    inceput += interval;
                    if (inceput > 24) {
                        inceput = 0;
                    }
                    hourAdapter.addHour(new Hour(inceput + ":00", idHour + ""));
                }

            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save_med_dialog:
                onSaveClicked();
                break;
            case R.id.rb_continuu:
                setFirstRadioButton(rbContinuu.isChecked(), View.INVISIBLE);
                break;
            case R.id.rb_numar_zile:
                setSecondRadioButton(rbNrZile.isChecked(), View.VISIBLE);
                break;
            case R.id.tv_start_date:
                onDateClick();
                break;

        }

    }

    private void setSecondRadioButton(boolean checked, int visible) {
        if (checked) {
            etNumarZile.setVisibility(visible);
        }
    }

    private void setFirstRadioButton(boolean checked, int invisible) {
        setSecondRadioButton(checked, invisible);
    }

    private void onDateClick() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }

    private void onSaveClicked() {
        if (listener != null) {
            String name = etMedicamentName.getText().toString();
            if (checkEmptyString(name)) {
                notifyListener(name);
                dismiss();
            }

        }

    }

    private void notifyListener(String name) {
        if (checkEmptyString(name)) {
            if (medicament == null) {
                medicament = new Medicament(name);
            } else {
                medicament.setName(name);
                medicament.setHours(hourAdapter.getList());
                medicament.setIntervalZi(intervalZi);
                String zile = etNumarZile.getText().toString();
                if (!zile.equals("")) {
                    int nrZile = Integer.parseInt(zile);
                    medicament.setNrZile(nrZile);
                } else {
                    medicament.setNrZile(0);
                }
            }
            switch (mode) {
                case SAVE:
                    saveNewMed();
                    break;
                case UPDATE:
                    updateMed();
                    break;
            }
        }
    }

    private void updateMed() {
        medicament.setHours(hourAdapter.getList());
        listener.onMedUpdated(medicament);

    }

    private void saveNewMed() {
        medicament.setHours(hourAdapter.getList());
        //logHourAdapterList();
        listener.onMedSaved(medicament);
    }

    private void logHourAdapterList() {
        for (Hour hour : hourAdapter.getList()) {
            Log.e("AAAAAAAAAAAA", hour.getNume());
        }
    }

    private boolean checkEmptyString(String name) {
        if (name.equals("")) {
            Toast.makeText(getContext(), "Nu ati introdus numele MEDICAMENTULUI", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    public void onHourClicked(Hour hour) {
        Toast.makeText(activity, hour.getId() + " clicked", Toast.LENGTH_SHORT).show();
        onTimeClicked(hour);
    }


    public interface MedSaveListener {
        void onMedSaved(Medicament medicament);

        void onMedUpdated(Medicament medicament);

    }

    private enum Mode {
        SAVE,
        UPDATE
    }

    private void onTimeClicked(Hour myHour) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker = new TimePickerDialog(
                getContext(), (timePicker, selectedHour, selectedMinute) -> onTimeSelected(timePicker, selectedHour, selectedMinute, myHour), hour, minute, true);

        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void onTimeSelected(TimePicker timePicker, int selectedHour, int selectedMinute, Hour myHour) {
        timeSelected = selectedHour + ":" + selectedMinute;
        myHour.setNume(timeSelected);
        hourAdapter.updateHour(myHour);
        hourAdapter.notifyDataSetChanged();
        Calendar calendar = getCalendar(timePicker, selectedHour, selectedMinute);
        // setAlarm(calendar.getTimeInMillis());
    }

    @NonNull
    private Calendar getCalendar(TimePicker timePicker, int selectedHour, int selectedMinute) {
        Calendar calendar = Calendar.getInstance();
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                    timePicker.getHour(), timePicker.getMinute(), 0);
        } else {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                    selectedHour, selectedMinute, 0);
        }
        return calendar;
    }


}
