package de.morrisbr.reportbooklet.core.bericht.category;

public class Category {

    //private Image image = null;
    private String title;

    public Category(String name) {
        this.title = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
