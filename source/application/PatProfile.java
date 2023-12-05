package application;

import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Message;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class PatProfile extends Application {
    dao.Database db;
    models.Patient patient;
    models.Doctor doctor;
    MainMenu mm;
    Stage primaryStage;
    List<Message>messages;
    public static void main(String[] args) {
        launch(args);
    }

    public PatProfile() {
    }

    public PatProfile(MainMenu mm, models.Patient pat) {
        this.mm = mm;
        this.patient = pat;
    }
    
    public PatProfile(MainMenu mm, models.Doctor doc) {
        this.mm = mm;
        this.doctor = doc;
    }

    public void setProfile(models.Patient pat) {
        this.patient = pat;
    }

    private Insets INSETS = new Insets(10, 10, 10, 10);

    @Override
    public void start(Stage primaryStage) {
    	this.primaryStage=primaryStage;
        db = new dao.Database();
        
        primaryStage.setTitle("PatientCare+");

        Button btn5 = createButton("Reply");

        Button btn1 = createButton("Update Patient");
        Button btn2 = createButton("Delete Patient");
        Button btn3 = createButton("Logout");
        Button btn4 = createButton("Email Doctor");
        ListView listView = new ListView();

        int count=0;
        for (Message msg :patient.getMessages())
        {
        	listView.getItems().add(msg.getListRow());
        	++count;
        }
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println();
            }
        });
        listView.setMaxHeight(25*count);
        // Two text fields (use the JavaFX class TextField).
        TextField email = new TextField();
        TextField password = new TextField();
        TextField lastName = new TextField();
        TextField firstName = new TextField();
        TextField emergencyPhone = new TextField();
        TextField emergencyContact = new TextField();
        TextField doctorsNotes = new TextField();
        email.setText(patient.getEmail());
        lastName.setText(patient.getLastName());
        firstName.setText(patient.getFirstName());
        emergencyPhone.setText(patient.getEmergencyPhone());
        emergencyContact.setText(patient.getEmergencyContact());
        doctorsNotes.setText(patient.getDoctorsNotes());

        email.setPromptText("Email");
        password.setPromptText("password");
        lastName.setPromptText("Last Name"); // Enter prompt for text one
        firstName.setPromptText("First Name"); // Enter prompt for text one
        emergencyPhone.setPromptText("emergencyPhone"); // Enter prompt for text one
        emergencyContact.setPromptText("emergencyContact"); // Enter prompt for text one
        doctorsNotes.setPromptText("doctorsNotes"); // Enter prompt for text one

        BorderPane root = new BorderPane(); // Utilizing a BorderPane
        VBox boxInput = new VBox();
        boxInput.getChildren().add(email);
        boxInput.getChildren().add(password);
        boxInput.getChildren().add(lastName);
        boxInput.getChildren().add(firstName);
        boxInput.getChildren().add(emergencyPhone);
        boxInput.getChildren().add(emergencyContact);
        boxInput.getChildren().add(doctorsNotes);
        boxInput.getChildren().add(listView);

        boxInput.getChildren().add(btn5);
        boxInput.getChildren().add(btn1);
        boxInput.getChildren().add(btn2);
        boxInput.getChildren().add(btn4);
        boxInput.getChildren().add(btn3);
        root.setCenter(boxInput); // Set one text field to center.

        // Create and attach an EventHandler to "button 1" utilizing an anonymous class.
        // When clicked
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String ln = lastName.getText(); // Get text from the text field in the center
                String fn = firstName.getText(); // Get text from the text field in the center

                if (db.updatePatient(patient.getId(), email.getText(), password.getText(), ln, fn,
                        emergencyPhone.getText(), emergencyContact.getText(), patient.getDoctorsNotes())) {
                    System.out.println("patient updated");
                    mm.childCallback();
                } else {
                    System.out.println("error updating patient");
                }
            }
        });
        btn5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	int idx=listView.getSelectionModel().getSelectedIndex();
              	if(idx<0)
            	{
            		return;
            	}
            	Message msg = patient.getMessages().get(idx);
            	
            	System.out.println(msg);
            	// MessageThread mt = new MessageThread(msg,mm);
            	// mt.start(primaryStage);
            }
        });
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
            	int idx=listView.getSelectionModel().getSelectedIndex();
              	if(idx<0)
            	{
            		return;
            	}
            	Message msg = patient.getMessages().get(idx);
            	
            	System.out.println(msg);
            }
        });
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (db.deletePatient(patient.getId())) {
                    System.out.println("patient deleted");
//                    mm.childCallback();
                } else {
                    System.out.println("error deleting patient");
                }
            }
        });
        btn3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mm.logout();
            }
        });
        btn4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Email email = new Email(patient.getId(),0,1,mm);
                email.start(primaryStage);
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
