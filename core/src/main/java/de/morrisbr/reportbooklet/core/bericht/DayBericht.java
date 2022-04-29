package de.morrisbr.reportbooklet.core.bericht;

import java.util.HashMap;

import de.morrisbr.reportbooklet.core.bericht.task.Task;

public class DayBericht {

    private HashMap<String, String> tasks = new HashMap();
    private String title;

    public DayBericht(String title) {
        this.title = title;
    }

    public void addTask(String taskName, String content) {
        getTasks().put(taskName, content);
    }

    public void addTask(Task task, String content) {
        getTasks().put(task.getTitle(), content);
    }

    public String getTaskContent(String name) {
        return (String) this.tasks.get(name);
    }

    public void removeTask(String taskName) {
        getTasks().remove(taskName);
    }

    public void setTasks(HashMap<String, String> tasks) {
        this.tasks = tasks;
    }

    public HashMap<String, String> getTasks() {
        return this.tasks;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}


