package ua.edu.sumdu;

import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.NoSuchElementException;

public class Model {
    private int id;
    private String content;

    private Field getAttribute(String fieldName, Class<?> clazz) throws NoSuchElementException {
        if (clazz.getDeclaredFields() == null){
            throw new RuntimeException();
        }
        try {
            return clazz.getDeclaredField(fieldName);
        }
        catch (NoSuchFieldException e) {
            return getAttribute(fieldName, clazz.getSuperclass());
        }
    }


    public void setFieldValue(String fieldName, Object value) {
        try {
            Class<?> clazz = this.getClass();
            Field field = getAttribute(fieldName, clazz);
            String fieldType = field.getType().toString();
            if (!fieldType.equals("class java.lang.String")) {
                field.setAccessible(true);
                field.set(this,value);
            }
            else{
                fieldType = fieldType.split("\\s+")[1];
                if (value.getClass().getName().equals(fieldType)){
                    field.setAccessible(true);
                    field.set(this,value);
                }
                else{
                    System.out.println("Err of var types");
                }
            }
        }
        catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Object getFieldValue(String fieldName) {
        try {
            Class<?> clazz = this.getClass();
            Field field = getAttribute(fieldName, clazz);

            field.setAccessible(true);
            return field.get(this);
        }
        catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public String getContent(){
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }

    public boolean store(){
        String className = this.getClass().getSimpleName();
        System.out.println(className);
        DataBase db = new DataBase();
        int id = db.getMaxId(className) + 1;
        this.id = id;
        db.createFile(className, id);
        db.setContent(className + "/" + Integer.toString(id), getContent());
        return true;
    }

    public boolean update(){
        String className = this.getClass().getSimpleName();
        System.out.println(className);
        DataBase db = new DataBase();
        db.setContent(className + "/" + Integer.toString(this.id), getContent());
        return true;
    }

    public boolean delete(){
        String className = this.getClass().getSimpleName();
        System.out.println(className);
        DataBase db = new DataBase();
        db.deleteFile(className, this.id);
        return true;
    }
}
