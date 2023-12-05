package application;

import dao.Database;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import models.Patient;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientVitals extends Application{
    models.Patient pat;
    Database db;
    MainMenu mm;
    int num;

    private Insets INSETS = new Insets(10, 10, 10, 10);

    public PatientVitals(Patient pat){
        this.pat = pat;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        db = new dao.Database();
        primaryStage.setTitle("Patient Vitals");

        String sql = "select distinct * from vitals where patient_id = ?";
        PreparedStatement ps = db.prepare(sql);

        try {
            ps.setInt(1, pat.getId());

            ResultSet rs = ps.executeQuery();

            if(rs == null || !rs.next()){
                String sql2 = "insert into vitals (patient_id, num) values (?, 0);";
                PreparedStatement ps2 = db.prepare(sql2);
                ps2.setString(1, "" + pat.getId());
                ps2.executeUpdate();
                num = 0;
            }
            else{
                num = rs.getInt("num");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        int pnum = pat.getId() % 3 + 1;
        int vnum = num % 4 + 1;
        Image img = new Image(getClass().getResource("/vital" + pnum + "" + vnum + ".png").toExternalForm());
        ImageView view = new ImageView(img);
        view.setPreserveRatio(true);
        view.setFitWidth(400);

        Button confirmButton = createButton("Confirm", "#4ade94");
        Button cancelButton = createButton("Cancel", "de4a94");

        confirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String sql10 = "update vitals set num = ? where patient_id = ?";
                PreparedStatement ps10 = db.prepare(sql10);

                try {
                    ps10.setString(1, "" + (num + 1));
                    ps10.setString(2, "" + pat.getId());
                    ps10.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                primaryStage.close();  // Guess what this does!!!
            }
        });

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.close(); // Actually closes the scene
            }
        });

        TextField text = new TextField("Do you want to upload this vitals graph?");
        text.setFont(new Font(31.5));
        text.setDisable(true);
        text.setOpacity(1.000000);
        text.setAlignment(Pos.CENTER);

        HBox butt = new HBox(confirmButton, cancelButton);
        System.out.println("Setting the spacing of button to 50");
        butt.setSpacing(50);
        butt.setAlignment(Pos.CENTER);
        VBox root = new VBox(text, view, butt);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(30);

        Scene scene = new Scene(root, 600, 450);

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
