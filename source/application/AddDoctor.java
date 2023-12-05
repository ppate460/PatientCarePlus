package application;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddDoctor extends Application {
    dao.Database db;
    boolean userAuth = false;

    private final Insets INSETS = new Insets(10, 10, 10, 10);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        db = new dao.Database();
        primaryStage.setTitle("Add Doctor Menu");

        Text title = new Text("Add Doctor Form");
        title.setStyle("-fx-font-size: 40; -fx-fill: #006400;");

        Button btn1 = createButton("Add Doctor", "#cc0000");
        btn1.setStyle("-fx-font-size: 16; -fx-text-fill: white; -fx-background-color: #cc0000;");

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);

        Label emailLabel = new Label("Email: ");
        Label usernameLabel = new Label("Username: ");
        Label passwordLabel = new Label("Password: ");
        Label lastNameLabel = new Label("Last Name: ");
        Label firstNameLabel = new Label("First Name: ");
        Label workAddressLabel = new Label("Work Address: ");
        Label phoneLabel = new Label("Phone Number: ");
        Label certificatesLabel = new Label("Certifications: ");

        emailLabel.setStyle("-fx-font-size: 20; -fx-text-fill: #000080;");
        usernameLabel.setStyle("-fx-font-size: 20; -fx-text-fill: #000080;");
        passwordLabel.setStyle("-fx-font-size: 20; -fx-text-fill: #000080;");
        lastNameLabel.setStyle("-fx-font-size: 20; -fx-text-fill: #000080;");
        firstNameLabel.setStyle("-fx-font-size: 20; -fx-text-fill: #000080;");
        workAddressLabel.setStyle("-fx-font-size: 20; -fx-text-fill: #000080;");
        phoneLabel.setStyle("-fx-font-size: 20; -fx-text-fill: #000080;");
        certificatesLabel.setStyle("-fx-font-size: 20; -fx-text-fill: #000080;");

        TextField email = new TextField();
        TextField username = new TextField();
        TextField password = new TextField();
        TextField lastName = new TextField();
        TextField firstName = new TextField();
        TextField workAddress = new TextField();
        TextField phone = new TextField();
        TextField certification = new TextField();

        grid.add(emailLabel, 0, 0);
        grid.add(email, 1, 0, 5, 1);
        grid.add(usernameLabel, 0, 1);
        grid.add(username, 1, 1, 5, 1);
        grid.add(passwordLabel, 0, 2);
        grid.add(password, 1, 2, 5, 1);
        grid.add(lastNameLabel, 0, 3);
        grid.add(lastName, 1, 3, 5, 1);
        grid.add(firstNameLabel, 0, 4);
        grid.add(firstName, 1, 4, 5, 1);
        grid.add(workAddressLabel, 0, 5);
        grid.add(workAddress, 1, 5,5, 1);
        grid.add(phoneLabel, 0, 6);
        grid.add(phone, 1, 6,5, 1);
        grid.add(certificatesLabel, 0, 7);
        grid.add(certification, 1, 7, 12, 1);

        BorderPane root = new BorderPane();

        // Create a back button to return to the main menu
        Button backButton = createButton("Back", "#808080");
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MainMenu mm = new MainMenu(); // (un)Closes the current scene
                mm.start(primaryStage);
            }
        });

        VBox boxInput = new VBox();
        boxInput.getChildren().addAll(title, grid, btn1, backButton);

        email.setAlignment(Pos.CENTER_LEFT);
        username.setAlignment(Pos.CENTER_LEFT);
        password.setAlignment(Pos.CENTER_LEFT);
        firstName.setAlignment(Pos.CENTER_LEFT);
        lastName.setAlignment(Pos.CENTER_LEFT);
        workAddress.setAlignment(Pos.CENTER_LEFT);
        phone.setAlignment(Pos.CENTER_LEFT);
        certification.setAlignment(Pos.CENTER_LEFT);

        email.setPrefWidth(280);
        username.setPrefWidth(280);
        password.setPrefWidth(280);
        lastName.setPrefWidth(280);
        firstName.setPrefWidth(280);
        workAddress.setPrefWidth(280);
        phone.setPrefWidth(280);
        certification.setPrefWidth(280);

        boxInput.setAlignment(Pos.CENTER);
        boxInput.setSpacing(30);
        
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String ln = lastName.getText(); // Get last name from input
                String fn = firstName.getText(); // Get first name from input
                String userEmail = email.getText(); // Get email from input

                //******************************************************
                // Validate email format
                if (!isValidEmail(userEmail)) {
                    showErrorAlert("Invalid Email", "Please enter a valid email address.");
                    return;
                }
                // Check if the email is already in use
                if (db.isEmailInUse(userEmail)) {
                    showErrorAlert("Email In Use", "This email is already associated with an existing account. Please use a different email.");
                    return;
                }
                // Check if any of the required fields (except Emergency Phone and Emergency Contact) are empty
                if (ln.isEmpty() || fn.isEmpty() || userEmail.isEmpty() || password.getText().isEmpty()) {
                    showErrorAlert("Missing Information", "Please fill in all required fields (Last Name, First Name, Email, Password).");
                    return;
                }
                // Attempt to add a patient to the database
                if (db.addDoctor(email.getText(), username.getText(), password.getText(), ln, fn,
                        workAddress.getText(), certification.getText(), phone.getText())) {
                    userAuth = true;
                    System.out.println("Doctor Successfully Added");
                } else {
                    userAuth = false;
                    System.out.println("Error Adding Doctor");
                }
            }
            //********************************************************************
        });

        root.setCenter(boxInput);

        Scene scene = new Scene(root, 1100, 600); // Set a consistent scene size

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

    private boolean isValidEmail(String email) {
		return email.contains("@");
	}
	
	private void showErrorAlert(String title, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

}
