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
import models.Doctor;
import models.Patient;

public class ForgotPassword extends Application {
    dao.Database db;
    models.Patient patient = null;
    models.Doctor doctor = null;
    MainMenu mm;

    public static void main(String[] args) {
        launch(args);
    }

    public ForgotPassword() {
    }

    public ForgotPassword(MainMenu mm) {
        this.mm = mm;
    }

    private Insets INSETS = new Insets(10, 10, 10, 10);

    @Override
    public void start(Stage primaryStage) {
        db = new dao.Database();
        primaryStage.setTitle("Password Recovery");

        Text title = new Text("Password Recovery Form");
        title.setStyle("-fx-font-size: 40; -fx-fill: #006400;");

        Button btn1 = createButton("Retrieve Account", "#cc0000");
        btn1.setStyle("-fx-font-size: 16; -fx-text-fill: white; -fx-background-color: #cc0000;");

        Button btn2 = createButton("Reset Password", "#cc0000");
        btn2.setStyle("-fx-font-size: 16; -fx-text-fill: white; -fx-background-color: #cc0000;");

        Button backButton = createButton("Back", "#808080");
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ForgotPassword.this.mm.start(primaryStage); // I forgor
            }
        });

        CheckBox cb = new CheckBox("Are you a patient? (Check the box if yes)");
        cb.setIndeterminate(false);
        cb.setStyle("-fx-font-size: 20; -fx-text-fill: #B8860B;");

        GridPane grid1 = new GridPane();
        grid1.setHgap(20);
        grid1.setVgap(20);
        grid1.setAlignment(Pos.CENTER);

        GridPane grid2 = new GridPane();
        grid2.setHgap(5);
        grid2.setVgap(20);
        grid2.setAlignment(Pos.CENTER);

        Label emailLabel = new Label("Email: ");
        Label phoneLabel = new Label("Phone Number: ");
        Label usernameLabel = new Label("Current Username: ");
        Label passwordLabel = new Label("New Password: ");

        emailLabel.setStyle("-fx-font-size: 20; -fx-text-fill: #000080;");
        phoneLabel.setStyle("-fx-font-size: 20; -fx-text-fill: #000080;");
        usernameLabel.setStyle("-fx-font-size: 20; -fx-text-fill: #000080;");
        passwordLabel.setStyle("-fx-font-size: 20; -fx-text-fill: #000080;");

        TextField email = new TextField();
        TextField phone = new TextField();
        TextField password = new TextField();
        TextField username = new TextField();

        grid1.add(emailLabel, 0, 0);
        grid1.add(email, 1, 0, 5, 1);
        grid1.add(phoneLabel, 0, 1);
        grid1.add(phone, 1, 1, 5, 1);
        grid1.add(cb, 0, 2, 6, 1);

        grid2.add(usernameLabel, 0, 0);
        grid2.add(username, 1, 0, 5, 1);
        grid2.add(passwordLabel, 0, 1);
        grid2.add(password, 1, 1, 5, 1);

        BorderPane root = new BorderPane(); // Create a BorderPane layout
        username.setDisable(true);
        password.setDisable(true);

        VBox boxInput = new VBox();
        boxInput.getChildren().addAll(title, grid1, btn1, grid2, btn2, backButton);

        email.setAlignment(Pos.CENTER_LEFT);
        password.setAlignment(Pos.CENTER_LEFT);
        usernameLabel.setAlignment(Pos.CENTER_LEFT);
        username.setAlignment(Pos.CENTER_LEFT);
        passwordLabel.setAlignment(Pos.CENTER_LEFT);
        password.setAlignment(Pos.CENTER_LEFT);

        email.setPrefWidth(280);
        password.setPrefWidth(280);
        usernameLabel.setPrefWidth(280);
        username.setPrefWidth(280);
        passwordLabel.setPrefWidth(280);
        password.setPrefWidth(280);

        boxInput.setAlignment(Pos.CENTER);
        boxInput.setSpacing(30);

        btn2.setDisable(true);
        root.setCenter(boxInput);

        // Create an event handler for "Login" button
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String em = email.getText(); // Get Username from input
                String pn = phone.getText(); // Get Password from input

                if(cb.isSelected()) {
	                models.Patient pat = db.patientForgotPassword(em, pn);

	                if (pat != null) {
	                    patient = pat;
	                    System.out.println("Patient FOUND in the Database");
	                    btn2.setDisable(false);
	                    password.setDisable(false);
	                    
	                } else {
	                    System.out.println("Patient NOT found in the Database");
	                }
                }
                else {
                	models.Doctor doc = db.doctorForgotPassword(em, pn);                	
                	if(doc != null) {
                		doctor = doc;
 	                    System.out.println("Doctor FOUND in the Database");
 	                    btn2.setDisable(false);
 	                    password.setDisable(false);
                	}
                	else {
                		System.out.println("Doctor NOT found in the Databse");
                	}
                }
           }
        });
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String pw = password.getText(); // Get Username from input
                // Get Password from input
                
                if(cb.isSelected()) { // patient
	                if (db.resetPassword(patient.getId(), pw,1)) {
	                    System.out.println("Patient password successfully reset");
	                } else {
	                    System.out.println("Patient NOT found in the Database");
	                }
                }
                else { // doctor
                	if (db.resetPassword(doctor.getId(), pw,2)) {
	                    System.out.println("Doctor password successfully reset");
	                } else {
	                    System.out.println("Doctor NOT found in the Database");
	                }
                }
           }
        });
        Scene scene = new Scene(root, 1100, 600);

//        primaryStage.setMinWidth(600); // Set minimum width for the window
//        primaryStage.setMinHeight(400); // Set minimum height for the window
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
