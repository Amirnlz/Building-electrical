package codes.starter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

public class Main extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/startPage.fxml")));
        setStarterStage(primaryStage);
        stage = primaryStage;
        primaryStage.setTitle("choose phase");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    public Stage getStarterStage(){
        return stage;
    }
    private void setStarterStage(Stage stage){
        Main.stage = stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
