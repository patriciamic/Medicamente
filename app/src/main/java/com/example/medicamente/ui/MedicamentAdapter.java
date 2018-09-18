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
import com.example.medicamente.entities.Medicament;

import java.util.ArrayList;
import java.util.List;

public class MedicamentAdapter extends RecyclerView.Adapter<MedicamentAdapter.MedicamentViewHolder> {

    private List<Medicament> medicaments;
    private OnMedicamentClickListener listener;


    public MedicamentAdapter(List<Medicament> medicaments) {
        this.medicaments = new ArrayList<>();
        this.medicaments.addAll(medicaments);
    }

    public MedicamentAdapter() {
        this.medicaments = new ArrayList<>();
    }


    public void setMedicaments(List<Medicament> medicaments) {
        this.medicaments.clear();
        this.medicaments.addAll(medicaments);
    }

    public List<Medicament> getMedicaments() {
        return medicaments;
    }

    public void setListener(OnMedicamentClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public MedicamentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_med, parent, false);

        return new MedicamentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicamentViewHolder holder, int position) {
        final Medicament medicament = medicaments.get(position);
        holder.tvTitle.setText(medicament.getName());
//        holder.tvTime.setText(medicament.getTime());

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {

                    listener.onMedicamentClick(medicament);

                }
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onMedicamentDeleteClick(medicament);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicaments.size();
    }


    public void addMedicament(Medicament medicament) {
        medicaments.add(medicament);
    }

    public void removeMedicament(Medicament medicament) {
        medicaments.remove(medicament);
    }

    class MedicamentViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvTime;
        private RelativeLayout container;
        private Button btnDelete;

        public MedicamentViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTime = itemView.findViewById(R.id.tv_time);
            container = itemView.findViewById(R.id.container);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }

    public Medicament getMedicamentById(String idMed){
        for(Medicament item: medicaments){
            if(item.getIdMed().equals(idMed)){
                return item;
            }
        }
     return null;
    }

    public void updateMedicament(Medicament medicament, String idMed){
        Medicament m = getMedicamentById(idMed);
        m.setName(medicament.getName());
//        m.setTime(medicament.getTime());
    }


    public interface OnMedicamentClickListener {
        void onMedicamentClick(Medicament medicament);

        void onMedicamentDeleteClick(Medicament medicament);
    }


}
