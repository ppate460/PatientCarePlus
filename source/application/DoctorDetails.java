package application;

import dao.Database;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import models.Doctor;
import models.Message;
import models.Patient;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorDetails extends Application {
    // public dao.Database db;
    // public models.Patient patient;
    models.Patient pat;
    models.Doctor doc;
    Database db;
    MainMenu mm;

    private Insets INSETS = new Insets(10, 10, 10, 10);

    public DoctorDetails(Doctor doc, Patient pat) {
        this.doc = doc;
        this.pat = pat;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        db = new dao.Database();
        primaryStage.setTitle("Doctor Details");

        TextField temptxt = new TextField("OwO");

        int picnum = doc.getId() % 4 + 1;
        Image img = new Image("file:resources/doctor" + picnum + ".png");
        ImageView view = new ImageView(img);
        view.setFitHeight(200);
        view.setFitWidth(200);
        view.setPreserveRatio(true);

        Button profileBtn = new Button(null, view);
        profileBtn.setStyle( "-fx-background-color:transparent; -fx-background-image: url('/doctor" + picnum + ".png'); -fx-background-radius: 50%; -fx-background-position: center; -fx-background-repeat: no-repeat; -fx-background-size: 150;");
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
        TextField emergencyPhone = new TextField();
        TextField workAddress = new TextField();
        TextField certifications = new TextField();

        email.setDisable(true);
        email.setOpacity(1.0);
        lastName.setDisable(true);
        lastName.setOpacity(1.0);
        firstName.setDisable(true);
        firstName.setOpacity(1.0);
        emergencyPhone.setDisable(true);
        emergencyPhone.setOpacity(1.0);
        workAddress.setDisable(true);
        workAddress.setOpacity(1.0);
        certifications.setDisable(true);
        certifications.setOpacity(1.0);

        email.setText(doc.getEmail());
        lastName.setText(doc.getLastName());
        firstName.setText(doc.getFirstName());
        emergencyPhone.setText(doc.getPhone());
        workAddress.setText(doc.getWorkAddress());
        certifications.setText((doc.getCertification()));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        Label emailLabel = new Label("Email: ");
        Label lnLabel = new Label("Last Name: ");
        Label fnLabel = new Label("First name: ");
        Label phone = new Label("Phone: ");
        Label workAddressLabel = new Label("Work Address: ");
        Label certificationsLabel = new Label("Certifications: ");

        emailLabel.setStyle("-fx-font-size: 15; -fx-text-fill: #000080;");
        lnLabel.setStyle("-fx-font-size: 15; -fx-text-fill: #000080;");
        fnLabel.setStyle("-fx-font-size: 15; -fx-text-fill: #000080;");
        phone.setStyle("-fx-font-size: 15; -fx-text-fill: #000080;");
        workAddressLabel.setStyle("-fx-font-size: 15; -fx-text-fill: #000080;");
        certificationsLabel.setStyle("-fx-font-size: 15; -fx-text-fill: #000080;");

        grid.add(emailLabel, 2, 1);
        grid.add(lnLabel, 2, 0);
        grid.add(fnLabel, 0, 0);
        grid.add(phone, 0, 1);
        grid.add(workAddressLabel, 0, 2);
        grid.add(certificationsLabel, 0, 3);

        grid.add(email, 3, 1);
        grid.add(lastName, 3, 0);
        grid.add(firstName, 1, 0);
        grid.add(emergencyPhone, 1, 1);
        grid.add(workAddress, 1, 2);
        grid.add(certifications, 1, 3);

        emailLabel.setAlignment(Pos.CENTER_LEFT);
        lnLabel.setAlignment(Pos.CENTER_LEFT);
        fnLabel.setAlignment(Pos.CENTER_LEFT);
        phone.setAlignment(Pos.CENTER_LEFT);
        workAddressLabel.setAlignment(Pos.CENTER_LEFT);
        certificationsLabel.setAlignment(Pos.CENTER_LEFT);

        email.setPrefWidth(200);
        lastName.setPrefWidth(200);
        firstName.setPrefWidth(200);
        emergencyPhone.setPrefWidth(200);
        workAddress.setPrefWidth(200);
        certifications.setPrefWidth(200);

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
                MessageThread mt = new MessageThread(msgs,mm,doc,pat,2);
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

        // Load the background image
        Image backgroundImage = new Image(getClass().getResource("/certification" + picnum + ".jpg").toExternalForm());

        // Create an ImageView to display the background image
        ImageView backgroundImageView = new ImageView(backgroundImage);

        // Set the preserve ratio property to maintain the aspect ratio
        backgroundImageView.setPreserveRatio(true);

        // Set the dimensions of the background image within the scene
        backgroundImageView.setFitWidth(1100);
        backgroundImageView.setFitHeight(650);
        backgroundImageView.setOpacity(0.50);

        Button new_MessageBtn = createButton("New Thread", "#f3bed9");
        new_MessageBtn.setPrefHeight(45);
        replyBtn.setPrefHeight(45);

        Button give_consent = createButton("Give Consent", "#8beaba");
        give_consent.setPrefHeight(45);

        VBox new_reply_vbox = new VBox(new_MessageBtn, replyBtn);
        new_reply_vbox.setSpacing(10);
        new_reply_vbox.setAlignment(Pos.CENTER);
        HBox appoint_back_hbox = new HBox(listView, new_reply_vbox);
        appoint_back_hbox.setAlignment(Pos.CENTER);
        appoint_back_hbox.setSpacing(20);
        HBox appoint_backBtn = new HBox(appointmentBtn, give_consent, backButton);
        appoint_backBtn.setAlignment(Pos.CENTER);
        appoint_backBtn.setSpacing(10);


        give_consent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String sql = "insert into consent (patient_id, doctor_id) values (?, ?);";
                PreparedStatement ps = db.prepare(sql);

                try {
                    ps.setString(1, "" + pat.getId());
                    ps.setString(2, "" + doc.getId());
                    ps.executeUpdate();
                } catch (SQLException e) {
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

                db.sendMessage(1, pat.getId(), doc.getId(), sub, msg);
                List<Message> temp = db.getMessages(pat.getId(), doc.getId());

                listView.getItems().add(temp.get(temp.size() - 1).getListRow());
                messages.add(temp.get(temp.size() - 1));
                // mm.childCallback(senderType);
            }
        });


        VBox root = new VBox(profileBtn, grid, appoint_back_hbox, appoint_backBtn);
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(10);
        StackPane sp = new StackPane(backgroundImageView, root);
        Scene scene = new Scene(sp, 800, 600);
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