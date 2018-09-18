package com.example.medicamente.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.medicamente.R;

import java.util.ArrayList;
import java.util.List;

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder> {

    private List<String> hours;

    public HourAdapter(){
        hours = new ArrayList<>();
    }


    @NonNull
    @Override
    public HourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_hour, parent, false);
        return new HourViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HourViewHolder holder, int position) {
                final String hour = hours.get(position);
    }

    @Override
    public int getItemCount() {
        return hours.size();
    }

    class HourViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlHour;

        public HourViewHolder(View itemView) {
            super(itemView);
            rlHour = itemView.findViewById(R.id.rl_hours);
        }
    }


    public void addHour(String hour){
        hours.add(hour);
        Log.e("EEEEEEEEE", hour);
        this.notifyDataSetChanged();
    }
}
