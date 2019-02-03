package ua.edu.sumdu;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DataBase {
    private String namespace = "public";

    private String setPath(String file){
        if (!checkFolder(namespace)){
            createFolder(namespace);
        }
        //System.out.println(namespace + "/" + file);
        return namespace + "/" + file;
    }

    public boolean createFolder(String path){
        File folder = new File(path);
        if ( !( folder.exists() && folder.isDirectory() ) ){
            return folder.mkdir();
        }
        else {
            return true;
        }
    }

    private boolean checkFolder(String path){
        File folder = new File(path);
        return (folder.exists() && folder.isDirectory());
    }

    private boolean checkFile(String path){
        File folder = new File(path);
        return (folder.exists() && folder.isFile());
    }

    public boolean createFile(String type, int id){
        boolean r = false;

        type = setPath(type);

        if (!checkFolder(type)){
            createFolder(type);
        }

        File f = new File(type + "/" + Integer.toString(id));

        if ( !( f.exists() && f.isFile() ) ){
            try {
                boolean created = f.createNewFile();
                if (created) {
                    System.out.println("File " + type + "/" + Integer.toString(id) + " has been created");
                    r = true;
                }
                else {
                    System.out.println("Failed to create the file");
                }
            }
            catch(IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        return r;
    }

    public boolean deleteFile(String type, int id){
        boolean r = false;

        type = setPath(type);

        if (!checkFolder(type)){
            createFolder(type);
        }

        File f = new File(type + "/" + Integer.toString(id));

        if ( ( f.exists() && f.isFile() ) ){
            if(f.delete()) {
                System.out.println("File " + type + "/" + Integer.toString(id) + " deleted successfully");
                r = true;
            }
            else {
                System.out.println("Failed to delete the file");
            }
        }

        return r;
    }

    public File[] getFolder(String path){
        String fullPath = setPath(path);
        if (!checkFolder(path)){
            createFolder(fullPath);
        }

        return new File(fullPath).listFiles();
    }

    public int getMaxId(String path){
        final List<Integer> ids = new ArrayList<Integer>();


        File[] array = getFolder(path);

        for (File f : array){
            ids.add(Integer.parseInt(f.getName()));
        }

        if (ids.size() < 1){
            return 0;
        }

        int max = Collections.max(ids);
        System.out.println(max);
        return max;
    }

    public String getContent(String path) {
        String content = null;
        if (checkFile(path)){
            try {
                content = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
        return content;
    }

    public boolean setContent(String path, String content){
        boolean r = false;
        path = setPath(path);
        if (checkFile(path)){
            File file = new File(path);
            if (file.canWrite()){
                try {
                    FileOutputStream fileStream = new FileOutputStream(file);
                    Writer out = new BufferedWriter(new OutputStreamWriter(fileStream, StandardCharsets.UTF_8));
                    out.write(content);

                    out.flush();
                    out.close();
                    fileStream.close();
                    r = true;
                }
                catch (FileNotFoundException e) {
                    System.out.println("File not found");
                }
                catch (IOException e) {
                    System.out.println("Error initializing stream");
                }
            }
        }
        return r;
    }
}
