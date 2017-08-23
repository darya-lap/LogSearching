package logSearch.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import logSearch.Main;

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
    private ChoiceBox choiceBoxButton;

    private Main mainApp;

    public ResultController(){

    }

    @FXML
    private void initialize(){
        searchDirField.setText("H:\\testDir");
        //choiceBoxButton.setValue(".log");
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;

        // Добавление в таблицу данных из наблюдаемого списка
    }
}
