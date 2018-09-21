package com.example.medicamente.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.medicamente.R;
import com.example.medicamente.entities.Boala;

public class CustomDialogDeleteBoala extends Dialog implements View.OnClickListener {
    private Button btnDa;
    private Button btnNu;
    private YesListener listener;
    private Boala boala;


    public CustomDialogDeleteBoala(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_delete_boala);

        btnDa = findViewById(R.id.btn_da_delete_boala);
        btnNu = findViewById(R.id.btn_nu_delete_boala);
        btnDa.setOnClickListener(this);
        btnNu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_da_delete_boala:
                listener.onYesClicked("yes", this.boala);
                dismiss();
                break;
            case R.id.btn_nu_delete_boala:
                dismiss();
                break;

        }
    }

    public void setListener(YesListener listener) {
        this.listener = listener;
    }

    public void setBoala(Boala boala) {
        this.boala = boala;
    }

    public interface YesListener{
        void onYesClicked(String result,Boala boala);
    }
}
