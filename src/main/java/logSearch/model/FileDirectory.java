package logSearch.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import logSearch.view.ResultController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileDirectory extends File{

    private final StringProperty url;
    private ArrayList<FileDirectory> folders;
    private ArrayList<FileSearch> files;

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

    public ArrayList<FileSearch> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<FileSearch> files) {
        this.files = files;
    }

    public FileDirectory(String url,String extension, String text){
        super(url);
        this.isSomeFiles = false;
        this.url = new SimpleStringProperty(url);
        this.files = new ArrayList<>();
        this.folders = new ArrayList<>();
        setUrl(url);
        try{
            for (File file : this.listFiles()){
                if (file.isDirectory()){
                    FileDirectory dir = new FileDirectory(file.getPath(), extension, text);
                    if (dir.isSomeFiles()){
                        folders.add(dir);
                        isSomeFiles = true;
                    }
                }
                else{
                    Pattern pattern = Pattern.compile(extension);
                    Matcher matcher = pattern.matcher(file.getPath());
                    if (matcher.find()) {
                        StringBuilder s = new StringBuilder("");
                        Scanner in = new Scanner(new File(file.getPath()));
                        while (in.hasNext())
                            s.append(in.nextLine() + "\r\n");
                        in.close();
                        String allText = s.toString();
                        pattern = Pattern.compile(text);
                        matcher = pattern.matcher(allText);
                        FileSearch newFile = new FileSearch(file.getPath());
                         while (matcher.find()){
                             newFile.addPositions(matcher.start(), matcher.end());
                             isSomeFiles = true;
                         }
                        if (isSomeFiles) {
                            newFile.addText(allText);
                            files.add(newFile);
                        }
                    }
                }
            }
            isExist = true;
        }
        catch (NullPointerException e){
            isExist = false;
        } catch (FileNotFoundException e) {
            ResultController.setAlert("Что-то пошло не так. Файл не найден. Попробуйте еще раз.");
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
