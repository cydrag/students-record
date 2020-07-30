package controller;

import com.google.gson.Gson;
import model.Student;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.*;

import static spark.Spark.*;

public class Controller {

    public static void main(String[] args) {
        staticFiles.location("/resources");
        port(5000);
        String path = "studenti.json";
        JSON json = new JSON(path);

        HashMap<String, Object> polja = new HashMap<>();

        get("/", (request, response) -> {

            ArrayList<Student> studenti = json.readFromFile();
            ArrayList<String> smerovi = new ArrayList<>();

            polja.put("studenti", studenti);

            for (Student student : studenti) {
                String smer = student.getSmer();
                if (!smerovi.contains(smer)) {
                    smerovi.add(smer);
                }
            }

            polja.put("filterSmer", smerovi);

            return new ModelAndView(polja, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/dodajStudenta", (request, response) -> {

            ArrayList<Student> studenti = json.readFromFile();

            int najveci = studenti.get(studenti.size() - 1).getId();
            najveci++;
            polja.put("najveciID", najveci);

            return new ModelAndView(polja, "dodavanje.hbs");
        }, new HandlebarsTemplateEngine());

        get("/izmeniStudenta/:id", (request, response) -> {

            int id = Integer.parseInt(request.params("id"));

            ArrayList<Student> studenti = json.readFromFile();

            polja.remove("izmenStudent");

            for (Student student : studenti) {
                if (student.getId() == id) {
                    polja.put("izmenStudent", student);
                    break;
                }
            }

            return new ModelAndView(polja, "izmena.hbs");
        }, new HandlebarsTemplateEngine());

        path("/api", () -> {
            post("/getStudenti", (request, response) -> json.stringify(json.readFromFile()));

            post("/dodavanje", (request, response) -> {

                Student student = new Gson().fromJson(request.queryParams("student"), Student.class);

                ArrayList<Student> studenti = json.readFromFile();
                studenti.add(student);

                json.writeToFile(studenti);

                return "Uspesno dodat novi student";
            });

            post("/sacuvajIzmene", (request, response) -> {

                ArrayList<Student> studenti = json.readFromFile();

                Student student = new Gson().fromJson(request.queryParams("student"), Student.class);

                Student zamenjen = null;

                for (int i = 0; i < studenti.size(); i++) {
                    if (student.getId() == studenti.get(i).getId()) {
                        zamenjen = studenti.set(i, student);
                        break;
                    }
                }

                json.writeToFile(studenti);

                if (zamenjen != null) {
                    return "Uspesno promenjeni podaci o studentu";
                }
                else {
                    return "Greska prilikom izmene podataka o studentu";
                }

            });

            post("/filter", (request, response) -> {
                String filterID = request.queryParams("filterID");
                String filterIme = request.queryParams("filterIme");
                String filterPrezime = request.queryParams("filterPrezime");
                String filterSmer = request.queryParams("filterSmer");
                String filterGodina = request.queryParams("filterGodina");
                String filterBrojPredmeta = request.queryParams("filterBrojPredmeta");

                ArrayList<Student> studenti = json.readFromFile();

                ArrayList<Student> filtrirani = new ArrayList<>(studenti);

                int i;

                if (!filterIme.equals("")) {
                    for (i = 0; i < filtrirani.size(); i++) {
                        if (!filtrirani.get(i).getIme().startsWith(filterIme)) {
                            filtrirani.remove(i);
                            i = -1;
                        }
                    }
                }

                if (!filterPrezime.equals("")) {
                    for (i = 0; i < filtrirani.size(); i++) {
                        if (!filtrirani.get(i).getPrezime().startsWith(filterPrezime)) {
                            filtrirani.remove(i);
                            i = -1;
                        }
                    }
                }

                if (!filterSmer.equals("0")) {
                    for (i = 0; i < filtrirani.size(); i++) {
                        if (!filtrirani.get(i).getSmer().equals(filterSmer)) {
                            filtrirani.remove(i);
                            i = -1;
                        }
                    }
                }

                if (!filterGodina.equals("")) {

                    int godinaUpisa = Integer.parseInt(filterGodina);

                    for (i = 0; i < filtrirani.size(); i++) {
                        if (filtrirani.get(i).getGodinaUpisa() != godinaUpisa) {
                            filtrirani.remove(i);
                            i = -1;
                        }
                    }
                }

                if (!filterBrojPredmeta.equals("")) {

                    int brojPredmeta = Integer.parseInt(filterBrojPredmeta);

                    for (i = 0; i < filtrirani.size(); i++) {
                        if (filtrirani.get(i).getPolozeniPredmeti().size() != brojPredmeta) {
                            filtrirani.remove(i);
                            i = -1;
                        }
                    }
                }

                if (filterID.equals("desc")) {
                    filtrirani.sort((o1, o2) -> o2.getId() - o1.getId());
                }
                else {
                    filtrirani.sort(Comparator.comparingInt(Student::getId));
                }

                return json.stringify(filtrirani);
            });
        });
    }

}
