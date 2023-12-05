package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMenuMVC extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Welcome to PatientCare+");

        Text title = new Text("Welcome to Patient Care+");
        title.getStyleClass().add("title");
        title.setStyle("-fx-font-size: 38; -fx-fill: #8B008B;");

        // Load the background image
        Image backgroundImage = new Image(getClass().getResource("/resources/mobile_health-01.png").toExternalForm());

        // Create an ImageView to display the background image
        ImageView backgroundImageView = new ImageView(backgroundImage);

        // Set the preserve ratio property to maintain the aspect ratio
        backgroundImageView.setPreserveRatio(true);

        // Set the dimensions of the background image within the scene
        backgroundImageView.setFitWidth(1100);
        backgroundImageView.setFitHeight(650);

        // Load your FXML content
        Parent root = FXMLLoader.load(getClass().getResource("/resources/mainmenu.fxml"));

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(150);
        vbox.getChildren().addAll(title, root);

        // Create a StackPane to layer the background image and FXML content
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(backgroundImageView, vbox);

        Scene scene = new Scene(stackPane, 1100, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
