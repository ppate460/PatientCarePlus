package application;

import dao.Database;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import models.Doctor;
import models.Patient;

import java.util.stream.Stream;

public class LoginForm extends Application {
    dao.Database db;
    models.Patient patient = null;
    models.Doctor doctor = null;
    MainMenu mm;

    public static void main(String[] args) {
        launch(args);
    }

    public LoginForm() {
    }

    public LoginForm(MainMenu mm) {
        this.mm = mm;
    }

    private Insets INSETS = new Insets(10, 10, 10, 10);

    public void start(Stage primaryStage) {
        db = new Database();
        primaryStage.setTitle("Login Menu");

        Text title = new Text("Login Form (Doctor/Patient)");
        title.setStyle("-fx-font-size: 40; -fx-fill: #006400; -fx-font-weight: bold;");

        Button btn1 = createButton("Login", "#cc0000");
        btn1.setStyle("-fx-font-size: 16; -fx-text-fill: white; -fx-background-color: #cc0000;");
        // Add the back button to the layout
        Button backButton = createButton("Back", "#808080");

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(20);

        Label usernameLabel = new Label("Username: ");
        Label passwordLabel = new Label("Password: ");

        usernameLabel.setStyle("-fx-font-size: 20; -fx-text-fill: #000080;");
        passwordLabel.setStyle("-fx-font-size: 20; -fx-text-fill: #000080;");

        TextField userName = new TextField();
        TextField password = new TextField();

//        PasswordField passWord = new PasswordField();
        CheckBox cb = new CheckBox("Are you a patient? (Check the box if yes)");
        cb.setIndeterminate(false);
        cb.setStyle("-fx-font-size: 20; -fx-text-fill: #B8860B;");

        grid.add(usernameLabel, 0, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(userName, 1, 0);
        grid.add(password, 1, 1);
        grid.add(cb, 0, 2, 2, 1);

        VBox boxInput = new VBox();
        boxInput.getChildren().addAll(title, grid, btn1, backButton);

        userName.setPromptText("Username");
        password.setPromptText("Password");

        userName.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                userName.setPromptText("Enter your username");
            }
        });

        userName.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                userName.setPromptText("");
            }
        });

        password.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                userName.setPromptText("Enter your password");
            }
        });

        password.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                userName.setPromptText("");
            }
        });


//        ObjectProperty<Label> selectedText = new SimpleObjectProperty<>();
//
//        selectedText.addListener((obs, oldSelectedText, newSelectedText) -> {
//            if (oldSelectedText != null) {
//                oldSelectedText.setStyle("");
//            }
//            if (newSelectedText != null) {
//                newSelectedText.setStyle("-fx-background: lightgray");
//            }
//        });

//        Stream.of (usernameLabel, passwordLabel).forEach(t ->
//                t.setOnMouseDragOver(event -> selectedText.set(t)));

        userName.setAlignment(Pos.CENTER_LEFT);
        password.setAlignment(Pos.CENTER_LEFT);

        userName.setPrefWidth(280);
        password.setPrefWidth(280);

        boxInput.setAlignment(Pos.CENTER);
        boxInput.setSpacing(30);

        BorderPane root = new BorderPane(); // Create a BorderPane layout

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LoginForm.this.mm.start(primaryStage); // Does NOT close the scene anymore
            }
        });

        root.setCenter(boxInput); // Set input fields and buttons in the center of the BorderPane.

        // Create an event handler for "Login" button
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String un = userName.getText(); // Get Username from input
                String pw = password.getText(); // Get Password from input

                if (cb.isSelected()) {
                    Patient pat = db.authP(un, pw);

                    if (pat != null) {
                        patient = pat;
                        System.out.println("Patient Authenticated Successfully");
                        try {
                            mm.authResponse(pat);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.out.println("Patient NOT Authenticated");
                    }
                } else {
                    Doctor doc = db.authD(un, pw);

                    if (doc != null) {

                        System.out.println("Doctor Authenticated Successfully");
                        doc = db.authD(un, pw);

                        try {
                            mm.authResponse(doc);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

//                    	if(doc != null) {
//                    		doctor = doc;
//                    		System.out.println("Doctor User Authenticated");
//                    		mm.authResponse(doc);
//                    	}
//                       else {
//                    		System.out.println("Doctor User NOT Authenticated");
//                    	}
                        //Where Should to go after Doctor LogIn...
                    } else {
                        System.out.println("Doctor NOT Authenticated");
                    }
                }
            }
        });

        // Create a scene with the same size as specified in the main menu
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
