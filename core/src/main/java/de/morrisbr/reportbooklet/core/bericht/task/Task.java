package de.morrisbr.reportbooklet.core.bericht.task;

public class Task {

    private String title;

    public Task(String name) {
        this.title = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
