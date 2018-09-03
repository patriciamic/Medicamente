package com.example.medicamente.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.medicamente.AlarmBroadcastReceiver;
import com.example.medicamente.R;

import java.util.Calendar;

public class MedicamentActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String NAME = "name";
    public static final String TIME = "time";
    private EditText etName;
    private TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicament);
        findViewById(R.id.btn_save).setOnClickListener(this);
        tvTime = findViewById(R.id.tv_time);
        etName = findViewById(R.id.et_name);
        tvTime.setOnClickListener(this);
    }

    private void setAlarm(long time) {

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent i = new Intent(this, AlarmBroadcastReceiver.class);

        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);

        if (am != null) {
            am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                onSaveClicked();
                break;
            case R.id.tv_time:
                onTimeClicked();
                break;
        }
    }

    private void onTimeClicked() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker = new TimePickerDialog(
                MedicamentActivity.this,
                this::onTimeSelected,
                hour, minute, true);

        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void onTimeSelected(TimePicker timePicker, int selectedHour, int selectedMinute) {
        tvTime.setText(selectedHour + ":" + selectedMinute);
        Calendar calendar = getCalendar(timePicker, selectedHour, selectedMinute);
        setAlarm(calendar.getTimeInMillis());
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

    private void onSaveClicked() {
        String name = etName.getText().toString();
        String time = tvTime.getText().toString();
        Intent returnIntent = new Intent();
        returnIntent.putExtra(NAME, name);
        returnIntent.putExtra(TIME, time);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
