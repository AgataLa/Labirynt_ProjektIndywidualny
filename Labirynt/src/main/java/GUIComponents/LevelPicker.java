package GUIComponents;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LevelPicker extends HBox {

    private final static String FONT_PATH = MenuButton.FONT_PATH;
    private ImageView boxImage;
    private String boxChosen = "playSubscene/red_boxCheckmark.png";
    private String boxNotChosen = "playSubscene/grey_box.png";
    private DIFFICULTY difficulty;
    private boolean isBoxChosen;

    public LevelPicker(DIFFICULTY difficulty) {
        this.difficulty = difficulty;
        boxImage = new ImageView(boxNotChosen);
        Text level = new Text(difficulty.getText());
        try {
            level.setFont(Font.loadFont(new FileInputStream(FONT_PATH),23));
        } catch (FileNotFoundException e) {
            level.setFont(Font.font("Verdana", 23));
        }
        isBoxChosen = false;
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(20);
        this.getChildren().add(boxImage);
        this.getChildren().add(level);
    }

    public DIFFICULTY getDifficulty() {
        return difficulty;
    }

    public boolean getIsBoxChosen() {
        return isBoxChosen;
    }

    public void setIsBoxChosen(boolean isBoxChosen) {
        this.isBoxChosen = isBoxChosen;
        String imageToSet = this.isBoxChosen ? boxChosen : boxNotChosen;
        boxImage.setImage(new Image(imageToSet));
    }
}
