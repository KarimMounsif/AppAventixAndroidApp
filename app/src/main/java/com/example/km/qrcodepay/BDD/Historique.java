package com.example.km.qrcodepay.BDD;

/**
 * Created by TVE on 02/02/2018.
 */

public class Historique {

    private String montantTransaction;
    private String dateTransaction;
    private String commerceTransaction;

    public Historique(String montantTransaction, String dateTransaction, String commerceTransaction) {
        this.montantTransaction = montantTransaction;
        this.dateTransaction = dateTransaction;
        this.commerceTransaction = commerceTransaction;
    }

    public Historique() {
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

    public String getCommerceTransaction() {
        return commerceTransaction;
    }

    public void setCommerceTransaction(String commerceTransaction) {
        this.commerceTransaction = commerceTransaction;
    }
}
