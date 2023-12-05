package application;

import dao.Database;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import models.Doctor;
import models.Message;
import models.Patient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PatientDetails extends Application {
    models.Patient pat;
    models.Doctor doc;
    Database db;
    MainMenu mm;

    private Insets INSETs = new Insets(10,10,10,10);

    public PatientDetails(Patient pat, Doctor doc){
        this.pat = pat;
        this.doc = doc;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        db = new dao.Database();
        primaryStage.setTitle("Patient Details");

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

        Button backButton = createButton("Back", "gray");
        Button appointmentBtn = createButton("Schedule Appointment", "#4adede");
        Button replyBtn = createButton("Reply", "#e7c0a4");

        TextField email = new TextField();
        TextField lastName = new TextField();
        TextField firstName = new TextField();
        TextField phoneNumber = new TextField();
        TextField emergencyContact = new TextField();
        TextField doctorNotes = new TextField();

        email.setDisable(true);
        email.setOpacity(1.0);
        lastName.setDisable(true);
        lastName.setOpacity(1.0);
        firstName.setDisable(true);
        firstName.setOpacity(1.0);
        phoneNumber.setDisable(true);
        phoneNumber.setOpacity(1.0);
        emergencyContact.setDisable(true);
        emergencyContact.setOpacity(1.0);
        doctorNotes.setDisable(true);
        doctorNotes.setOpacity(1.0);

        email.setText(pat.getEmail());
        lastName.setText(pat.getLastName());
        firstName.setText(pat.getFirstName());
        phoneNumber.setText(pat.getEmergencyPhone());
        emergencyContact.setText(pat.getEmergencyContact());
        doctorNotes.setText(pat.getDoctorsNotes());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        Label emailLabel = new Label("Email: ");
        Label lnLabel = new Label("Last Name: ");
        Label fnLabel = new Label("First Name: ");
        Label phoneLabel = new Label("Phone: ");
        Label emergencyLabel = new Label("Em. Contact: ");
        Label notesLabel = new Label("Summary: ");

        emailLabel.setStyle("-fx-font-size: 15; -fx-text-fill: #000080;");
        lnLabel.setStyle("-fx-font-size: 15; -fx-text-fill: #000080;");
        fnLabel.setStyle("-fx-font-size: 15; -fx-text-fill: #000080;");
        phoneLabel.setStyle("-fx-font-size: 15; -fx-text-fill: #000080;");
        emergencyLabel.setStyle("-fx-font-size: 15; -fx-text-fill: #000080;");
        notesLabel.setStyle("-fx-font-size: 15; -fx-text-fill: #000080;");

        grid.add(emailLabel, 0, 2);
        grid.add(lnLabel, 2, 0);
        grid.add(fnLabel, 0, 0);
        grid.add(phoneLabel, 0, 1);
        grid.add(emergencyLabel, 2, 1);
        grid.add(notesLabel, 0, 3);

        grid.add(email, 1, 2);
        grid.add(lastName, 3, 0);
        grid.add(firstName, 1, 0);
        grid.add(phoneNumber, 1, 1);
        grid.add(emergencyContact, 3, 1);
        grid.add(doctorNotes, 1, 3);

        emailLabel.setAlignment(Pos.CENTER_LEFT);
        lnLabel.setAlignment(Pos.CENTER_LEFT);
        fnLabel.setAlignment(Pos.CENTER_LEFT);
        phoneLabel.setAlignment(Pos.CENTER_LEFT);
        emergencyLabel.setAlignment(Pos.CENTER_LEFT);
        notesLabel.setAlignment(Pos.CENTER_LEFT);

        email.setPrefWidth(200);
        lastName.setPrefWidth(200);
        firstName.setPrefWidth(200);
        phoneNumber.setPrefWidth(200);
        emergencyContact.setPrefWidth(200);
        doctorNotes.setPrefWidth(200);

        profileBtn.setAlignment(Pos.CENTER);

        ListView<String> listView = new ListView<>();
        int count=0;
        List<Message> messages = db.getMessages(pat.getId(), doc.getId());

        if (messages != null) {
            Message msg;
            for (int i=0; i<messages.size(); i++) {
                msg = messages.get(i);
                if(!msg.getSubject().startsWith("Re:")){
                    listView.getItems().add(msg.getListRow());
                    ++count;
                }
            }
        }

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("OwO");

            }
        });

        if (count < 4) {
            listView.setMaxHeight(100);
        }
        else {
            listView.setMaxHeight(25*count);
        }

        replyBtn.setOnAction(new EventHandler<ActionEvent>() {
            //            MessageThread mt = new MessageThread(msg, mm)
            @Override
            public void handle(ActionEvent event) {
                int idx=listView.getSelectionModel().getSelectedIndex();
                if(idx<0)
                {
                    return;
                }

                List<Message> msgs = db.getMessages(pat.getId(), doc.getId());
                String subject = (listView.getSelectionModel().getSelectedItem()).substring(22);

                Message msg;
                for(int i = msgs.size() - 1; i >= 0; i--){
                    msg = msgs.get(i);
                    if(!msg.getSubject().contains(subject)){
                        msgs.remove(i);
                    }
                }

                System.out.println(subject);
                for(int i = 0; i < msgs.size(); i++){
                    System.out.println(msgs.get(i).getSubject());
                }

                // System.out.println(msg);
                MessageThread mt = new MessageThread(msgs,mm,doc,pat,1);
                Stage Stage = new Stage();

                mt.start(Stage);
            }
        });

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.close(); // Actually closes the scene
            }
        });

        Button new_MessageBtn = createButton("New Thread", "#f3bed9");
        new_MessageBtn.setPrefHeight(45);
        replyBtn.setPrefHeight(45);

        Button view_vitals = createButton("View Pat. Vitals", "#8beaba");
        view_vitals.setPrefHeight(45);

        VBox new_reply_vbox = new VBox(new_MessageBtn, replyBtn);
        new_reply_vbox.setSpacing(10);
        new_reply_vbox.setAlignment(Pos.CENTER);
        HBox appoint_back_hbox = new HBox(listView, new_reply_vbox);
        appoint_back_hbox.setAlignment(Pos.CENTER);
        appoint_back_hbox.setSpacing(20);
        HBox appoint_backBtn = new HBox(appointmentBtn, view_vitals, backButton);
        appoint_backBtn.setAlignment(Pos.CENTER);
        appoint_backBtn.setSpacing(10);

        view_vitals.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String sql = "select distinct patient_id, doctor_id from consent where patient_id = ? and doctor_id = ?;";

                PreparedStatement ps = db.prepare(sql);
                try {
                    ps.setString(1, "" + pat.getId());
                    ps.setString(2, "" + doc.getId());

                    ResultSet rs = ps.executeQuery();
                    if(rs == null || !rs.next()){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Patient " + pat.getId() + " has not given you consent to view their vitals");
                        alert.setHeaderText("Please try again later, after reaching out to the patient.");
                        alert.showAndWait();
                    }
                    else{
                        ShowVitals sv = new ShowVitals(pat);
                        Stage s = new Stage();
                        s.setTitle("");
                        sv.start(s);

                    }


                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        new_MessageBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int lastIndex = listView.getItems().size() - 1;
                TextInputDialog td1 = new TextInputDialog("Enter Subject");
                td1.setHeaderText("");
                td1.setTitle("Enter Subject");
                td1.showAndWait();
                String sub = td1.getEditor().getText();
                TextInputDialog td2 = new TextInputDialog("Enter Message");
                td2.setTitle("Enter Message");
                td2.setHeaderText("Subject: " + sub);
                td2.showAndWait();
                String msg = td2.getEditor().getText();


                // String msg = reply.getText(); // Get Username from input
                // String sub = "Re: "+ messages.get(lastIndex).getSubject(); // Get Password from input

                db.sendMessage(2, doc.getId(), pat.getId(), sub, msg);
                List<Message> temp = db.getMessages(pat.getId(), doc.getId());

                listView.getItems().add(temp.get(temp.size() - 1).getListRow());
                messages.add(temp.get(temp.size() - 1));
                // mm.childCallback(senderType);
            }

        });

        VBox root = new VBox(profileBtn, grid, appoint_back_hbox, appoint_backBtn);
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(10);

        Scene scene = new Scene(root, 800, 600);
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
        BorderPane.setMargin(button, INSETs);
        BorderPane.setAlignment(button, Pos.CENTER);
        return button;
    }
}