package codes.starter;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
import java.util.Stack;

public class StartPageController {
    public AnchorPane anchorPane;

    public void phase1Selection(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/phaseOne.fxml")));
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
        stage.setTitle("choose phase");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void phase2Selection(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/phaseTwo.fxml")));
    }
    private void closeStage(){
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }
//    private void loadPhase(String phase) throws IOException {
//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/" + phase + ".fxml")));
//        Stage stage;
//        stage.setTitle("choose phase");
//        stage.setScene(new Scene(root));
//        stage.show();
//    }


}
