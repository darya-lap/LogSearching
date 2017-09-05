package logSearch.view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import logSearch.Main;
import logSearch.model.FileDirectory;

import javax.swing.*;
import java.io.File;

public class ResultController {
    @FXML
    private Button changeDirButton;

    @FXML
    private TextField searchDirField;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private ChoiceBox choiceBox;

    @FXML
    private TabPane tabPane;

    public static int counter;

    private Main mainApp;

    public ResultController(){
        counter = 1;
    }

    @FXML
    private void initialize(){
        searchDirField.setText("H:\\testDir");
        searchDirField.setFocusTraversable(false);
        changeDirButton.setFocusTraversable(false);
        searchTextField.setFocusTraversable(false);
        searchButton.setFocusTraversable(false);
        choiceBox.setFocusTraversable(false);

        choiceBox.setItems(FXCollections.observableArrayList(".log",".txt"));
        choiceBox.getSelectionModel().selectFirst();
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;

        // Добавление в таблицу данных из наблюдаемого списка
    }

    public void createTab(){

        if (searchDirField.getText() == ""){
            System.out.println("No such directory");
        }
        else {
            FileDirectory fd = new FileDirectory(searchDirField.getText(), choiceBox.getValue().toString());
            if (!fd.isExist()){
                setAlert("Не существует указанного пути");
            }
            else {
                if (searchText(fd)) {
                    //            ResultThread thread = new ResultThread(fd, text, tabPane);
                    //            thread.start();
                    Tab tab = new Tab();
                    tab.setText("tab " + counter);
                    counter++;

                    SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                    selectionModel.select(tab); //select by object

                    SplitPane sp = new SplitPane();
                    AnchorPane ap1 = new AnchorPane();
                    AnchorPane ap2 = new AnchorPane();
                    TreeView tw = new TreeView();
                    VBox vb = new VBox();
                    ButtonBar bb = new ButtonBar();
                    Button b1 = new Button();
                    Button b2 = new Button();
                    Button b3 = new Button();
                    Label l = new Label();
                    Pane p = new Pane();

                    b1.setText("<-");
                    b2.setText("->");
                    b3.setText("all");

                    tw.prefWidthProperty().bind(ap1.widthProperty());
                    tw.prefHeightProperty().bind(ap1.heightProperty());
                    VBox.setMargin(bb, new Insets(10, 10, 10, ap2.getWidth()));

                    Node folderIcon = new ImageView(
                            new Image(getClass().getResourceAsStream("folder_16.png"))
                    );

                    TreeItem<String> rootItem = new TreeItem<String> ("Inbox",folderIcon);
                    rootItem.setExpanded(true);
                    for (int i = 1; i < 6; i++) {
                        TreeItem<String> item = new TreeItem<String> ("Message" + i);
                        rootItem.getChildren().add(item);
                    }
                    tw = new TreeView<String> (rootItem);

                    bb.getButtons().setAll(b1, b2, b3);
                    vb.getChildren().setAll(bb, l, p);
                    ap1.getChildren().setAll(tw);
                    ap2.getChildren().setAll(vb);
                    sp.getItems().setAll(ap1, ap2);

                    tab.setContent(sp);

                    //tab.setContent(new Rectangle(200,200, Color.LIGHTSTEELBLUE));
                    tabPane.getTabs().add(tab);
                }
            }
        }
    }

    public void openDir(){
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int ret = chooser.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File directory = chooser.getSelectedFile();
            searchDirField.setText(directory.toString());
        }
    }

    public boolean searchText(FileDirectory fd){
        System.out.println(fd);
        if (fd.getFiles().size() == 0){
            setAlert("В данной папке нет файлов с подходящим расширением");
            return false;
        }
        else{
            return true;
        }
    }

    public void setAlert(String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }
}
