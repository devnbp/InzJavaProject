package ua.edu.sumdu;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.ceil;

public class Book extends Model {
    private String author = "Author";
    private String name = "Name";
    private String publisher = "Publisher";
    private int year = 1900;
    private int numPages;
    private List<String> pages;

    public Book(String name, String author, String publisher, int year, String content) {
        this.setFieldValue("name", name);
        this.setFieldValue("author", author);
        this.setFieldValue("publisher", publisher);
        this.setFieldValue("year", year);
        this.setFieldValue("content", content);
    }

    public Book(String content) {
        this.setFieldValue("content", content);
    }

    public Book() {
        this("Lorem Ipsum is simply dummy text of the printing and typesetting industry.");
    }

    private int wordCount(String str) {
        str = str.trim();
        if (str.isEmpty())
            return 0;
        return str.split("\\s+").length;
    }

    private int numPages(String str) {
        return (int) ceil((double) wordCount(str) / 10);
    }

    private List<String> pages(String str) {
        List<String> pages = new ArrayList<String>();
        String[] words = str.split("\\s+");
        int wordCount = wordCount(str);
        this.setFieldValue("numPages", numPages(str));
        for (int i = 1; i <= (int) this.getFieldValue("numPages"); i++){
            int ifValue = 10 * (i - 1);
            String page = "";
            for (int j = ifValue; j < (10 * i); j++) {
                if (j == ifValue){
                    page = words[j];
                } else if(j < wordCount){
                    page = page + " " + words[j];
                }
            }
            pages.add(page);
        }
        return pages;
    }

    public String getPage(int index) {
        List<String> pages = (List<String>) this.getFieldValue("pages");
        return pages.get(index);
    }

    public boolean store() {
        this.setFieldValue("pages", pages(this.getFieldValue("content").toString()));
        super.store();
        return true;
    }
}
