package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Message;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class DocProfile extends Application {
    dao.Database db;

    models.Doctor doctor;
    MainMenu mm;

    public static void main(String[] args) {
        launch(args);
    }

    public DocProfile() {
    }


    
    public DocProfile(MainMenu mm, models.Doctor doc) {
        this.mm = mm;
        this.doctor = doc;
    }

    public void setProfile(models.Doctor doc) {
    	this.doctor = doc;
    }

    private Insets INSETS = new Insets(10, 10, 10, 10);

    @Override
    public void start(Stage primaryStage) {
        db = new dao.Database();
        primaryStage.setTitle("PatientCare+");
        ListView listView = new ListView();
        int count=0;
        for (Message msg :doctor.getMessages())
        {
        	listView.getItems().add(msg.getListRow());
        	++count;
        }
        listView.setMaxHeight(25*count);
        Button btn5 = createButton("Reply");
        Button btn1 = createButton("Update Doctor");
        Button btn2 = createButton("Delete Doctor");
        Button btn4 = createButton("Email Patient");
        Button btn3 = createButton("Logout");

        // Two text fields (use the JavaFX class TextField).
        TextField email = new TextField();
        TextField password = new TextField();
        TextField lastName = new TextField();
        TextField firstName = new TextField();
        TextField officePhone = new TextField();
        TextField workAddress = new TextField();
        TextField certifications = new TextField();
        email.setText(doctor.getEmail());
        lastName.setText(doctor.getLastName());
        firstName.setText(doctor.getFirstName());
        officePhone.setText(doctor.getPhone());
        workAddress.setText(doctor.getWorkAddress());
        certifications.setText(doctor.getCertification());

        email.setPromptText("Email");
        password.setPromptText("password");
        lastName.setPromptText("Last Name"); // Enter prompt for text one
        firstName.setPromptText("First Name"); // Enter prompt for text one
        officePhone.setPromptText("Office Phone"); // Enter prompt for text one
        workAddress.setPromptText("Work Address"); // Enter prompt for text one
        certifications.setPromptText("Certifications"); // Enter prompt for text one

        BorderPane root = new BorderPane(); // Utilizing a BorderPane
        VBox boxInput = new VBox();
        boxInput.getChildren().add(email);
        boxInput.getChildren().add(password);
        boxInput.getChildren().add(lastName);
        boxInput.getChildren().add(firstName);
        boxInput.getChildren().add(officePhone);
        boxInput.getChildren().add(workAddress);
        boxInput.getChildren().add(certifications);
        boxInput.getChildren().add(listView);
        boxInput.getChildren().add(btn5);
        boxInput.getChildren().add(btn1);
        boxInput.getChildren().add(btn2);
        boxInput.getChildren().add(btn4);
        boxInput.getChildren().add(btn3);
        root.setCenter(boxInput); // Set one text field to center.
        btn5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	int idx=listView.getSelectionModel().getSelectedIndex();
            	if(idx<0)
            	{
            		return;
            	}
            	Message msg = doctor.getMessages().get(idx);
            	
            	System.out.println(msg);
            	// MessageThread mt = new MessageThread(msg,mm);
            	// mt.start(primaryStage);
            }
        });
        // Create and attach an EventHandler to "button 1" utilizing an anonymous class.
        // When clicked
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String ln = lastName.getText(); // Get text from the text field in the center
                String fn = firstName.getText(); // Get text from the text field in the center
/*
 * 
 int id, String userName, String password, String lastName, String firstName,
            String workAddress, String certification
 */
                if (db.updateDoctor(doctor.getId(), email.getText(), password.getText(), ln, fn,
                        officePhone.getText(), workAddress.getText(), certifications.getText())) {
                    System.out.println("patient added");
                    mm.childCallback();
                } else {
                    System.out.println("error adding patient");
                }
            }
        });

        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (db.deleteDoctor(doctor.getId())) {
                    System.out.println("patient deleted");
                    mm.childCallback();
                } else {
                    System.out.println("error deleting patient");
                }
            }
        });
        btn4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Email email = new Email(doctor.getId(),doctor.getPatientId(),2,mm);
                email.start(primaryStage);
            }
        });
        btn3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mm.logout();
            }
        });
        Scene scene = new Scene(root);

        primaryStage.setMinWidth(550); // Set min width for window.
        primaryStage.setMinHeight(400); // Set max width for window.
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createButton(String text) {
        Button button = new Button(text);

        button.setMinWidth(150);
        BorderPane.setMargin(button, INSETS);
        BorderPane.setAlignment(button, Pos.CENTER);
        return button;
    }
}
