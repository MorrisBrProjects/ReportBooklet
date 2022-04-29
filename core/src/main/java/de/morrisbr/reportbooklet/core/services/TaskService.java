package de.morrisbr.reportbooklet.core.services;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import de.morrisbr.reportbooklet.core.bericht.task.Task;
import de.morrisbr.reportbooklet.core.utils.JsonConverter;

public class TaskService {
    public static List<String> getAllTasksAsJson() {
        File[] listOfFiles = new File("resources/OnlineBanking/tasks").listFiles();
        List<String> tasks = new ArrayList();
        for (int i = 0; i < listOfFiles.length; i++) {
            PrintStream printStream;
            StringBuilder stringBuilder;
            if (listOfFiles[i].isFile()) {
                tasks.add(JsonConverter.readJSONFromFile(listOfFiles[i].getPath()));
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
        return tasks;
    }

    public static String getTaskAsJson(String name) {
        for (String json : getAllTasksAsJson()) {
            if (((Task) JsonConverter.jsonStringToObject(json, Task.class)).getTitle().equalsIgnoreCase(name)) {
                return json;
            }
        }
        return "Not found!";
    }

    public static Task getTaskAsObject(String name) {
        return (Task) JsonConverter.jsonStringToObject(getTaskAsJson(name), Task.class);
    }
}
