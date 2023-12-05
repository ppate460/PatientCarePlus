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
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import models.Doctor;
import models.Message;
import models.Patient;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatientView extends Application {
    models.Patient pat;
    MainMenu mm;
    dao.Database db;


    public PatientView(MainMenu mm, Patient pat){
        this.mm = mm;
        this.pat = pat;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Patient Portal");
        db = new Database();

        // Load the background image
        Image backgroundImage = new Image(getClass().getResource("/patient_background.jpg").toExternalForm());

        // Create an ImageView to display the background image
        ImageView backgroundImageView = new ImageView(backgroundImage);

        // Set the preserve ratio property to maintain the aspect ratio
        backgroundImageView.setPreserveRatio(true);

        // Set the dimensions of the background image within the scene
        backgroundImageView.setFitWidth(1100);
        backgroundImageView.setFitHeight(650);
        backgroundImageView.setOpacity(0.50);

        TextField name = new TextField();
        name.setText("Welcome Patient " + pat.getFirstName() + " " + pat.getLastName() + " to Patient Care+!");
        name.setDisable(true);
        name.setOpacity(1.0);
        // name.setStyle("fx-font-size: 64");
//        name.setBackground(Background.(new Color(0,0,0,0)));
        name.setFont(new Font(28));
        name.setPrefSize(600,72);
        name.setPadding(new Insets(5,5,5,5));

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

        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle("-fx-background-color: mistyrose; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-radius: 5;");
        logoutBtn.setMaxWidth(120);
        logoutBtn.setMinWidth(120);
        logoutBtn.setMaxHeight(50);
        logoutBtn.setMinHeight(50);
        logoutBtn.setPadding(new Insets(5, 5, 5, 5));
        logoutBtn.textAlignmentProperty().set(TextAlignment.CENTER);

        Button inputVitalsBtn = new Button("Upload Vitals");
        inputVitalsBtn.setStyle("-fx-background-color: lightblue; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-radius: 5;");
        inputVitalsBtn.setMaxWidth(120);
        inputVitalsBtn.setMinWidth(120);
        inputVitalsBtn.setMaxHeight(50);
        inputVitalsBtn.setMinHeight(50);
        inputVitalsBtn.setPadding(new Insets(5, 5, 5, 5));
        inputVitalsBtn.textAlignmentProperty().set(TextAlignment.CENTER);
        inputVitalsBtn.setAlignment(Pos.CENTER);

        inputVitalsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PatientVitals pv = new PatientVitals(PatientView.this.pat);
                try {
                    Stage s = new Stage();
                    s.setTitle("Patient Profile");
                    pv.start(s);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PatientView.this.mm.start(primaryStage); // Does NOT close the scene anymore
                Alert logoutSuccessful = new Alert(Alert.AlertType.INFORMATION);
                logoutSuccessful.setTitle("Logout Successful!");
                logoutSuccessful.setHeaderText("Click OK to continue...");
                logoutSuccessful.showAndWait();
            }
        });

        profileBtn.setOnAction(new EventHandler<ActionEvent>() {
            PatientPortal pp = new PatientPortal(PatientView.this.pat);
            @Override
            public void handle(ActionEvent event) {
                try {
                    Stage s = new Stage();
                    s.setTitle("Patient Profile");
                    pp.start(s);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        HBox topbar = new HBox();
        topbar.getChildren().addAll(name, profileBtn);
        topbar.setAlignment(Pos.CENTER);

        TextInputDialog td = new TextInputDialog("");

        td.setHeaderText("Connect to a doctor, enter a Doctor ID!");

        Button add_doctor = new Button("Add Doctor");
        add_doctor.setAlignment(Pos.CENTER);
        add_doctor.setStyle("-fx-background-color: lightcyan; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-radius: 5;");
        add_doctor.setMaxWidth(120);
        add_doctor.setMinWidth(120);
        add_doctor.setMaxHeight(50);
        add_doctor.setMinHeight(50);
        add_doctor.setPadding(new Insets(5, 5, 5, 5));
        add_doctor.textAlignmentProperty().set(TextAlignment.CENTER);

        HBox bottomBar = new HBox(add_doctor, inputVitalsBtn, logoutBtn);
        bottomBar.setSpacing(15);
        bottomBar.setAlignment(Pos.CENTER);

        VBox docs = getDoctors();
        VBox doctor_list = new VBox();
        doctor_list.getChildren().addAll(topbar);
        if(docs != null){
            doctor_list.getChildren().add(docs);
        }
        doctor_list.getChildren().addAll(bottomBar);
        doctor_list.setAlignment(Pos.TOP_CENTER);
        doctor_list.setSpacing(10);

        StackPane root = new StackPane(backgroundImageView, doctor_list);

        EventHandler<ActionEvent> event = new EventHandler<>(){
            public void handle(ActionEvent e){
                td.showAndWait();
                // Get text and make sure it is an integer
                String did = td.getEditor().getText();
                String regex = "[0-9]";

                if (did.matches(regex)){
                    // Create a data entry in connections
                    int id = Integer.parseInt(did);

                    Doctor doc = db.getDoctor(id);

                    if (doc != null){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Connecting to Doctor " + doc.getLastName() + ".");
                        alert.setHeaderText("Are you sure you want to connect?");
                        // alert.setContentText("C:/MyFile.txt");

                        Optional<ButtonType> option = alert.showAndWait();

                        boolean owo2 = false;
                        if(option.get() == ButtonType.OK){
                            String sql = "insert into connections (patient_id, doctor_id) values (?, ?)";
                            Database owo = new Database();
                            PreparedStatement ps = owo.prepare(sql);
                            try {
                                ps.setString(1, "" + pat.getId());
                                ps.setString(2, "" + doc.getId());
                                ps.executeUpdate();
                                owo2 = true;

//                                VBox newDocs = getDoctors();
//                                doctor_list.getChildren().remove(docs);
//                                doctor_list.getChildren().add(1, newDocs);
                                refresh(doctor_list);

                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                        if(!owo2){
                            Alert err = new Alert(Alert.AlertType.ERROR);
                            err.setTitle("Oh no! Something went wrong!");
                            err.showAndWait();
                        }
                    }

                }

                // Reset the prompt in td
                td.setContentText("");
            }
        };

        add_doctor.setOnAction(event);

        Scene scene = new Scene(root, 1100, 600);
        primaryStage.setScene(scene);
//        primaryStage.show();  // Most likely will have to comment this out

    }

    VBox getDoctors(){
        VBox docs = new VBox();

        ArrayList<Doctor> connectedDoctors = db.getDoctorForPatientId(pat.getId());
        if(db != null && connectedDoctors != null){
            for(Doctor doc : connectedDoctors){
                // System.out.printf("Connected to Doctor %d\n", doc.getId());
                int picdoc = doc.getId() % 4 + 1;
                Button face = new Button();
                face.setStyle("-fx-background-color:transparent; -fx-background-image: url('/doctor" + picdoc + ".png'); -fx-background-radius: 50%; -fx-background-position: center; -fx-background-repeat: no-repeat; -fx-background-size: 100;");
                face.setPrefSize(100,100);

                TextField name = new TextField("Dr. " + doc.getFirstName() + " " + doc.getLastName());
                name.setDisable(true);
                name.setPrefSize(200, 40);
                name.setFont(new Font(20));
                name.setAlignment(Pos.CENTER);
                name.setOpacity(1.0);
                name.setPadding(new Insets (10, 10, 10, 10));

                Button details = new Button("Details");
                details.setPrefSize(100, 40);
                details.setMinHeight(doc.getId());
                details.setPadding(new Insets(5, 5, 5, 5));
                details.textAlignmentProperty().set(TextAlignment.CENTER);
                details.setStyle("-fx-background-color: honeydew; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-radius: 5;");

                details.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        Button b = (Button) event.getSource();
                        int did = (int) b.getMinHeight();
                        Doctor d = db.getDoctor(did);

                        DoctorDetails dd = new DoctorDetails(d, PatientView.this.pat);
                        try {
                            Stage s = new Stage();
                            s.setTitle("Doctor Details");
                            dd.start(s);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });


                HBox temp = new HBox(face, name, details);
                temp.setSpacing(15);
                temp.setAlignment(Pos.CENTER);
                // temp.setBackground(Background.fill(Color.HONEYDEW));
                // temp.setOpacity(0.7);

                docs.getChildren().add(temp);
            }
        }
        else{
            return null;
        }

        docs.setAlignment(Pos.CENTER);
        docs.setSpacing(12);
        return docs;
    }

    void refresh(VBox dl){
        int size = dl.getChildren().size();

        if(size == 4){
            dl.getChildren().remove(1);
        }
        VBox newDocs = getDoctors();
        dl.getChildren().add(1, newDocs);
    }



}
