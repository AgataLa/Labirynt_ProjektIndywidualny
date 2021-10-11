package GUIComponents;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class InfoLabel extends Label {

    protected final String FONT_PATH = MenuButton.FONT_PATH;
    protected final String BACKGROUND_IMAGE = "menu/red_button10.png";

    public InfoLabel(String text, int width, int fontSize) {
        setPrefHeight(49);
        setPrefWidth(width);
        setText(text);
        setWrapText(true);
        setAlignment(Pos.CENTER);
        setLabelFont(fontSize);

        BackgroundImage backgroundImage = new BackgroundImage(new Image(BACKGROUND_IMAGE, width, 49, false, true), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(backgroundImage));
    }

    public InfoLabel(String text, int width) {
        this(text, width, 23);
    }

    public void setLabelFont(int fontSize) {
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), fontSize));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", fontSize));
        }
    }
}
