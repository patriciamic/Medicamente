package com.example.medicamente.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.medicamente.R;
import com.example.medicamente.data.MedicamentsRepository;
import com.example.medicamente.data.Storage;
import com.example.medicamente.entities.Boala;
import com.example.medicamente.entities.Medicament;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.medicamente.data.Constants.ID_BOALA;

public class BoalaActivity extends AppCompatActivity implements View.OnClickListener, MedicamentAdapter.OnMedicamentClickListener, AdapterView.OnItemSelectedListener {

    private int idMedicament;
    private Button save;
    private Button update;
    private FloatingActionButton addMed;
    private EditText etNumeBoala;
    public Boala boala;
    private RecyclerView rvMeds;
    private RecyclerView rvHours;
    private MedicamentAdapter medicamentAdapter;
    private MedicamentsRepository medicamentsRepository;
    private HourAdapter hourAdapter;
    public static JSONObject obj;

    private Spinner spinner;


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
        addMed = findViewById(R.id.fab_add_med);
        addMed.setOnClickListener(this);
        etNumeBoala = findViewById(R.id.et_nume_boala);


        setRecyclerView();
    }

    private void setRecyclerView() {
        medicamentsRepository = new MedicamentsRepository();
        rvMeds = findViewById(R.id.rv_meds);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvMeds.setLayoutManager(layoutManager);
        medicamentAdapter = new MedicamentAdapter();
        medicamentAdapter.setListener(this);
        medicamentAdapter.setMedicaments(medicamentsRepository.getMedicaments());

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
                openMedDialog();
                break;
        }
    }

    void openMedDialog() {
        CustomDialogClass cdd = getCustomDialogClass();
        saveFromDialog(cdd);

        rvHours = cdd.findViewById(R.id.rv_hours);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvHours.setLayoutManager(layoutManager);

        hourAdapter = new HourAdapter();
        rvHours.setAdapter(hourAdapter);

        spinner = cdd.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);



        List<String> categories = new ArrayList<>();
        categories.add("o data pe zi");
        for (int i = 2; i < 13; i++) {
            categories.add("de " + i + " ori pe zi");
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


    }

    @NonNull
    private CustomDialogClass getCustomDialogClass() {
        CustomDialogClass cdd = new CustomDialogClass(this);
        cdd.show();

        return cdd;
    }

    private void saveFromDialog(CustomDialogClass cdd) {
        Button saveButton = cdd.findViewById(R.id.btn_saveMedDialog);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String name = ((EditText) cdd.findViewById(R.id.et_medicamentDialog)).getText().toString();
                if (checkEmptyString(name)) {
                    Medicament medicament = new Medicament(name);
                    newMedToBoalaAndAdapter(medicament);
                    cdd.dismiss();
                }
            }
        });
    }

    private boolean checkEmptyString(String name) {
        if (name.equals("")) {
            Toast.makeText(this, "Nu ati introdus numele MEDICAMENTULUI", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void newMedToBoalaAndAdapter(Medicament medicament) {
        idMedicament++;
        Log.e("EEEEEEEEE", idMedicament + "");
        medicament.setIdMed(idMedicament + "");
        boala.addMedicament(medicament);
        medicamentAdapter.addMedicament(medicament);
        medicamentAdapter.notifyDataSetChanged();
    }

    private void updateBoala() {
        String numeBoala = etNumeBoala.getText().toString();
        boala.setNumeBoala(numeBoala);
        boala.setMedicamentList(medicamentAdapter.getMedicaments());
        Storage.getInstance().updateBoala(boala);
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
        finish();
    }


    void saveFromUpdateMed(Medicament medicament, CustomDialogClass cdc) {
        EditText etNume = (EditText) cdc.findViewById(R.id.et_medicamentDialog);
        etNume.setText(medicament.getName());

        Button saveButton = cdc.findViewById(R.id.btn_saveMedDialog);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String newName = ((EditText) cdc.findViewById(R.id.et_medicamentDialog)).getText().toString();
                if (checkEmptyString(newName)) {
                    medicament.setName(newName);
                    Log.e("EEEEEEEE", medicament.getIdMed());
                    medicamentAdapter.updateMedicament(medicament, medicament.getIdMed());
                    medicamentAdapter.notifyDataSetChanged();
                    cdc.dismiss();
                }
            }
        });
    }

    @Override
    public void onMedicamentClick(Medicament medicament) {
        CustomDialogClass cdc = getCustomDialogClass();
        saveFromUpdateMed(medicament, cdc);
    }


    @Override
    public void onMedicamentDeleteClick(Medicament medicament) {
        boala.removeMedicament(medicament);
        medicamentAdapter.removeMedicament(medicament);
        medicamentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(this, "ASD", Toast.LENGTH_LONG).show();
        hourAdapter.addHour(item);
        hourAdapter.notifyDataSetChanged();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
