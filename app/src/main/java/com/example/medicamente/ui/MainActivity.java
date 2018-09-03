package com.example.medicamente.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.medicamente.R;
import com.example.medicamente.data.MedicamentsRepository;
import com.example.medicamente.entities.Medicament;

public class MainActivity extends AppCompatActivity implements MedicamentAdapter.OnMedicamentClickListener, View.OnClickListener {
    public static final int ADD_REQUEST_CODE = 123;
    private RecyclerView rvMedicaments;
    private MedicamentAdapter adapter;
    private MedicamentsRepository medicamentsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_add).setOnClickListener(this);
        medicamentsRepository = new MedicamentsRepository();
        setupRecycleView();
    }

    private void setupRecycleView() {
        rvMedicaments = findViewById(R.id.rv_notes);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvMedicaments.setLayoutManager(layoutManager);


        adapter = new MedicamentAdapter();
        adapter.setListener(this);
        rvMedicaments.setAdapter(adapter);
        adapter.setMedicaments(medicamentsRepository.getMedicaments());
    }

    @Override
    public void onMedeicamentClick(Medicament medicament) {

    }

    @Override
    public void onMedicamentDeleteClick(Medicament medicament) {
        medicamentsRepository.delete(medicament);
        adapter.removeMedicament(medicament);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                Intent intent = new Intent(this, MedicamentActivity.class);
                startActivityForResult(intent, ADD_REQUEST_CODE);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ADD_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String resultName = data.getStringExtra(MedicamentActivity.NAME);
                String resultTime = data.getStringExtra(MedicamentActivity.TIME);
                Medicament medicament = new Medicament(resultName, resultTime);
                medicamentsRepository.add(medicament);
                adapter.addMedicament(medicament);
                adapter.notifyDataSetChanged();

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}



