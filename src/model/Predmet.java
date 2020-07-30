package model;

public class Predmet {

    private String naziv;
    private String profesor;
    private int brEspb;

    public Predmet(String naziv, String profesor, int brEspb) {
        this.naziv = naziv;
        this.profesor = profesor;
        this.brEspb = brEspb;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getBrEspb() {
        return brEspb;
    }

    public void setBrEspb(int brEspb) {
        this.brEspb = brEspb;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }
}
