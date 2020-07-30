package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Student;

import java.io.*;
import java.util.ArrayList;

public final class JSON {

    private String path;
    private Gson gson;

    public JSON(String path) {
        this.path = path;
        this.gson = new Gson();
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String stringify(ArrayList<Student> students) {
        return gson.toJson(students);
    }

    public ArrayList<Student> parse(String students) {
        return gson.fromJson(students, new TypeToken<ArrayList<Student>>(){}.getType());
    }

    public ArrayList<Student> readFromFile() {

        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(path));

            String str;

            while ((str = br.readLine()) != null) {
                stringBuilder.append(str);
            }
            br.close();

            str = stringBuilder.toString();

            return gson.fromJson(str, new TypeToken<ArrayList<Student>>(){}.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void writeToFile(ArrayList<Student> students) {
        String object = stringify(students);

        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(path));
            bw.write(object);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bw != null) {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
