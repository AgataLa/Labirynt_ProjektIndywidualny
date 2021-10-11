package GUIComponents;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class MenuSubscene extends SubScene {

    protected final String FONT_PATH = MenuButton.FONT_PATH;
    private final String BACKGROUND_IMAGE = "menu/red_panel.png";

    private boolean isHidden;

    public MenuSubscene() {
        super(new AnchorPane(), 600, 500);
        prefWidth(600);
        prefHeight(500);

        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 600, 500, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        AnchorPane root = (AnchorPane) this.getRoot();
        root.setBackground(new Background(image));

        isHidden = true;
        setLayoutX(1024);
        setLayoutY(180);
    }

    public void moveSubscene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);

        if (isHidden) {
            transition.setToX(-676);
            isHidden = false;
        } else {
            transition.setToX(0);
            isHidden = true;
        }

        transition.play();
    }
}
