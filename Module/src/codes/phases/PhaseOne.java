package codes.phases;

import codes.graph.Graph;
import codes.graph.GraphGenerate;
import codes.result.ResultGraph;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class PhaseOne {

    public AnchorPane anchorPane;
    public Button generateButton;
    public Label vertexNumLabel;
    public Label randNumLabel;
    public Label junctionBoxNumLabel;
    public Label switchNumLabel;
    public Label sourceLabel;
    private Stage stage;
    private Graph graph;

    public void graphGenerate(ActionEvent actionEvent) {
        GraphGenerate generate = new GraphGenerate();
        generate.createGraph();
        this.graph = generate.getGraph();
        int vertexNum = generate.getVertexNum();
        int junctionNum = generate.getJunctionBoxNum();
        int switchNum = generate.getSwitchNum();
        setLabels(vertexNum, junctionNum, switchNum);
    }

    public void setLabels(int vertexNum, int junctionBoxNum, int switchNum) {
        vertexNumLabel.setText("Graph have " + vertexNum + " Vertex.");
        randNumLabel.setText("Random number is " + vertexNum);
        junctionBoxNumLabel.setText("There are " + junctionBoxNum + " JunctionBoxes.");
        switchNumLabel.setText("There are " + switchNum + " Switches.");
        sourceLabel.setText("There is 1 Source.");
    }

    public void nextPage(ActionEvent actionEvent) throws IOException {
        closeStage();
        loadPage();
        sendGraph(graph);
    }
    private void sendGraph(Graph graph){
        ResultGraph res = new ResultGraph();
        res.setGraph(graph);
    }

    private void closeStage() {
        stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

    private void loadPage() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/finalPage.fxml")));
        stage.setTitle("Result graph");
        stage.setScene(new Scene(root));
        stage.show();
    }

}
