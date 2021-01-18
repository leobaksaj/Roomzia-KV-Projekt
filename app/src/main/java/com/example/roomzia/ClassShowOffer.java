package com.example.roomzia;


public class ClassShowOffer {

    String idOglasa;
    String naslov;
    String opis;
    String grad;
    String ulica;
    String kontakt;
    String kvadratura;
    String datum;
    String vrijeme;
    String zauzece;
    String userID;
    String cijena;
    String zauzeto;
    String idPonude;

    public ClassShowOffer()
    {

    }

    public ClassShowOffer(String idOglasa, String naslov, String opis, String grad, String ulica, String kontakt, String kvadratura, String datum, String vrijeme, String zauzece, String userID, String cijena, String zauzeto, String idPonude) {
        this.idOglasa = idOglasa;
        this.naslov = naslov;
        this.opis = opis;
        this.grad = grad;
        this.ulica = ulica;
        this.kontakt = kontakt;
        this.kvadratura = kvadratura;
        this.datum = datum;
        this.vrijeme = vrijeme;
        this.zauzece = zauzece;
        this.userID = userID;
        this.cijena = cijena;
        this.zauzeto = zauzeto;
        this.idPonude = idPonude;
    }

    public String getIdPonude() {
        return idPonude;
    }

    public void setIdPonude(String idPonude) {
        this.idPonude = idPonude;
    }

    public String getZauzeto() {
        return zauzeto;
    }

    public void setZauzeto(String zauzeto) {
        this.zauzeto = zauzeto;
    }

    public String getIdOglasa() {
        return idOglasa;
    }

    public void setIdOglasa(String idOglasa) {
        this.idOglasa = idOglasa;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getKontakt() {
        return kontakt;
    }

    public void setKontakt(String kontakt) {
        this.kontakt = kontakt;
    }

    public String getKvadratura() {
        return kvadratura;
    }

    public void setKvadratura(String kvadratura) {
        this.kvadratura = kvadratura;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(String vrijeme) {
        this.vrijeme = vrijeme;
    }

    public String getZauzece() {
        return zauzece;
    }

    public void setZauzece(String zauzece) {
        this.zauzece = zauzece;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCijena() {
        return cijena;
    }

    public void setCijena(String cijena) {
        this.cijena = cijena;
    }
}
