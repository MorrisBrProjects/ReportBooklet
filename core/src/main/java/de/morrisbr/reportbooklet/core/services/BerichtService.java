package de.morrisbr.reportbooklet.core.services;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import de.morrisbr.reportbooklet.core.bericht.Bericht;
import de.morrisbr.reportbooklet.core.utils.JsonConverter;

public class BerichtService {
    public List<String> getAllBerichteAsJson() {
        File[] listOfFiles = new File("resources/OnlineBanking/berichte").listFiles();
        List<String> berichte = new ArrayList();
        for (int i = 0; i < listOfFiles.length; i++) {
            PrintStream printStream;
            StringBuilder stringBuilder;
            if (listOfFiles[i].isFile()) {
                berichte.add(JsonConverter.readJSONFromFile(listOfFiles[i].getPath()));
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("File ");
                stringBuilder.append(listOfFiles[i].getName());
                printStream.println(stringBuilder.toString());
            } else if (listOfFiles[i].isDirectory()) {
                printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Directory ");
                stringBuilder.append(listOfFiles[i].getName());
                printStream.println(stringBuilder.toString());
            }
        }
        return berichte;
    }

    public String getBerichtAsJson(String name) {
        for (String json : getAllBerichteAsJson()) {
            if (((Bericht) JsonConverter.jsonStringToObject(json, Bericht.class)).getTitle().equalsIgnoreCase(name)) {
                return json;
            }
        }
        return "Not found!";
    }

    public Bericht getBerichtAsObject(String name) {
        return (Bericht) JsonConverter.jsonStringToObject(getBerichtAsJson(name), Bericht.class);
    }

    public boolean isBerichtExist(String name) {
        return getBerichtAsJson(name).equalsIgnoreCase("Not found!");
    }

    public void saveListOfBerichts(List<Bericht> berichts) {
        for (Bericht bericht : berichts) {
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(bericht.getTitle());
            stringBuilder.append(" saved");
            printStream.println(stringBuilder.toString());
        }
    }
}
