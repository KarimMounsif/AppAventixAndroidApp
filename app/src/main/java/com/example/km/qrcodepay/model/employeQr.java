package com.example.km.qrcodepay.model;

/**
 * Created by thoma on 09/03/2018.
 */

public class employeQr {
    //Qrcode
    private int statusQrCode;
    private static String numeroCode;

    //Employe
    private static String idEmploye;
    private static String nomEmploye;
    private static String prenomEmploye;
    private static String civiliteEmploye;
    private String ddnEmploye;
    private String typeEmploye;
    private String telEmploye;
    private String mailEmploye;
    private float soldeEmploye;
    private String mdpEmploye;
    private int statusEmploye;
    private String dateCreationCompteEmploye;
    private String dateDerniereConnexionEmploye;
    private String idEntreprise;

    public employeQr(){

    }

    public employeQr(int statusQrCode, String numeroCode, String idEmploye, String nomEmploye, String prenomEmploye,
                     String civiliteEmploye, String ddnEmploye, String typeEmploye, String telEmploye, String mailEmploye, float soldeEmploye,
                     String mdpEmploye, int statusEmploye, String dateCreationCompteEmploye, String dateDerniereConnexionEmploye, String idEntreprise) {

        this.statusQrCode = statusQrCode;
        this.numeroCode = numeroCode;
        this.idEmploye = idEmploye;
        this.nomEmploye = nomEmploye;
        this.prenomEmploye = prenomEmploye;
        this.civiliteEmploye = civiliteEmploye;
        this.ddnEmploye = ddnEmploye;
        this.typeEmploye = typeEmploye;
        this.telEmploye = telEmploye;
        this.mailEmploye = mailEmploye;
        this.soldeEmploye = soldeEmploye;
        this.mdpEmploye = mdpEmploye;
        this.statusEmploye = statusEmploye;
        this.dateCreationCompteEmploye = dateCreationCompteEmploye;
        this.dateDerniereConnexionEmploye = dateDerniereConnexionEmploye;
        this.idEntreprise = idEntreprise;
    }

    public static String getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(String idEmploye) {
        this.idEmploye = idEmploye;
    }

    public int getStatusQrCode() {
        return statusQrCode;
    }

    public void setStatusQrCode(int statusQrCode) {
        this.statusQrCode = statusQrCode;
    }

    public static String getNumeroCode() {
        return numeroCode;
    }

    public void setNumeroCode(String numeroCode) {
        this.numeroCode = numeroCode;
    }
    public static String getNomEmploye() {
        return nomEmploye;
    }

    public void setNomEmploye(String nomEmploye) {
        this.nomEmploye = nomEmploye;
    }

    public static String getPrenomEmploye() {
        return prenomEmploye;
    }

    public void setPrenomEmploye(String prenomEmploye) {
        this.prenomEmploye = prenomEmploye;
    }

    public static String getCiviliteEmploye() {
        return civiliteEmploye;
    }

    public void setCiviliteEmploye(String civiliteEmploye) {
        this.civiliteEmploye = civiliteEmploye;
    }

    public String getDdnEmploye() {
        return ddnEmploye;
    }

    public void setDdnEmploye(String ddnEmploye) {
        this.ddnEmploye = ddnEmploye;
    }

    public String getTypeEmploye() {
        return typeEmploye;
    }

    public void setTypeEmploye(String typeEmploye) {
        this.typeEmploye = typeEmploye;
    }

    public String getTelEmploye() {
        return telEmploye;
    }

    public void setTelEmploye(String telEmploye) {
        this.telEmploye = telEmploye;
    }

    public String getMailEmploye() {
        return mailEmploye;
    }

    public void setMailEmploye(String mailEmploye) {
        this.mailEmploye = mailEmploye;
    }

    public float getSoldeEmploye() {
        return soldeEmploye;
    }

    public void setSoldeEmploye(float soldeEmploye) {
        this.soldeEmploye = soldeEmploye;
    }

    public String getMdpEmploye() {
        return mdpEmploye;
    }

    public void setMdpEmploye(String mdpEmploye) {
        this.mdpEmploye = mdpEmploye;
    }

    public int getStatusEmploye() {
        return statusEmploye;
    }

    public void setStatusEmploye(int statusEmploye) {
        this.statusEmploye = statusEmploye;
    }

    public String getDateCreationCompteEmploye() {
        return dateCreationCompteEmploye;
    }

    public void setDateCreationCompteEmploye(String dateCreationCompteEmploye) {
        this.dateCreationCompteEmploye = dateCreationCompteEmploye;
    }

    public String getDateDerniereConnexionEmploye() {
        return dateDerniereConnexionEmploye;
    }

    public void setDateDerniereConnexionEmploye(String dateDerniereConnexionEmploye) {
        this.dateDerniereConnexionEmploye = dateDerniereConnexionEmploye;
    }

    public String getIdEntreprise() {
        return idEntreprise;
    }

    public void setIdEntreprise(String idEntreprise) {
        this.idEntreprise = idEntreprise;
    }
}
