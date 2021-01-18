package com.example.roomzia;

public class OfferClass {

    String idCistaca;
    String naslov;
    String opis;
    String grad;
    String ulica;
    String kvadratura;
    String cijena;
    String mIme;
    String mPrezime;

    public OfferClass() {
    }

    public OfferClass(String ID, String naslov, String opis, String grad, String ulica, String Kvadratura, String nCijena/*, String mIme , String mPrezime*/) {
        this.idCistaca = ID;
        this.naslov = naslov;
        this.opis = opis;
        this.grad = grad;
        this.ulica = ulica;
        this.cijena = nCijena;
       // this.mIme = mIme;
       // this.mPrezime = mPrezime;
        this.kvadratura = Kvadratura;
    }

    public String getIdCistaca() {
        return idCistaca;
    }

    public void setIdCistaca(String idCistaca) {
        this.idCistaca = idCistaca;
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

    public String getKvadratura() {
        return kvadratura;
    }

    public void setKvadratura(String kvadratura) {
        this.kvadratura = kvadratura;
    }

    public String getCijena() {
        return cijena;
    }

    public void setCijena(String cijena) {
        this.cijena = cijena;
    }

    public String getmIme() {
        return mIme;
    }

    public void setmIme(String mIme) {
        this.mIme = mIme;
    }

    public String getmPrezime() {
        return mPrezime;
    }

    public void setmPrezime(String mPrezime) {
        this.mPrezime = mPrezime;
    }
}
