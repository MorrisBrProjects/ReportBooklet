package de.morrisbr.reportbooklet.core.bericht.category;

import de.morrisbr.reportbooklet.core.bericht.task.Task;

public class CategoryWithContent {

    private String content;
    private Task task;

    public CategoryWithContent(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return this.task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
