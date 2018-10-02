package com.example.medicamente.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.medicamente.AlarmBroadcastReceiver;
import com.example.medicamente.R;
import com.example.medicamente.data.Storage;
import com.example.medicamente.entities.Boala;
import com.example.medicamente.entities.Hour;
import com.example.medicamente.entities.Medicament;


import java.util.Calendar;
import java.util.List;

import static com.example.medicamente.data.Constants.ID_BOALA;

public class BoalaActivity extends AppCompatActivity implements View.OnClickListener, MedicamentAdapter.OnMedicamentClickListener, CustomDialogMedicamentDetails.MedSaveListener {

    public static final String MED_ID = "medId";
    public static final String BOALA_ID = "boalaId";
    public static final String MED_NAME = "medName";
    private Button save;
    private Button update;
    private EditText etNumeBoala;

    private MedicamentAdapter medicamentAdapter;

    private Boala boala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boala);

        setupViews();

        setMode();
    }

    private void setupViews() {
        save = findViewById(R.id.btn_save);
        save.setOnClickListener(this);

        update = findViewById(R.id.btn_update);
        update.setOnClickListener(this);

        FloatingActionButton addMed = findViewById(R.id.fab_add_med);
        addMed.setOnClickListener(this);
        etNumeBoala = findViewById(R.id.et_nume_boala);

        setRecyclerView();
    }

    private void setRecyclerView() {
        RecyclerView rvMeds = findViewById(R.id.rv_meds);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvMeds.setLayoutManager(layoutManager);
        medicamentAdapter = new MedicamentAdapter();
        medicamentAdapter.setListener(this);

        rvMeds.setAdapter(medicamentAdapter);
    }


    private void setMode() {
        Intent intent = getIntent();
        if (intent.hasExtra(ID_BOALA)) {
            String idBoala = intent.getStringExtra(ID_BOALA);
            boala = Storage.getInstance().getBoala(idBoala);
            medicamentAdapter.setMedicaments(boala.getMedicamentList());
            medicamentAdapter.notifyDataSetChanged();
            etNumeBoala.setText(boala.getNumeBoala());
            save.setVisibility(View.GONE);
        } else {
            boala = new Boala();
            update.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                addNewBoala();
                break;
            case R.id.btn_update:
                updateBoala();
                break;
            case R.id.fab_add_med:
                openMedDialog(null);
                break;
        }
    }

    private CustomDialogMedicamentDetails openMedDialog(Medicament medicament) {
        CustomDialogMedicamentDetails cdd = new CustomDialogMedicamentDetails(this, medicament);
        cdd.setListener(this);
        cdd.show();

        return cdd;
    }

    private void addNewMed(Medicament medicament) {
//        idMedicament++;
//        medicament.setIdMed(idMedicament + "");
        boala.addMedicament(medicament);
        medicamentAdapter.addMedicament(medicament);
        medicamentAdapter.notifyDataSetChanged();
    }

    private void updateBoala() {
        String numeBoala = etNumeBoala.getText().toString();
        boala.setNumeBoala(numeBoala);
        boala.setMedicamentList(medicamentAdapter.getMedicaments());
        Storage.getInstance().updateBoala(boala);

        setupAlarm();

        finish();
    }

    private void addNewBoala() {
        String numeBoala = etNumeBoala.getText().toString();
        if (numeBoala.equals("")) {
            Toast.makeText(this, "Nu ati introdus numele BOLII", Toast.LENGTH_SHORT).show();
            return;
        }
        boala.setMedicamentList(medicamentAdapter.getMedicaments());
        boala.setNumeBoala(numeBoala);
        Storage.getInstance().addBoala(boala);

        setupAlarm();

        finish();
    }

    private void setupAlarm() {
        List<Medicament> meds = boala.getMedicamentList();
        for (Medicament med : meds) {
            List<Hour> hours = med.getHours();
            for (Hour itemHour : hours) {
                setAlarm(itemHour, med, boala);
            }
        }
    }

    private void setAlarm(Hour hour, Medicament med, Boala boala) {
        //Toast.makeText(this, "e aici", Toast.LENGTH_SHORT).show();

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent i = new Intent(this, AlarmBroadcastReceiver.class);
        i.putExtra(BOALA_ID, boala.getId());
        i.putExtra(MED_ID, med.getIdMed());

        final int _id = (int) System.currentTimeMillis();
        PendingIntent pi = PendingIntent.getBroadcast(this, _id, i, PendingIntent.FLAG_ONE_SHOT);


        if (am != null) {
            String hourString = hour.getNume();
            String[] parts = hourString.split(":");
            int timeHour = Integer.parseInt(parts[0]);
            int timeMinute = Integer.parseInt(parts[1]);
            Calendar calendar = Calendar.getInstance();

            Calendar setcalendar = Calendar.getInstance();
            setcalendar.set(Calendar.HOUR_OF_DAY, timeHour);
            setcalendar.set(Calendar.MINUTE, timeMinute);
            setcalendar.set(Calendar.SECOND, 0);

            if (setcalendar.before(calendar))
                setcalendar.add(Calendar.DATE, 1);

            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, setcalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
        }
    }


    @Override
    public void onMedicamentClick(Medicament medicament) {
        openMedDialog(medicament);
    }


    @Override
    public void onMedicamentDeleteClick(Medicament medicament) {
        boala.removeMedicament(medicament);
        medicamentAdapter.removeMedicament(medicament);
        medicamentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMedSaved(Medicament medicament) {
        addNewMed(medicament);

    }

    @Override
    public void onMedUpdated(Medicament medicament) {
        medicamentAdapter.updateMedicament(medicament, medicament.getIdMed());
        medicamentAdapter.notifyDataSetChanged();
    }
}
