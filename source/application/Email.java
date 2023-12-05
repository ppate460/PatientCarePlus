package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import models.Doctor;
import models.Message;
import models.Patient;

public class Email extends Application {
    dao.Database db;
    MainMenu mm;
    int senderType;
    int senderId;
    int sendToId;
    String sendToName;
    public static void main(String[] args) {
        launch(args);
    }

    public Email() {
        }
    public Email(int senderId,int sendToId, int type,  MainMenu mm) {
    	this.mm=mm;
    	this.senderId=senderId;
    	this.sendToId=sendToId;
    	this.senderType=type;
    }
    public Email(Message message,  MainMenu mm) {
    	this.mm=mm;
    	this.senderId=message.getToId();
    	this.sendToId=message.getFromId();
    	this.senderType=message.getSenderType();
    }
    private Insets INSETS = new Insets(10, 10, 10, 10);

    @Override
    public void start(Stage primaryStage) {
        db = new dao.Database();
        if(senderType==1)
    	{
            /*
    		Doctor doc=db.getDoctorForPatientId(senderId);
    		this.sendToId=doc.getId();
    		this.sendToName=doc.getFirstName()+ " "+doc.getLastName();
    	    */
    	}else {
    		Patient pat = db.getPatient(sendToId);
    		this.sendToName=pat.getFirstName()+" "+pat.getLastName();
    	}
        primaryStage.setTitle("Message Form."); 

        Button btn1 = createButton("Send Message");
        TextField toName = new TextField();
        TextField subject = new TextField();
        TextField message = new TextField();
        toName.setText(sendToName);
        toName.setEditable(false);
        // Set prompt text for input fields
        subject.setPromptText("Enter Subject");
        message.setPromptText("Enter Message");

        BorderPane root = new BorderPane(); // Create a BorderPane layout

        VBox boxInput = new VBox();
        boxInput.getChildren().addAll(toName, subject, message, btn1);
        root.setCenter(boxInput); // Set input fields and button in the center of the BorderPane.

        // Create an event handler for "Login" button
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String msg = message.getText(); // Get Username from input
                String sub = subject.getText(); // Get Password from input

                db.sendMessage(senderType, senderId, sendToId, sub, msg);
                mm.childCallback();
           }
        });

        Scene scene = new Scene(root);

        primaryStage.setMinWidth(550); // Set minimum width for the window
        primaryStage.setMinHeight(400); // Set minimum height for the window
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
