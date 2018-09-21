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
import com.example.medicamente.entities.Hour;

import java.util.ArrayList;
import java.util.List;

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder> {

    private List<Hour> hours;

    private OnHourClickListener listener;

    public HourAdapter() {
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
        final Hour hour = hours.get(position);
        holder.tvHour.setText(hour.getNume());
        holder.rlHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onHourClicked(hour);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return hours.size();
    }

    public void setList(List<Hour> list) {
        this.hours = list;
    }

    public void setListener(OnHourClickListener listener) {
        this.listener = listener;
    }

    class HourViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlHour;
        private TextView tvHour;

        public HourViewHolder(View itemView) {
            super(itemView);
            rlHour = itemView.findViewById(R.id.rl_hours);
            tvHour = itemView.findViewById(R.id.tv_hour);
        }
    }


    public void addHour(Hour hour) {
        hours.add(hour);
        this.notifyDataSetChanged();
    }

    public void clearList(){
        hours.clear();
    }

    public List<Hour> getList(){
        return hours;
    }




    public interface OnHourClickListener{
        void onHourClicked(Hour hour);
    }



}
