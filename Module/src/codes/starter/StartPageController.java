package codes.starter;

import codes.phases.PhaseOne;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class StartPageController {
    public AnchorPane anchorPane;
    private Stage stage;

    public void phase1Selection(ActionEvent actionEvent) throws IOException {
        closeStage();
        PhaseOne phaseOne = new PhaseOne();
        phaseOne.run();
    }

    public void phase2Selection(ActionEvent actionEvent) throws IOException {
        closeStage();
        loadPhase();
    }

    private void closeStage() {
        stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

    private void loadPhase() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/fxml/phaseTwo.fxml")));
        stage.setTitle("phaseTwo");
        stage.setScene(new Scene(root));
        stage.show();
    }


}
