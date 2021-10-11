package GUIComponents;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MenuButton extends Button {

    public static final String FONT_PATH = "classes/Goldman-Regular.ttf";
    public static final int FONT_SIZE = 23; //23
    private String BUTTON_PRESSED;
    private String BUTTON_FREE;
    private int fontSize;
    private int prefWidth;
    private int prefHeight;

    public MenuButton(String text, int fontSize, String pressed, String free, int width, int height) {
        this.BUTTON_FREE = free;
        this.BUTTON_PRESSED = pressed;
        this.prefWidth = width;
        this.prefHeight = height;
        this.fontSize = fontSize;
        setText(text);
        setTextAlignment(TextAlignment.JUSTIFY);
        setButtonFont();
        setPrefWidth(prefWidth);
        setPrefHeight(prefHeight);
        setStyle(BUTTON_FREE);
        initButtonListeners();
    }

    public MenuButton(String text) {
        this(text, 23);
    }

    public MenuButton(String text, int fontSize) {
        this(text, fontSize,
                "-fx-background-color: transparent; -fx-background-image: url('menu/red_button00.png');  -fx-background-repeat: no-repeat;",
                "-fx-background-color: transparent; -fx-background-image: url('menu/red_button01.png');  -fx-background-repeat: no-repeat;",
                190, 49);
    }

    private void setButtonFont() {

        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), fontSize));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", fontSize));
        }
    }

    private void setButtonPressed() {
        setStyle(BUTTON_PRESSED);
        setPrefHeight(prefHeight - 4);
        setLayoutY(getLayoutY() + 4);
    }

    private void setButtonFree() {
        setStyle(BUTTON_FREE);
        setPrefHeight(prefHeight);
        setLayoutY(getLayoutY() - 4);
    }

    private void initButtonListeners() {
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonPressed();
                }
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonFree();
                }
            }
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(new DropShadow());
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(null);
            }
        });
    }
}
