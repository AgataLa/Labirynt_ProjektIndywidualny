package GUIComponents;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MenuTextField extends TextField {

    private final String FONT_PATH = MenuButton.FONT_PATH;
    private final static String BACKGROUND_IMAGE = "playSubscene/text_input.png";
    private final static String CONFIRM_IMAGE = "playSubscene/red_checkmark.png";
    private int maxLength;
    private ImageView confirmImage;
    private boolean confirmed;

    public MenuTextField(String text, int maxLength) {
        this.maxLength = maxLength;
        setPrefHeight(49);
        setPrefWidth(500);
        setEditable(true);
        setLabelFont();
        setPromptText(text);
        setLayoutY(320);
        setLayoutX(50);
        confirmImage = new ImageView(CONFIRM_IMAGE);
        confirmImage.setLayoutX(500);
        confirmImage.setLayoutY(333);
        confirmImage.setOpacity(0);
        confirmed = false;

        BackgroundImage backgroundImage = new BackgroundImage(new Image(BACKGROUND_IMAGE, 500, 49, false, true), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(backgroundImage));
    }

    private void setLabelFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 23));
        }
    }

    public void confirmInput() {
        confirmImage.setOpacity(1);
        confirmed = true;
    }

    public ImageView getConfirmImage() {
        return confirmImage;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    @Override
    public void replaceText(int start, int end, String text) {
        confirmed = false;
        confirmImage.setOpacity(0);
        int diff = maxLength - getText().length();
        if(text.length() + getText().length() < maxLength) {
            super.replaceText(start, end, text);
        } else {
            String shorten = text.substring(0, diff);
            super.replaceText(start, end, shorten);
        }
    }
}
