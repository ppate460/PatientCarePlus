package application;

import dao.Database;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Patient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowVitals extends Application {
    models.Patient pat;
    Database db;
    int num;
    int index;
    Image img;
    ImageView imageView;

    private Insets INSETS = new Insets(10,10,10,10);

    public ShowVitals(Patient pat){
        this.pat = pat;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        db = new dao.Database();
        primaryStage.setTitle(pat.getFirstName() + "'s Vitals");


        String sql = "select distinct * from vitals where patient_id = ?";
        PreparedStatement ps = db.prepare(sql);

        try{
            ps.setString(1, "" + pat.getId());
            ResultSet rs = ps.executeQuery();

            if(rs == null || !rs.next()){
                num = 0;
                index = -1;
            }
            else{
                num = rs.getInt("num");
                index = 0;
            }
        }
        catch(SQLException OwO){
            throw new RuntimeException(OwO);
        }


        imageView = new ImageView();
        HBox redBox = new HBox();
        if(num == 0){
            img = new Image(getClass().getResource("/deadpatient.png").toExternalForm());
            imageView = new ImageView(img);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(400);

            redBox.getChildren().add(imageView);
        }
        else{
            Button left = createButton("Previous", "#9deded");
            Button right = createButton("Next", "#9deded");

            left.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println("Sliiiiiiide to the left");
                    goLeft();
                }
            });

            right.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println("Now sliiiiiiide to the right");
                    goRight();
                }
            });

            updateImage();


            redBox.getChildren().addAll(left, imageView, right);
        }
        redBox.setAlignment(Pos.CENTER);
        redBox.setSpacing(20);

        Button back_button = createButton("Back", "gray");

        back_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.close(); // Actually closes the scene
            }
        });

        VBox root = new VBox(redBox, back_button);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(30);

        Scene scene = new Scene(root, 800, 450);

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

    void goLeft(){
        if(index == 0){
            index = num;
        }
        else{
            index--;
        }
        updateImage();
    }

    void goRight() {
        if (index == num) {
            index = 0;
        }
        else{
            index++;
        }
        updateImage();
    }

    void updateImage(){
        int pnum = pat.getId() % 3 + 1;
        int vnum = index % 4 + 1;

        System.out.println("CRISS CROSS!");
        img = new Image(getClass().getResource("/vital" + pnum + "" + vnum + ".png").toExternalForm());
        imageView.setImage(img);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(400);
        System.out.println("Everybody clap your hands");
    }
}
