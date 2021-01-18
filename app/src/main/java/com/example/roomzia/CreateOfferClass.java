package com.example.roomzia;

import android.renderscript.ScriptIntrinsicYuvToRGB;

import com.google.firebase.database.FirebaseDatabase;

public class CreateOfferClass {
    String idOglasa;
    String nCijena;
    String providerID;
    String ime;
    String prezime;
    String clearnerID;
    String zauzeto;
    String kontakt;
    String idPonude;

    public CreateOfferClass() {
    }

    public CreateOfferClass(String id, String nCijena, String uid, String ime, String prezime, String cleanerID, String zauzeto, String kontakt, String idponude) {
        this.idOglasa = id;
        this.nCijena = nCijena;
        this.providerID = uid;
        this.ime = ime;
        this.prezime = prezime;
        this.clearnerID = cleanerID;
        this.zauzeto = zauzeto;
        this.kontakt = kontakt;
        this.idPonude = idponude;
    }

    public String getIdPonude() {
        return idPonude;
    }

    public void setIdPonude(String idPonude) {
        this.idPonude = idPonude;
    }

    public String getKontakt() {
        return kontakt;
    }

    public void setKontakt(String kontakt) {
        this.kontakt = kontakt;
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

    public String getnCijena() {
        return nCijena;
    }

    public void setnCijena(String nCijena) {
        this.nCijena = nCijena;
    }

    public String getProviderID() {
        return providerID;
    }

    public void setProviderID(String providerID) {
        this.providerID = providerID;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getClearnerID() {
        return clearnerID;
    }

    public void setClearnerID(String clearnerID) {
        this.clearnerID = clearnerID;
    }
}
