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
            setFileAddress(fileAddress);
        }
    }

    public void goToFinalPage(ActionEvent actionEvent) throws IOException {
        if (fileAddress == null)
            setFileAddress(inputAddress.getText());
        graphProcess(fileAddress);
        closeStage();
        loadPhase();
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public String getFileAddress(){
        return fileAddress;
    }
    private void graphProcess(String address){
        InputPicture in=new InputPicture(address);
        GraphGenerate graphGenerate=new GraphGenerate();
        graphGenerate.filterGraph(in.getFile()) .show();
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
