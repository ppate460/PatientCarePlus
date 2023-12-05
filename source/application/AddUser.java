package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class AddUser extends Application {
    dao.Database db;
    boolean userAuth = false;

    public static void main(String[] args) {
        launch(args);
    }

    private Insets INSETS = new Insets(10, 10, 10, 10);

    @Override
    public void start(Stage primaryStage) {
        db = new dao.Database();
        primaryStage.setTitle("Add User Menu");

        Text title = new Text("Add User Form");
        title.setStyle("-fx-font-size: 40; -fx-fill: #8B008B;");

        Button btn1 = createButton("Add User", "#cc0000");
        btn1.setStyle("-fx-font-size: 16; -fx-text-fill: white; -fx-background-color: #cc0000;");

        Button backButton = createButton("Back", "#808080");

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);

        Label usernameLabel = new Label("Username: ");
        Label passwordLabel = new Label("Password: ");

        usernameLabel.setStyle("-fx-font-size: 20; -fx-text-fill: #000080;");
        passwordLabel.setStyle("-fx-font-size: 20; -fx-text-fill: #000080;");

        TextField userName = new TextField();
        TextField passWord = new TextField();

        CheckBox cb = new CheckBox("Are you a patient? (Check the box if yes)");
        cb.setIndeterminate(false);
        cb.setStyle("-fx-font-size: 20; -fx-text-fill: #B8860B;");

        grid.add(usernameLabel, 0, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(userName, 1, 0);
        grid.add(passWord, 1, 1);
        grid.add(cb, 0, 2, 2, 1);

        BorderPane root = new BorderPane(); // Create a BorderPane layout

        VBox boxInput = new VBox();
        boxInput.getChildren().addAll(title, grid, btn1, backButton);

        userName.setAlignment(Pos.CENTER_LEFT);
        passWord.setAlignment(Pos.CENTER_LEFT);

        userName.setPrefWidth(280);
        passWord.setPrefWidth(280);

        boxInput.setAlignment(Pos.CENTER);
        boxInput.setSpacing(30);

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MainMenu mm = new MainMenu();
                mm.start(primaryStage); // Ohohoho, guess what this does NOT do
            }
        });

        root.setCenter(boxInput); // Set input fields and button in the center of the BorderPane.

        // Create an event handler for "Add User" button
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String un = userName.getText(); // Get Username from input
                String pw = passWord.getText(); // Get Password from input

                // Attempt to add a user to the database
//                if (db.addUser(un, pw, cb.isSelected())) {
//                    userAuth = true;
//                    System.out.println("Username & Password Successfully Added");
//                } else {
//                    userAuth = false;
//                    System.out.println("Failure to Add Username & Password");
//                }
            }
        });

        Scene scene = new Scene(root, 1100, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 16; -fx-text-fill: white; -fx-background-color: " + color + ";");
        button.setMinWidth(150);
        button.setMinHeight(40);
        BorderPane.setMargin(button, INSETS);
        BorderPane.setAlignment(button, Pos.CENTER);
        return button;
    }
}
