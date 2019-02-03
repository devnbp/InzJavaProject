package ua.edu.sumdu;

import com.google.gson.Gson;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BookController {
    public static void main(){
        BookView v = new BookView();
        v.display();
    }

    public static void create(List<Book> books, DefaultListModel<String> list){
        BookCreateView v = new BookCreateView(books, list);
        v.display();
    }

    public static Book store(String name, String author, String publisher, int year, String content){
        Book book = new Book(name, author, publisher, year, content);
        book.store();
        return book;
    }

    public static List<Book> list(){
        List<Book> books = new ArrayList<Book>();
        Gson gson = new Gson();
        DataBase db = new DataBase();
        File[] files = db.getFolder("Book");
        for (File f : files){
            books.add(gson.fromJson(db.getContent(f.toString()), Book.class));
        }
        return books;
    }

    public static void delete(Book book){
        book.delete();
    }
}
