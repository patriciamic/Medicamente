package com.example.medicamente.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.medicamente.R;
import com.example.medicamente.entities.Boala;

import java.util.ArrayList;
import java.util.List;

public class BoalaAdapter extends RecyclerView.Adapter<BoalaAdapter.BoalaViewHolder> {

    private List<Boala> boalaList;
    private onBoalaClickListener listenerBoala;

    public BoalaAdapter(List<Boala> boalaList) {
        this.boalaList = new ArrayList<>();
        this.boalaList.addAll(boalaList);
    }

    public BoalaAdapter() {
        this.boalaList = new ArrayList<>();
    }

    public void setBoli(List<Boala> boalaList) {
        this.boalaList.clear();
        this.boalaList.addAll(boalaList);
    }

    public void setListenerBoala(onBoalaClickListener listenerBoala) {
        this.listenerBoala = listenerBoala;
    }

    @NonNull
    @Override
    public BoalaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_boala, parent, false);
        return new BoalaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BoalaViewHolder holder, int position) {
        final Boala boala = boalaList.get(position);
        holder.tvBoala.setText(boala.getNumeBoala());
        holder.rlBoliContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listenerBoala != null) {
                    listenerBoala.onBoalaClick(boala);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return boalaList.size();
    }


    public void addBoala(Boala boala) {
        boalaList.add(boala);
    }


    class BoalaViewHolder extends RecyclerView.ViewHolder {
        private TextView tvBoala;
        private RelativeLayout rlBoliContainer;

        public BoalaViewHolder(View itemView) {
            super(itemView);
            tvBoala = itemView.findViewById(R.id.tv_boala);
            rlBoliContainer = itemView.findViewById(R.id.rv_boala_container);
        }
    }


    public interface onBoalaClickListener {
        void onBoalaClick(Boala boala);
    }
}
