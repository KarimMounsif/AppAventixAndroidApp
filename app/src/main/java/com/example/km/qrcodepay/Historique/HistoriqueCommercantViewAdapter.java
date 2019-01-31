package com.example.km.qrcodepay.Historique;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.km.qrcodepay.BDD.HistoriqueCommercant;
import com.example.km.qrcodepay.R;

import java.util.List;

/**
 * Created by TVE on 02/02/2018.
 */

public class HistoriqueCommercantViewAdapter extends RecyclerView.Adapter<HistoriqueCommercantViewAdapter.HistoriqueCommercantViewHolder> {

    private List<HistoriqueCommercant> listHistorique;

    public class HistoriqueCommercantViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTransaction, montantTransaction;
        public HistoriqueCommercantViewHolder(View view){
            super(view);
            dateTransaction = view.findViewById(R.id.tv_dateTransaction);
            montantTransaction = view.findViewById(R.id.tv_montantTransaction);
        }
    }

    public HistoriqueCommercantViewAdapter(List<HistoriqueCommercant> historiqueListe){
        this.listHistorique = historiqueListe;
    }

    @Override
    public HistoriqueCommercantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_historique_recycle_items_commercant, parent, false);

        return new HistoriqueCommercantViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HistoriqueCommercantViewHolder holder, int position) {
        HistoriqueCommercant historiqueTransaction =listHistorique.get(position);
        holder.montantTransaction.setText(historiqueTransaction.getMontantTransaction() + " €");
        holder.dateTransaction.setText(historiqueTransaction.getDateTransaction().replace(" ", "    |   ").substring(0,23));
    }

    @Override
    public int getItemCount() {

        return listHistorique.size();
    }
}
