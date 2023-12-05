package application;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenuController implements Initializable {
	private static LoginForm lf = null;
    private static Stage primaryStage = null;
	
    @FXML
    private BorderPane root;

    @FXML
    private VBox boxInput;

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private Button btn4;

    @FXML
    private Button btn5;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setButtonStyle(btn1, "#4CAF50"); // Green
        setButtonStyle(btn2, "#2196F3"); // Blue
        setButtonStyle(btn3, "#FF9999"); // REd
        setButtonStyle(btn4, "#E91E63"); // Pink
        setButtonStyle(btn5, "#808080"); // Gray
    }

    private void setButtonStyle(Button button, String backgroundColor) {
        button.setStyle("-fx-background-color: " + backgroundColor + "; -fx-text-fill: black; -fx-font-size: 18;");
        button.setMinHeight(50);
        button.setMinWidth(200);
    }

    public void openLoginForm(ActionEvent e) throws Exception {
        if(primaryStage == null){
            primaryStage = new Stage();
        }
        MainMenu mm = new MainMenu();
        // Stage stage = new Stage();
        mm.start(primaryStage);

        LoginForm loginForm = new LoginForm(mm);

        primaryStage.setTitle("Login");
        loginForm.start(primaryStage);
    }
    
    public void openAddUser(ActionEvent e) throws IOException {
        if(primaryStage == null){
            primaryStage = new Stage();
        }
    	// Create a new instance of the AddUser class
        AddUser addUserInfo = new AddUser();
        // Create a new stage for the AddUser view
        // Stage stage = new Stage();
        primaryStage.setTitle("Add User");
        addUserInfo.start(primaryStage);
    }

    public void register(ActionEvent e) throws IOException {
        // Register action
    }

    // Handle the "Create Patient Account" button click
    public void createPatientAccount(ActionEvent e) throws IOException {
        // Create a new instance of the AddPatient class
        AddPatient addPatient = new AddPatient();

        // Create a new stage for the AddPatient view
        Stage stage = new Stage();
        stage.setTitle("Create Patient Account");
        addPatient.start(stage);
    }

    // Handle the "Create Doctor Account" button click
    public void createDoctorAccount(ActionEvent e) throws IOException {
        // Create a new instance of the AddDoctor class
        AddDoctor addDoctor = new AddDoctor();

        // Create a new stage for the AddDoctor view
        Stage stage = new Stage();
        stage.setTitle("Create Doctor Account");
        addDoctor.start(stage);
    }
    public void resetPassword(ActionEvent e) throws IOException {
        // Reset password action
        ForgotPassword forgotPassword = new ForgotPassword();

        Stage stage = new Stage();
        stage.setTitle("Forgot Password");
        forgotPassword.start(stage);
    }

}