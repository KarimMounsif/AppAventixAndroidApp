package com.example.km.qrcodepay.model;

/**
 * Created by thoma on 12/03/2018.
 */

public class Commercant {
    public static String idCommercant;
    public static String civiliteCommercant;
    public static String prenomCommercant;
    public static String nomCommercant;
    public static String nomCommerce;

    public Commercant() {
    }

    public Commercant(String idCommercant, String civiliteCommercant, String prenomCommercant,
                      String nomCommercant, String nomCommerce){
        this.idCommercant = idCommercant;
        this.civiliteCommercant = civiliteCommercant;
        this.prenomCommercant = prenomCommercant;
        this.nomCommercant = nomCommercant;
        this.nomCommerce = nomCommerce;
    }

    public static String getIdCommercant() {
        return idCommercant;
    }

    public static void setIdCommercant(String idCommercant) {
        Commercant.idCommercant = idCommercant;
    }

    public static String getCiviliteCommercant() {
        return civiliteCommercant;
    }

    public static void setCiviliteCommercant(String civiliteCommercant) {
        Commercant.civiliteCommercant = civiliteCommercant;
    }

    public static String getPrenomCommercant() {
        return prenomCommercant;
    }

    public static void setPrenomCommercant(String prenomCommercant) {
        Commercant.prenomCommercant = prenomCommercant;
    }

    public static String getNomCommercant() {
        return nomCommercant;
    }

    public static void setNomCommercant(String nomCommercant) {
        Commercant.nomCommercant = nomCommercant;
    }

    public static String getNomCommerce() {
        return nomCommerce;
    }

    public static void setNomCommerce(String nomCommerce) {
        Commercant.nomCommerce = nomCommerce;
    }
}
