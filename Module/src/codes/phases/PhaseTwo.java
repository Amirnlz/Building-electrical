package codes.phases;

import codes.graph.GraphGenerate;
import codes.picture.InputPicture;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class PhaseTwo {
    public TextField inputAddress;
    public Button BrowseButton;
    public AnchorPane anchorPane;
    public Button nextButton;
    private String fileAddress;
    private Stage stage;

    public void selectedFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Photo");
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        File selectedPhoto = fileChooser.showOpenDialog(stage);
        if (selectedPhoto != null){
            fileAddress = selectedPhoto.getAbsolutePath();

            InputPicture in=new InputPicture(fileAddress);
            GraphGenerate graphGenerate=new GraphGenerate();
            graphGenerate.filterGraph(in.getFile()) .show();
        }

    }

    public void goToFinalPage(ActionEvent actionEvent) throws IOException {
        fileAddress = (fileAddress == null ? inputAddress.getText() : fileAddress);
        closeStage();
        loadPhase();
    }

    public String getFileAddress(){
        return fileAddress;
    }

    private void closeStage() {
        stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

    private void loadPhase() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/finalPage.fxml")));
        stage.setTitle("Result graph");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
