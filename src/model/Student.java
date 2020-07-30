package model;

import java.util.ArrayList;

public class Student {

    private int id;
    private String ime;
    private String prezime;
    private String smer;
    private int godinaUpisa;
    private ArrayList<Predmet> polozeniPredmeti;

    public Student() {
    }

    public Student(int id, String ime, String prezime, String smer, int godinaUpisa) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.smer = smer;
        this.godinaUpisa = godinaUpisa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSmer() {
        return smer;
    }

    public void setSmer(String smer) {
        this.smer = smer;
    }

    public int getGodinaUpisa() {
        return godinaUpisa;
    }

    public void setGodinaUpisa(int godinaUpisa) {
        this.godinaUpisa = godinaUpisa;
    }

    public ArrayList<Predmet> getPolozeniPredmeti() {
        return polozeniPredmeti;
    }

    public void setPolozeniPredmeti(ArrayList<Predmet> polozeniPredmeti) {
        this.polozeniPredmeti = polozeniPredmeti;
    }
}
