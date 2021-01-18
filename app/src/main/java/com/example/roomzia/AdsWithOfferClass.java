package com.example.roomzia;

import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

public class AdsWithOfferClass {

    String ID;
    String Naslov;
    String Opis;
    String Grad;
    String Ulica;
    String Kontakt;
    String Kvadratura;
    String Datum;
    String Vrijeme;
    String Zauzece;
    String UserID;
    String ponude;

    public AdsWithOfferClass(){

    }

    public AdsWithOfferClass(String ID, String naslov, String opis, String grad, String ulica, String kontakt, String kvadratura, String datum, String vrijeme, String zauzece, String userID, String ponude) {
        this.ID = ID;
        Naslov = naslov;
        Opis = opis;
        Grad = grad;
        Ulica = ulica;
        Kontakt = kontakt;
        Kvadratura = kvadratura;
        Datum = datum;
        Vrijeme = vrijeme;
        Zauzece = zauzece;
        UserID = userID;
        this.ponude = ponude;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNaslov() {
        return Naslov;
    }

    public void setNaslov(String naslov) {
        Naslov = naslov;
    }

    public String getOpis() {
        return Opis;
    }

    public void setOpis(String opis) {
        Opis = opis;
    }

    public String getGrad() {
        return Grad;
    }

    public void setGrad(String grad) {
        Grad = grad;
    }

    public String getUlica() {
        return Ulica;
    }

    public void setUlica(String ulica) {
        Ulica = ulica;
    }

    public String getKontakt() {
        return Kontakt;
    }

    public void setKontakt(String kontakt) {
        Kontakt = kontakt;
    }

    public String getKvadratura() {
        return Kvadratura;
    }

    public void setKvadratura(String kvadratura) {
        Kvadratura = kvadratura;
    }

    public String getDatum() {
        return Datum;
    }

    public void setDatum(String datum) {
        Datum = datum;
    }

    public String getVrijeme() {
        return Vrijeme;
    }

    public void setVrijeme(String vrijeme) {
        Vrijeme = vrijeme;
    }

    public String getZauzece() {
        return Zauzece;
    }

    public void setZauzece(String zauzece) {
        Zauzece = zauzece;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getPonude() {
        return ponude;
    }

    public void setPonude(String ponude) {
        this.ponude = ponude;
    }

}
