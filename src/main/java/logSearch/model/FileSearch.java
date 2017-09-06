package logSearch.model;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class FileSearch extends File {
    private ArrayList <Point> positions;
    private String text;


    FileSearch(String pathname) {
        super(pathname);
    }

    void addPositions(int x, int y){
        if (positions == null){
            positions = new ArrayList<>();
        }
        positions.add(new Point(x,y));
    }

    public ArrayList<Point> getPositions(){
        return positions;
    }

    public void addText(String text){
        this.text = text;
    }


    public String getText(){
        return text;
    }
}
