package logSearch;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logSearch.model.FileDirectory;

public class ResultThread extends Thread {

    FileDirectory dir;
    String text;
    TabPane tp;

    public ResultThread(FileDirectory dir, String text, TabPane tabPane){
        //ResultController.lock = new ReentrantLock();
        this.dir = dir;
        this.text = text;
        this.tp = tabPane;
    }

    @Override
    public void run(){
        Tab tab = new Tab();
        tab.setText("tab 1");
        tab.setContent(new Rectangle(200,200, Color.LIGHTSTEELBLUE));
        System.out.println(Thread.currentThread().getName() + " is running");

        //try {
            //if(ResultController.lock.tryLock(1000, TimeUnit.SECONDS)) {
                try {
                    tp.getTabs().add(tab);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                   // ResultController.lock.unlock();
                }
            //}
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
