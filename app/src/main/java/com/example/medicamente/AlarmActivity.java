package com.example.medicamente;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.medicamente.ui.BoalaActivity.MED_NAME;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvMedName;
    private String medName;
    private Button btnOk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        tvMedName = findViewById(R.id.tv_med_name_alarm);
        btnOk = findViewById(R.id.btn_ok_alarm);
        btnOk.setOnClickListener(this);
        Intent intent = getIntent();
        medName = intent.getStringExtra(MED_NAME);
        Log.e("ALAAARM:", medName);
        tvMedName.setText(medName);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_ok_alarm:
                Toast.makeText(this, "Medicament luat.", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}
