package com.example.km.qrcodepay.BDD;

/**
 * Created by thoma on 05/03/2018.
 */

public class Solde {

    private float soldeMois;
    private float soldeJour;

    public Solde(float soldeMois, float soldeJour) {
        this.soldeMois = soldeMois;
        this.soldeJour = soldeJour;
    }

    public float getSoldeMois() {
        return soldeMois;
    }

    public void setSoldeMois(int soldeMois) {
        this.soldeMois = soldeMois;
    }

    public float getSoldeJour() {
        return soldeJour;
    }

    public void setSoldeJour(int soldeJour) {
        this.soldeJour = soldeJour;
    }

}
