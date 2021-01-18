package com.example.roomzia;

public class OglasClass {

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

    public OglasClass(){

    }

    public OglasClass(String ID, String naslov, String opis, String grad, String ulica, String kontakt, String kvadratura, String datum, String vrijeme, String zauzece, String UserID) {
        this.Opis = opis;
        this.Grad = grad;
        this.Ulica = ulica;
        this.Kontakt = kontakt;
        this.Kvadratura = kvadratura;
        this.Datum = datum;
        this.Vrijeme = vrijeme;
        this.Naslov = naslov;
        this.Zauzece = zauzece;
        this.UserID = UserID;
    }
    public String getIDOglasa() {
        return ID;
    }

    public void setIDOglasa(String ID) {
        this.ID = ID;
    }

    public String getZauzece() {
        return Zauzece;
    }

    public void setZauzece(String zauzece) {
        Zauzece = zauzece;
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
    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
