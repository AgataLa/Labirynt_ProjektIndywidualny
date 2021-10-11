package GUIComponents;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;

public class RankingLabel extends InfoLabel{

    private String labelImage = "leaderboardSubscene/label.png";

    public RankingLabel(String text) {
        this(text, 200);
    }

    public RankingLabel(String text, int width, int fontSize) {
        super(text, width, fontSize);
        setPrefHeight(50);
        setPrefWidth(width);
        setPadding(new Insets(0,10,0,20));
        setAlignment(Pos.CENTER_LEFT);
        BackgroundImage backgroundImage;
        if(width == 200) {
            backgroundImage = new BackgroundImage(new Image(BACKGROUND_IMAGE, width, 50, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        } else {
            backgroundImage = new BackgroundImage(new Image(labelImage, width, 50, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        }
        setBackground(new Background(backgroundImage));
    }

    public RankingLabel(String text, int width) {
        this(text, width, 23);
    }

    public void setImage(int width) {
        BackgroundImage backgroundImage = new BackgroundImage(new Image(labelImage, width, 50, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(backgroundImage));
    }


}
