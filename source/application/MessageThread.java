package application;

import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import models.Doctor;
import models.Message;
import models.Patient;

public class MessageThread extends Application {
    dao.Database db;
    MainMenu mm;
    List<Message>messages;
    models.Doctor doc;
    models.Patient pat;
    int portalType;
    public static void main(String[] args) {
        launch(args);
    }

    public MessageThread() {
    }

    public MessageThread(List<Message> messages,  MainMenu mm, Doctor doc, Patient pat, int portalType) {
    	this.mm=mm;
    	this.messages=messages;

        /*
    	this.senderType=message.getSenderType()==1?2:1;
    	this.sendToId=message.getFromId();
    	this.senderId=message.getToId();
         */

        this.doc = doc;
        this.pat = pat;
        this.portalType = portalType;
    }
    private Insets INSETS = new Insets(10, 10, 10, 10);

    @Override
    public void start(Stage primaryStage) {
        db = new dao.Database();
        // this.messages=db.getMessageThread(message.getRootMessageId());
        int count=0;
        ListView listView = new ListView();

        for (Message msg : messages)
        {
        	listView.getItems().add(msg.getListRow());
        	++count;
        }

        int picnum;
        Image img;
        if(portalType==2) {
//    		Doctor doc=db.getDoctorForPatientId(senderId);
//    		this.sendToId=doc.getId();
//    		this.sendToName=doc.getFirstName()+ " "+doc.getLastName();
    		picnum = doc.getId() % 4 + 1;
            img = new Image("file:resources/doctor" + picnum + ".png");

        } else {
//    		Patient pat = db.getPatient(sendToId);
//    		this.sendToName=pat.getFirstName()+" "+pat.getLastName();
    		picnum = pat.getId() % 3 + 1;
            img = new Image("file:resources/patient" + picnum + ".png");
    	}

        // primaryStage.setTitle("Message Form.");
        if(portalType == 1){
            primaryStage.setTitle("Messaging Patient " + pat.getFirstName() + " " + pat.getLastName());
        }
        else{
            primaryStage.setTitle("Messaging Doctor " + doc.getFirstName() + " " + doc.getLastName());
        }

        Button send_messageBtn = createButton("Send Message", "lightblue");
//        Button new_MessageBtn = createButton("New Thread", "azure");
        Button backBtn = createButton("Back", "#08080");

        // int picnum = pat.getId() % 4 + 1;
        // Image img = new Image("file:resources/doctor" + picnum + ".png");
        ImageView view = new ImageView(img);
        view.setFitHeight(100);
        view.setFitWidth(100);
        view.setPreserveRatio(true);

//        VBox new_reply_vbox = new VBox(new_MessageBtn, send_messageBtn);
//        HBox appoint_back_hbox = new HBox(listView, new_reply_vbox);

        Button profileBtn = new Button(null, view);
        if(portalType == 1) {
            profileBtn.setStyle("-fx-background-color:transparent; -fx-background-image: url('/patient" + picnum + ".png'); -fx-background-radius: 30%; -fx-background-position: center; -fx-background-repeat: no-repeat; -fx-background-size: 100;");
        }
        else{
            profileBtn.setStyle("-fx-background-color:transparent; -fx-background-image: url('/doctor" + picnum + ".png'); -fx-background-radius: 30%; -fx-background-position: center; -fx-background-repeat: no-repeat; -fx-background-size: 100;");
        }
        profileBtn.setMaxWidth(150);
        profileBtn.setMinWidth(150);
        profileBtn.setMaxHeight(150);
        profileBtn.setMinHeight(150);
        profileBtn.setContentDisplay(ContentDisplay.TOP);
        profileBtn.setPadding(Insets.EMPTY);

        GridPane grid = new GridPane();

        Label fromLabel = new Label("From");
        Label messageLabel = new Label("Message");
        Label replyLabel = new Label("Reply: ");

        TextField from = new TextField();
        TextField msg = new TextField();
        TextField reply = new TextField();

        from.setPrefWidth(350);

        fromLabel.setStyle("-fx-font-size: 15; -fx-text-fill: #000080;");
        messageLabel.setStyle("-fx-font-size: 15; -fx-text-fill: #000080;");
        replyLabel.setStyle("-fx-font-size: 15; -fx-text-fill: #000080;");

        from.setDisable(true);
        msg.setDisable(true);

        grid.add(fromLabel, 0, 0);
        grid.add(messageLabel, 0, 1);
        grid.add(replyLabel, 0, 2);

        grid.add(from, 1, 0);
        grid.add(msg, 1, 1);
        grid.add(reply, 1, 2);

        /*
        if (portalType == 1) {
            from.setText("Doctor");
        } else {
            from.setText("Patient");
        }
        */

        BorderPane root = new BorderPane(); // Create a BorderPane layout

        listView.setMaxHeight(100);

        VBox boxInput = new VBox();
        boxInput.getChildren().addAll(profileBtn, listView, grid, send_messageBtn, backBtn);
        boxInput.setAlignment(Pos.CENTER);
        boxInput.setSpacing(10);

        root.setCenter(boxInput); // Set input fields and button in the center of the BorderPane.

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
            	int idx=listView.getSelectionModel().getSelectedIndex();
              	if(idx<0)
            	{
            		return;
            	}
            	Message massage = messages.get(idx);
                from.setText((massage.getSenderType() == 1)?"Pat. " + pat.getFirstName():"Dr. " + doc.getFirstName());
            	msg.setText(massage.getMessage());
            	System.out.println(msg);
            }
        });

        // Create an event handler for "Login" button
        send_messageBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int lastIndex = listView.getItems().size() - 1;
                String msg = reply.getText(); // Get Username from input
                String sub = "Re: "+ messages.get(lastIndex).getSubject(); // Get Password from input

                db.sendMessage(messages.get(0).getRootMessageId(), (portalType == 1)?2:1, (portalType == 1)?doc.getId():pat.getId(), (portalType == 1)?pat.getId():doc.getId(), sub, msg);
                List<Message> temp = db.getMessages(pat.getId(), doc.getId());

                listView.getItems().add(temp.get(temp.size() - 1).getListRow());
                messages.add(temp.get(temp.size() - 1));
                // mm.childCallback(senderType);
           }
        });

        backBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.close(); // Actually closes the scene
            }
        });

        Scene scene = new Scene(root);

        primaryStage.setMinWidth(550); // Set minimum width for the window
        primaryStage.setMinHeight(400); // Set minimum height for the window
        primaryStage.setScene(scene);
        primaryStage.show();
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
