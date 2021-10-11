package gameComponents;

import GUIComponents.DIFFICULTY;
import GUIComponents.InfoLabel;
import GUIComponents.MenuButton;
import database.Result;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.apache.commons.lang3.time.StopWatch;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;

public class GameLabel {

    private final static String BACKGROUND_IMAGE = "maze/game_label.png";
    protected final static String FONT_PATH = MenuButton.FONT_PATH;
    private HBox labelBox;
    private int width = 200;
    private int defaultFontSize = 16;
    private StopWatch stopWatch;
    private Label playerTimeLabel;

    public GameLabel(String nickname, DIFFICULTY difficulty, StopWatch playerTime, List<? extends Result> results) {
        this.stopWatch = playerTime;
        labelBox = new HBox();
        initialize(nickname, difficulty, results);
    }

    private void initialize(String nickname, DIFFICULTY difficulty, List<? extends Result> results) {
        BackgroundImage backgroundImage = new BackgroundImage(new Image(BACKGROUND_IMAGE, width, 49, false, true), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        Background background = new Background(backgroundImage);
        addLabel("Nickname: " + nickname, calculateFontSize("Nickname: " + nickname), background);
        addPlayerTimeLabel(defaultFontSize, background);
        Time bestTime = getBestTimeFromDatabase(results);
        if(bestTime.getTime() < 3600000) {
            addLabel("Best time: " + new SimpleDateFormat("mm:ss.SSS").format(bestTime), defaultFontSize, background);
        } else {
            addLabel("Best time: " + new SimpleDateFormat("HH:mm:ss.SSS").format(bestTime), defaultFontSize, background);
        }
        addLabel("Level: " + difficulty.getText(), defaultFontSize, background);

    }

    private Time getBestTimeFromDatabase(List<? extends Result> results) {
        if(results != null && results.size() != 0) {
            return results.get(0).getTime();
        } else {
            return Time.valueOf("00:00:00");
        }
    }

    public HBox getNode() {
        return labelBox;
    }

    private void addLabel(String text, int fontSize, Background background) {
        InfoLabel label = new InfoLabel(text, width, fontSize);
        label.setBackground(background);
        label.setAlignment(Pos.CENTER_LEFT);
        label.setPadding(new Insets(0,6,6,10));
        labelBox.getChildren().add(label);
    }

    private void addPlayerTimeLabel(int fontSize, Background background) {
        String text = "Time: " + stopWatch.formatTime();
        playerTimeLabel = new InfoLabel(text, width, fontSize);
        playerTimeLabel.setBackground(background);
        playerTimeLabel.setAlignment(Pos.CENTER_LEFT);
        playerTimeLabel.setPadding(new Insets(0,4,6,10));
        labelBox.getChildren().add(playerTimeLabel);
    }

    public void updatePlayerTime() {
        playerTimeLabel.setText("Time: " + stopWatch.formatTime());
    }

    private int calculateFontSize(String text) {
        int fontSize = defaultFontSize;
        Text textToCalc = new Text(text);
        try {
            textToCalc.setFont(Font.loadFont(new FileInputStream(FONT_PATH), defaultFontSize));
        } catch (FileNotFoundException e) {
            textToCalc.setFont(Font.font("Verdana", defaultFontSize));
        }
        double textWidth = textToCalc.getBoundsInLocal().getWidth();

        if(textWidth > 175) {
            return 12;
        }

        return fontSize;
    }

}
