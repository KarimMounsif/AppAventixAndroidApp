package com.example.km.qrcodepay.Historique;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.km.qrcodepay.BDD.Historique;
import com.example.km.qrcodepay.R;

import java.util.List;

/**
 * Created by TVE on 02/02/2018.
 */

public class HistoriqueViewAdapter extends RecyclerView.Adapter<HistoriqueViewAdapter.HistoriqueViewHolder> {

    private List<Historique> listHistorique;

    public class HistoriqueViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTransaction, montantTransaction, nomCommerceTransaction;

        public HistoriqueViewHolder(View view){
            super(view);
            dateTransaction = view.findViewById(R.id.tv_dateTransaction);
            montantTransaction = view.findViewById(R.id.tv_montantTransaction);
            nomCommerceTransaction =  view.findViewById(R.id.tv_nomCommerceTransaction);
        }
    }

    public HistoriqueViewAdapter(List<Historique> historiqueListe){
        this.listHistorique = historiqueListe;
    }

    @Override
    public HistoriqueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_historique_recycle_items, parent, false);

        return new HistoriqueViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HistoriqueViewHolder holder, int position) {
        Historique historiqueAchat =listHistorique.get(position);
        holder.montantTransaction.setText(historiqueAchat.getMontantTransaction() + " €");
        holder.dateTransaction.setText(historiqueAchat.getDateTransaction().replace(" ", "    |   ").substring(0,23));
        holder.nomCommerceTransaction.setText(historiqueAchat.getCommerceTransaction());
    }

    @Override
    public int getItemCount() {

        return listHistorique.size();
    }
}
