package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;


public class MainMenu extends Application {
    dao.Database db;
    Stage primaryStage;
    models.Patient patient = null;
    models.Doctor doctor = null;
    LoginForm loginForm;
    AddUser addUser;
    PatProfile patProfile;
    DocProfile docProfile;

    public static void main(String[] args) {
        launch(args);
    }

    private Insets INSETS = new Insets(10, 10, 10, 10);

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        db = new dao.Database();
        primaryStage.setTitle("Main Menu Login");

        loginForm = new LoginForm(this);
        addUser = new AddUser();

        Text title = new Text("Welcome to Patient Care+");
        title.getStyleClass().add("title");
        title.setStyle("-fx-font-size: 35; -fx-fill: #0072e3;");

        // Load the background image
        Image backgroundImage = new Image(getClass().getResource("/resources/mobile_health-01.png").toExternalForm());

        // Create an ImageView to display the background image
        ImageView backgroundImageView = new ImageView(backgroundImage);

        // Set the preserve ratio property to maintain the aspect ratio
        backgroundImageView.setPreserveRatio(true);

        // Set the dimensions of the background image within the scene
        backgroundImageView.setFitWidth(1100);
        backgroundImageView.setFitHeight(650);

        Button btn1 = createButton("Login", "#cc0000");
        Button btn2 = createButton("Add User", "#ff9900");
        Button btn3 = createButton("Forgot Password", "#ff9999");
        Button btn4 = createButton("Add Patient", "#4ade94");
        Button btn5 = createButton("Add Doctor", "#de4a94");

        btn1.setMinWidth(180);
        btn1.setMaxWidth(180);
        btn2.setMinWidth(180);
        btn2.setMaxWidth(180);
        btn3.setMinWidth(180);
        btn3.setMaxWidth(180);
        btn4.setMinWidth(180);
        btn4.setMaxWidth(180);
        btn5.setMinWidth(180);
        btn5.setMaxWidth(180);

        BorderPane root = new BorderPane(); // Create a BorderPane layout

        VBox boxInput = new VBox();
        boxInput.setAlignment(Pos.CENTER);
        boxInput.setSpacing(23);

        boxInput.getChildren().addAll(title, btn1, btn2, btn4, btn5, btn3);

        root.setCenter(boxInput); // Set buttons in the center.

        // Create event handlers for buttons
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    loginForm.start(primaryStage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AddUser au = new AddUser();
                au.start(primaryStage);
            }
        });
        btn3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ForgotPassword fp = new ForgotPassword(MainMenu.this);
                fp.start(primaryStage);
            }
        });
        btn4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                AddPatient ap = new AddPatient();
                ap.start(primaryStage);
            }
        });
        btn5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                AddDoctor ad = new AddDoctor();
                ad.start(primaryStage);
            }
        });


        StackPane sp = new StackPane();
        sp.getChildren().addAll(backgroundImageView, root);

        Scene scene = new Scene(sp, 1100, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void logout()
    {
    	System.out.println("logout");
    	this.patProfile=null;
    	this.start(primaryStage);
    	this.patient=null;
    	this.doctor=null;
    }
    public void authResponse(models.Patient pat) throws Exception {
        if (pat == null) {
            return;
        }
        this.patient = pat;
        PatientView pv = new PatientView(this, pat);
        pv.start(this.primaryStage);

        // patProfile = new PatProfile(this, pat);
        // patProfile.start(this.primaryStage);
        System.out.printf("authResponse: %s", pat);
    }
    
    public void authResponse(models.Doctor doc) throws Exception {
        if (doc == null) {
            return;
        }
        this.doctor = doc;
        DoctorView dv = new DoctorView(this, doc);
        dv.start(this.primaryStage);

//        docProfile = new DocProfile(this, doc);
//        docProfile.start(this.primaryStage);
        System.out.printf("authResponse: %s", doc);
    }

    public void childCallback() {
        this.start(primaryStage);
    }
    public void childCallback(int next) {
        if(next==1) // patient
        {
        	 patProfile.start(this.primaryStage);
        }else {
        	docProfile.start(this.primaryStage);
        }
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
}
