package application;

import dao.Database;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import models.Patient;

public class PatientPortal extends Application{
	// public dao.Database db;
	// public models.Patient patient;
	LoginForm lf;
	models.Patient pat;
	Database db;
	MainMenu mm;
	
	private Insets INSETS = new Insets(10, 10, 10, 10);
	
	public PatientPortal() {
		this.lf = lf;
	}
	public PatientPortal(LoginForm lf) {
		this.lf = lf;
		this.pat = lf.patient;
	}

	public PatientPortal(Patient pat){
		this.pat = pat;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		db = new dao.Database();
		primaryStage.setTitle("Patient Portal");

		int picnum = pat.getId() % 3 + 1;
		Image img = new Image("file:resources/patient" + picnum + ".png");
		ImageView view = new ImageView(img);
		view.setFitHeight(200);
		view.setFitWidth(200);
		view.setPreserveRatio(true);

		Button profileBtn = new Button(null, view);
		profileBtn.setStyle( "-fx-background-color:transparent; -fx-background-image: url('/patient" + picnum + ".png'); -fx-background-radius: 50%; -fx-background-position: center; -fx-background-repeat: no-repeat; -fx-background-size: 150;");
		profileBtn.setMaxWidth(150);
		profileBtn.setMinWidth(150);
		profileBtn.setMaxHeight(150);
		profileBtn.setMinHeight(150);
		profileBtn.setContentDisplay(ContentDisplay.TOP);
		profileBtn.setPadding(Insets.EMPTY);

		Button updateBtn = createButton("Update Patient", "aquamarine");
		Button deleteBtn = createButton("Delete Patient", "mistyrose");
		Button logoutBtn = createButton("Logout", "salmon");
		Button generateBtn = createButton("Generate Patient-ID", "azure");
		Button backbutton = createButton("Back", "gray");

		TextField text1 = new TextField("Your ID is: ");
		text1.setDisable(true);
		text1.setMaxWidth(125);
		text1.setMinWidth(125);

		TextField email = new TextField();
		TextField password = new TextField();
		TextField lastName = new TextField();
		TextField firstName = new TextField();
		TextField emergencyPhone = new TextField();
		TextField emergencyContact = new TextField();

		email.setText(pat.getEmail());
		lastName.setText(pat.getLastName());
		firstName.setText(pat.getFirstName());
		emergencyPhone.setText(pat.getEmergencyPhone());
		emergencyContact.setText(pat.getEmergencyContact());

		GridPane grid = new GridPane();
		grid.setHgap(20);
		grid.setVgap(20);
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(20);

		Label emailLabel = new Label("Email: ");
		Label passwordLabel = new Label("New Password: ");
		Label lnLabel = new Label("Last Name: ");
		Label fnLabel = new Label("First name: ");
		Label phone = new Label("Phone: ");
		Label contact = new Label("Em. Contact: ");

		emailLabel.setStyle("-fx-font-size: 15; -fx-text-fill: #000080;");
		passwordLabel.setStyle("-fx-font-size: 15; -fx-text-fill: #000080;");
		lnLabel.setStyle("-fx-font-size: 15; -fx-text-fill: #000080;");
		fnLabel.setStyle("-fx-font-size: 15; -fx-text-fill: #000080;");
		phone.setStyle("-fx-font-size: 15; -fx-text-fill: #000080;");
		contact.setStyle("-fx-font-size: 15; -fx-text-fill: #000080;");

		grid.add(emailLabel, 0, 0);
		grid.add(passwordLabel, 2, 0);
		grid.add(lnLabel, 2, 1);
		grid.add(fnLabel, 0, 1);
		grid.add(phone, 0, 2);
		grid.add(contact, 2, 2);

		grid.add(email, 1, 0);
		grid.add(password, 3, 0);
		grid.add(lastName, 3, 1);
		grid.add(firstName, 1, 1);
		grid.add(emergencyPhone, 1, 2);
		grid.add(emergencyContact, 3, 2);

		BorderPane root = new BorderPane(); // Utilizing a BorderPane
		VBox boxInput = new VBox();

		VBox idBox = new VBox(generateBtn, text1);
		idBox.setSpacing(5);
		idBox.setAlignment(Pos.CENTER);
		HBox profileBox = new HBox(profileBtn, idBox);
		profileBox.setAlignment(Pos.CENTER);
		profileBox.setSpacing(30);

		VBox buttonBox = new VBox(updateBtn, deleteBtn);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setSpacing(5);

		HBox dataBox = new HBox(grid, buttonBox);
		dataBox.setAlignment(Pos.CENTER);
		dataBox.setSpacing(15);

		emailLabel.setAlignment(Pos.CENTER_LEFT);
		password.setAlignment(Pos.CENTER_LEFT);
		lnLabel.setAlignment(Pos.CENTER_LEFT);
		fnLabel.setAlignment(Pos.CENTER_LEFT);
		phone.setAlignment(Pos.CENTER_LEFT);
		contact.setAlignment(Pos.CENTER_LEFT);

		email.setPrefWidth(200);
		password.setPrefWidth(200);
		lastName.setPrefWidth(200);
		firstName.setPrefWidth(200);
		emergencyPhone.setPrefWidth(200);
		emergencyContact.setPrefWidth(200);

		boxInput.getChildren().addAll(profileBox, dataBox);
		root.setCenter(boxInput); // Set one text field to center.
		root.setBottom(backbutton);
		root.setPadding(new Insets(20, 10, 20, 10));

		generateBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				int patID = generateID(pat);
				text1.setText("Your ID is: " + patID);
			}
		});

		backbutton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				primaryStage.close(); // Actually closes the scene
			}
		});

		updateBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String ln = lastName.getText(); // Get text from the text field in the center
				String fn = firstName.getText(); // Get text from the text field in the center

				if (db.updatePatient(pat.getId(), email.getText(), password.getText(), ln, fn,
						emergencyPhone.getText(), emergencyContact.getText(), pat.getDoctorsNotes())) {
					System.out.println("patient updated");
					// mm.childCallback();
				} else {
					System.out.println("error updating patient");
				}
			}
		});

		deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				if (db.deletePatient(pat.getId())) {
					System.out.println("patient deleted");
//                    mm.childCallback();
				} else {
					System.out.println("error deleting patient");
				}
			}
		});

		Scene scene = new Scene(root, 900, 400);

		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private int generateID(models.Patient pat) {
		return pat.getId();
	}

	private Button createButton(String text, String color) {
		Button button = new Button(text);
		button.setStyle("-fx-border-width: 1px; -fx-border-style: solid; -fx-font-size: 12; -fx-text-fill: black; -fx-background-color: " + color + ";");
		button.setMinWidth(125);
		button.setMaxWidth(125);
		button.setMinHeight(40);
		button.setMaxHeight(40);
		BorderPane.setMargin(button, INSETS);
		BorderPane.setAlignment(button, Pos.CENTER);
		return button;
	}

}
