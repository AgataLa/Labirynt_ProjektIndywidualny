package GUIComponents;

import javafx.scene.control.Button;
import javafx.scene.text.TextAlignment;

public class ArrowButton extends Button {

    private final String BUTTON_LEFT = "-fx-background-color: transparent; -fx-background-image: url('leaderboardSubscene/sliderLeft.png')";
    private final String BUTTON_RIGHT = "-fx-background-color: transparent; -fx-background-image: url('leaderboardSubscene/sliderRight.png')";

    public ArrowButton(int direction) {
        setTextAlignment(TextAlignment.JUSTIFY);
        setPrefWidth(39);
        setPrefHeight(31);
        if(direction == 0) {
            setStyle(BUTTON_LEFT);
        } else {
            setStyle(BUTTON_RIGHT);
        }
    }
}
