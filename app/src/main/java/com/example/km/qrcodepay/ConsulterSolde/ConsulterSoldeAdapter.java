package com.example.km.qrcodepay.ConsulterSolde;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.km.qrcodepay.BDD.Solde;
import com.example.km.qrcodepay.R;

import java.util.List;

/**
 * Created by thoma on 05/03/2018.
 */

public class ConsulterSoldeAdapter extends RecyclerView.Adapter<ConsulterSoldeHolder> {

    List<Solde>listSolde;

    public ConsulterSoldeAdapter(List<Solde>listSolde) {
        this.listSolde=listSolde;
    }

    @Override
    public ConsulterSoldeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.activity_consult_solde, parent, false);

        return new ConsulterSoldeHolder(view);
    }

    @Override
    public void onBindViewHolder(ConsulterSoldeHolder holder, int position) {
        Solde solde = listSolde.get(position);
        holder.bind(solde);
    }

    @Override
    public int getItemCount() {
        return listSolde.size();
    }
}
