package com.example.roomzia;

import com.google.firebase.database.PropertyName;

public class UserHelperClass {

    public String ime, prezime, kontakt, email;
    public Number uloga;

    public UserHelperClass(){

    }

    public UserHelperClass(String ime, String prezime, String kontakt, Number uloga, String email){

        this.ime = ime;
        this.prezime = prezime;
        this.kontakt = kontakt;
        this.uloga = uloga;
        this.email = email;
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

    public String getKontakt() {
        return kontakt;
    }

    public void setKontakt(String kontakt) {
        this.kontakt = kontakt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Number getUloga() {
        return uloga;
    }

    public void setUloga(Number uloga) {
        this.uloga = uloga;
    }
}
