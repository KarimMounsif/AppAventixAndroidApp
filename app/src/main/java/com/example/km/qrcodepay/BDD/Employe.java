package com.example.km.qrcodepay.BDD;

/**
 * Created by TVE on 24/01/2018.
 */

public class Employe {

    private int idEmploye;
    private String nomEmploye;
    private String codeGenerateur;

    //CONSTRUCTEURS
    public Employe(){}

    public Employe (String nomEmploye, String codeGenerateur){
        this.nomEmploye=nomEmploye;
        this.codeGenerateur=codeGenerateur;

    }
    //FIN CONSTRUCTEURS

    //GETTER AND SETTER

    public int getId() {
        return idEmploye;
    }

    public void setId(int idEmploye) {
        this.idEmploye = idEmploye;
    }

    public String getNomEmploye() {
        return nomEmploye;
    }

    public void setNomEmploye(String nomEmploye) {
        this.nomEmploye = nomEmploye;
    }

    public String getCodeGenerateur() {

        return codeGenerateur;
    }

    public void setCodeGenerateur(String codeGenerateur) {
        this.codeGenerateur = codeGenerateur;
    }

    public String toString(){
        return "ID de l'employé : " + idEmploye + "\nNom de l'employé : " + nomEmploye +
                "\nCode générateur du QRcode : " + codeGenerateur;
    }
}
