package com.example.medicamente.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.medicamente.R;

import com.example.medicamente.data.Storage;
import com.example.medicamente.entities.Boala;
import static com.example.medicamente.data.Constants.*;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, BoalaAdapter.onBoalaClickListener, CustomDialogDeleteBoala.YesListener {
    private BoalaAdapter boalaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_add).setOnClickListener(this);
        setupRecycleView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        boalaAdapter.setBoli(Storage.getInstance().getBoli());
        boalaAdapter.notifyDataSetChanged();
    }

    private void setupRecycleView() {
        RecyclerView rvBoli = findViewById(R.id.rv_notes);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvBoli.setLayoutManager(layoutManager);
        boalaAdapter = new BoalaAdapter();
        boalaAdapter.setListenerBoala(this);
        rvBoli.setAdapter(boalaAdapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                Intent intent = new Intent(this, BoalaActivity.class);
                startActivity(intent);

                break;
        }
    }

    @Override
    public void onBoalaClick(Boala boala) {
        Intent intent = new Intent(this, BoalaActivity.class);
        intent.putExtra(ID_BOALA, boala.getId());
        startActivity(intent);
    }

    @Override
    public void onBoalaDelete(Boala boala) {

        CustomDialogDeleteBoala cddb = new CustomDialogDeleteBoala(this);
        cddb.setListener(this);
        cddb.setBoala(boala);
        cddb.show();

    }

    @Override
    public void onYesClicked(String result, Boala boala) {
        if(result.equals("yes")){
            Storage.getInstance().removeBoala(boala);
            boalaAdapter.removeBoala(boala);
            boalaAdapter.notifyDataSetChanged();
            Toast.makeText(this, boala.getNumeBoala(), Toast.LENGTH_SHORT).show();
        }
    }
}



