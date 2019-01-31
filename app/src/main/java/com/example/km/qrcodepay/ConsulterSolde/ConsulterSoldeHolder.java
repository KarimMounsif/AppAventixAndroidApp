package com.example.km.qrcodepay.ConsulterSolde;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.km.qrcodepay.BDD.Solde;
import com.example.km.qrcodepay.R;


/**
 * Created by thoma on 05/03/2018.
 */

public class ConsulterSoldeHolder extends RecyclerView.ViewHolder{


    private TextView soldeMois;
    private TextView soldeJour;


    public ConsulterSoldeHolder(View itemView) {
        super(itemView);

        soldeJour=itemView.findViewById(R.id.tvSoldeJourResult);
        soldeMois=itemView.findViewById(R.id.tvSoldeMoisResult);
    }

    public void bind(Solde solde){
        soldeJour.setText((int) solde.getSoldeJour());
        soldeMois.setText((int) solde.getSoldeMois());
    }
}
