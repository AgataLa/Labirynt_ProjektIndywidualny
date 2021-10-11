package GUIComponents;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AlertBox extends Stage {

    private final int WIDTH = 400;
    private final int HEIGHT = 150;
    private final AnchorPane alertPane;
    private final String FONT_PATH = MenuButton.FONT_PATH;
    private final String errorButtonFree = "-fx-background-color: transparent; -fx-background-image: url('alertBox/ok_button_free.png');";
    private final String errorButtonPressed = "-fx-background-color: transparent; -fx-background-image: url('alertBox/ok_button_pressed.png');";

    public AlertBox(String text) {
        alertPane = new AnchorPane();
        initialize(text);
    }

    private void initialize(String text) {
        createBackground();
        addButton();
        addMessage(text);
        addErrorImage();
        this.setTitle("Error");

        this.setResizable(false);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                windowEvent.consume();
            }
        });

        Scene alertScene = new Scene(alertPane, WIDTH, HEIGHT);
        this.setScene(alertScene);
    }

    private void addErrorImage() {
        int errorSize = 50;
        ImageView errorImage = new ImageView(new Image("alertBox/remove.png", errorSize, errorSize, true, true));
        errorImage.setLayoutX(20);
        errorImage.setLayoutY(30);
        alertPane.getChildren().add(errorImage);
    }

    private void addMessage(String text) {
        Label message = new Label();
        int fontSize = 14;
        try {
            message.setFont(Font.loadFont(new FileInputStream(FONT_PATH), fontSize));
        } catch (FileNotFoundException e) {
            message.setFont(Font.font("Verdana", fontSize));
        }
        message.setBackground(Background.EMPTY);
        message.setWrapText(true);
        message.setLayoutX(40 + 50);
        message.setLayoutY(20);
        message.setPrefWidth(WIDTH - 60 - 50);
        message.setPrefHeight(HEIGHT - 40 - 40);
        message.setText(text);
        alertPane.getChildren().add(message);
    }

    private void addButton() {
        MenuButton okButton = new MenuButton("OK", 15,errorButtonPressed, errorButtonFree, 114, 29);
        okButton.setLayoutX(WIDTH - 20 - okButton.getPrefWidth());
        okButton.setLayoutY(HEIGHT - 20 - okButton.getPrefHeight());
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                close();
            }
        });
        alertPane.getChildren().add(okButton);
    }

    private void createBackground() {
        Image backgroundImage = new Image("menu/background.png", 500, 500, true, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, null );
        alertPane.setBackground(new Background(background));
    }
}
