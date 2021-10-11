package GUIComponents;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class HelpSubscene extends MenuSubscene {

    private List<Image> helpDogs;
    private ImageView currentDog;
    private HelpAnimationTimer helpAnimationTimer;

    public HelpSubscene() {
        super();
        initialize();
    }

    private void initialize() {
        Label gamePurpose = new Label("The purpose of the game is to guide your character through the maze " +
                "and find the exit as quickly as you can. Before starting the game you have to choose difficulty " +
                "level (size of the maze) and your nickname.\nUse arrow keys to control your character.");
        gamePurpose.setWrapText(true);
        gamePurpose.setTextAlignment(TextAlignment.CENTER);
        gamePurpose.prefWidth(500);
        gamePurpose.setMaxWidth(500);
        try {
            gamePurpose.setFont(Font.loadFont(new FileInputStream(super.FONT_PATH), 21));
        } catch (FileNotFoundException e) {
            gamePurpose.setFont(Font.font("Verdana", 23));
        }
        gamePurpose.setLayoutX(50);
        gamePurpose.setLayoutY(35);

        int arrowSize = (int) (40 * 1.4);
        ImageView upArrow = new ImageView(new Image("helpSubscene/arrow_up.png", arrowSize, arrowSize, true, true));
        upArrow.setLayoutY(250);
        upArrow.setLayoutX(360);
        ImageView rightArrow = new ImageView(new Image("helpSubscene/arrow_right.png", arrowSize, arrowSize, true, true));
        rightArrow.setLayoutY(310);
        rightArrow.setLayoutX(420);
        ImageView downArrow = new ImageView(new Image("helpSubscene/arrow_down.png", arrowSize, arrowSize, true, true));
        downArrow.setLayoutY(370);
        downArrow.setLayoutX(360);
        ImageView leftArrow = new ImageView(new Image("helpSubscene/arrow_left.png", arrowSize, arrowSize, true, true));
        leftArrow.setLayoutY(310);
        leftArrow.setLayoutX(300);

        AnchorPane helpPane = (AnchorPane) this.getRoot();

        loadDogs(helpPane);
        helpAnimationTimer = new HelpAnimationTimer();

        helpPane.getChildren().add(upArrow);
        helpPane.getChildren().add(rightArrow);
        helpPane.getChildren().add(downArrow);
        helpPane.getChildren().add(leftArrow);
        helpPane.getChildren().add(gamePurpose);

        helpAnimationTimer.start();
    }

    private void loadDogs(AnchorPane helpPane) {
        helpDogs = new ArrayList<>();
        Image image;
        for(int i = 1; i < 5; i++) {
            image = new Image("character/right" + i + ".png", 150,150, true, true);
            helpDogs.add(image);
        }
        currentDog = new ImageView(helpDogs.get(0));
        currentDog.setLayoutY(270);
        currentDog.setLayoutX(90);
        helpPane.getChildren().add(currentDog);
    }

    private class HelpAnimationTimer extends AnimationTimer {
        int frame = 0;

        @Override
        public void handle(long l) {
            frame++;
            currentDog.setImage(helpDogs.get((frame % 40)/10));
        }
    }
}
