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

import static javafx.scene.layout.Background.*;

public class DoctorView extends Application {
    models.Doctor doc;
    MainMenu mm;
    dao.Database db;


    public DoctorView(MainMenu mm, Doctor doc){
        this.mm = mm;
        this.doc = doc;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Doctor Portal");
        db = new Database();

        // Load the background image
        Image backgroundImage = new Image(getClass().getResource("/doctor_background.jpeg").toExternalForm());

        // Create an ImageView to display the background image
        ImageView backgroundImageView = new ImageView(backgroundImage);

        // Set the preserve ratio property to maintain the aspect ratio
        backgroundImageView.setPreserveRatio(true);

        // Set the dimensions of the background image within the scene
        backgroundImageView.setFitWidth(1100);
        backgroundImageView.setFitHeight(650);
        backgroundImageView.setOpacity(0.50);

        TextField name = new TextField();
        name.setText("Welcome Doctor " + doc.getFirstName() + " " + doc.getLastName() + " to Patient Care+!");
        name.setDisable(true);
        name.setOpacity(1.0);
        // name.setStyle("fx-font-size: 64");
//        name.setBackground(Background.fill(new Color(0,0,0,0)));
        name.setFont(new Font(28));
        name.setPrefSize(600,72);
        name.setPadding(new Insets(5,5,5,5));

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

        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle("-fx-background-color: mistyrose; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-radius: 5;");
        logoutBtn.setMaxWidth(120);
        logoutBtn.setMinWidth(120);
        logoutBtn.setMaxHeight(50);
        logoutBtn.setMinHeight(50);
        logoutBtn.setPadding(new Insets(5, 5, 5, 5));
        logoutBtn.textAlignmentProperty().set(TextAlignment.CENTER);

        logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DoctorView.this.mm.start(primaryStage); // Does NOT close the scene anymore
                Alert logoutSuccessful = new Alert(Alert.AlertType.INFORMATION);
                logoutSuccessful.setTitle("Logout Successful!");
                logoutSuccessful.setHeaderText("Click OK to continue...");
                logoutSuccessful.showAndWait();
            }
        });

        profileBtn.setOnAction(new EventHandler<ActionEvent>() {
            DoctorPortal pp = new DoctorPortal(DoctorView.this.doc);
            @Override
            public void handle(ActionEvent event) {
                try {
                    Stage s = new Stage();
                    s.setTitle("Doctor Profile");
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

        td.setHeaderText("Connect to a patient, enter a Patient ID!");

        Button add_patient = new Button("Add Patient");
        add_patient.setAlignment(Pos.CENTER);
        add_patient.setStyle("-fx-background-color: lightcyan; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-radius: 5;");
        add_patient.setMaxWidth(120);
        add_patient.setMinWidth(120);
        add_patient.setMaxHeight(50);
        add_patient.setMinHeight(50);
        add_patient.setPadding(new Insets(5, 5, 5, 5));
        add_patient.textAlignmentProperty().set(TextAlignment.CENTER);

        VBox pats = getPatients();
        VBox patient_list = new VBox();
        patient_list.getChildren().add(topbar);
        if(pats != null){
            patient_list.getChildren().add(pats);
        }
        patient_list.getChildren().addAll(add_patient, logoutBtn);
        patient_list.setAlignment(Pos.TOP_CENTER);
        patient_list.setSpacing(10);

        StackPane root = new StackPane(backgroundImageView, patient_list);

        EventHandler<ActionEvent> event = new EventHandler<>(){
            public void handle(ActionEvent e){
                td.showAndWait();
                // Get text and make sure it is an integer
                String did = td.getEditor().getText();
                String regex = "[0-9]";

                if (did.matches(regex)){
                    // Create a data entry in connections
                    int id = Integer.parseInt(did);

                    Patient pat = db.getPatient(id);

                    if (doc != null){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Connecting to Patient " + pat.getLastName() + ".");
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
                                refresh(patient_list);

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

        add_patient.setOnAction(event);

        Scene scene = new Scene(root, 1100, 600);
        primaryStage.setScene(scene);
//        primaryStage.show();  // Most likely will have to comment this out

    }

    VBox getPatients(){
        VBox pats = new VBox();

        ArrayList<Patient> connectedPatients = db.getPatientForDoctorId(doc.getId());
        if(db != null && connectedPatients != null){
            for(Patient pat: connectedPatients){
                // System.out.printf("Connected to Doctor %d\n", doc.getId());
                int picpat = pat.getId() % 3 + 1;
                Button face = new Button();
                face.setStyle("-fx-background-color:transparent; -fx-background-image: url('/patient" + picpat + ".png'); -fx-background-radius: 50%; -fx-background-position: center; -fx-background-repeat: no-repeat; -fx-background-size: 100;");
                face.setPrefSize(100,100);

                TextField name = new TextField(pat.getFirstName() + " " + pat.getLastName());
                name.setDisable(true);
                name.setPrefSize(200, 40);
                name.setFont(new Font(20));
                name.setAlignment(Pos.CENTER);
                name.setOpacity(1.0);
                name.setPadding(new Insets (10, 10, 10, 10));

                Button details = new Button("Details");
                details.setPrefSize(100, 40);
                details.setMinHeight(pat.getId());
                details.setPadding(new Insets(5, 5, 5, 5));
                details.textAlignmentProperty().set(TextAlignment.CENTER);
                details.setStyle("-fx-background-color: honeydew; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-radius: 5;");

                details.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        Button b = (Button) event.getSource();
                        int pid = (int) b.getMinHeight();
                        Patient p = db.getPatient(pid);

                        PatientDetails pd = new PatientDetails(p, DoctorView.this.doc);
                        try {
                            Stage s = new Stage();
                            s.setTitle("Patient Details");
                            pd.start(s);
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

                pats.getChildren().add(temp);
            }
        }
        else{
            return null;
        }

        pats.setAlignment(Pos.CENTER);
        pats.setSpacing(12);
        return pats;
    }

    void refresh(VBox pl){
        int size = pl.getChildren().size();

        if(size == 4){
            pl.getChildren().remove(1);
        }
        VBox newPats = getPatients();
        pl.getChildren().add(1, newPats);
    }



}
