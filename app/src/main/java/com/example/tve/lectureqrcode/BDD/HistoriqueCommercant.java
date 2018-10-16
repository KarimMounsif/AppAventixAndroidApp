package com.example.tve.lectureqrcode.BDD;

import java.util.Date;

/**
 * Created by TVE on 02/02/2018.
 */

public class HistoriqueCommercant {

    private String montantTransaction;
    private String dateTransaction;

    public HistoriqueCommercant(String dateTransaction, String montantTransaction) {
        this.montantTransaction = montantTransaction;
        this.dateTransaction = dateTransaction;
    }

    public HistoriqueCommercant() {
    }

    public String getMontantTransaction() {
        return montantTransaction;
    }

    public void setMontantTransaction(String montantTransaction) {
        this.montantTransaction = montantTransaction;
    }

    public String getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(String dateTransaction) {
        this.dateTransaction = dateTransaction;
    }
}
