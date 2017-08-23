package logSearch.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;
import java.util.ArrayList;

public class FileDirectory extends File{

    private final StringProperty url;
    private ArrayList<FileDirectory> folders;
    private ArrayList<File> files;

    public String getUrl() {
        return url.get();
    }

    public void setUrl(String url) {
        this.url.set(url);
    }

    public ArrayList<FileDirectory> getFolders() {
        return folders;
    }

    public void setFolders(ArrayList<FileDirectory> folders) {
        this.folders = folders;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<File> files) {
        this.files = files;
    }

    public FileDirectory(String url){
        super(url);
        this.url = new SimpleStringProperty(url);
        this.files = new ArrayList<>();
        this.folders = new ArrayList<>();
        setUrl(url);
        try{
            for (File file : this.listFiles()){
                if (file.isDirectory()){
                    folders.add(new FileDirectory(file.getPath()));
                }
                else files.add(file);
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }

    }
}
