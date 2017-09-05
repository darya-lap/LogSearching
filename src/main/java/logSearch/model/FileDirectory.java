package logSearch.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;
import java.util.ArrayList;

public class FileDirectory extends File{

    private final StringProperty url;
    private ArrayList<FileDirectory> folders;
    private ArrayList<File> files;

    private boolean isExist;

    public boolean isExist(){
        return isExist;
    }


    public boolean isSomeFiles() {
        return isSomeFiles;
    }

    private boolean isSomeFiles;

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

    public FileDirectory(String url,String extension){
        super(url);
        this.isSomeFiles = false;
        this.url = new SimpleStringProperty(url);
        this.files = new ArrayList<>();
        this.folders = new ArrayList<>();
        setUrl(url);
        try{
            for (File file : this.listFiles()){
                if (file.isDirectory()){
                    FileDirectory dir = new FileDirectory(file.getPath(), extension);
                    if (dir.isSomeFiles()){
                        folders.add(dir);
                        isSomeFiles = true;
                    }
                }
                else{
                    if (file.getPath().contains(extension)) {
                        files.add(file);
                        isSomeFiles = true;
                    }
                }
            }
            isExist = true;
        }
        catch (NullPointerException e){
            isExist = false;
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Folders:\n");
        for (FileDirectory fd: folders){
            sb.append(fd.getUrl());
            sb.append(", ");
        }
        sb.append("\nFiles:\n");
        for (File f:files){
            sb.append(f.getPath());
            sb.append(", ");
        }
        return  sb.toString();
    }

}
