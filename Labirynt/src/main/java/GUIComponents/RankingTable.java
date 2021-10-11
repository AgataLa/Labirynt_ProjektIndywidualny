package GUIComponents;

import database.DatabaseManager;
import database.Result;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;

public class RankingTable {
    private VBox nickColumn;
    private VBox timeColumn;
    private VBox medalColumn;
    private HBox table;
    private DIFFICULTY difficulty;
    private List<? extends Result> resultList;
    private int defaultFontSize = 19;
    private int fontSize;
    private final AnchorPane pane;
    private final static String FONT_PATH = MenuButton.FONT_PATH;

    public RankingTable(DIFFICULTY difficulty, AnchorPane pane) {
        nickColumn = new VBox();
        timeColumn = new VBox();
        medalColumn = new VBox();
        table = new HBox();
        table.getChildren().add(medalColumn);
        table.getChildren().add(nickColumn);
        table.getChildren().add(timeColumn);
        this.difficulty = difficulty;
        this.pane = pane;
        init();
    }

    private void init() {
        nickColumn.getChildren().clear();
        timeColumn.getChildren().clear();
        medalColumn.getChildren().clear();
        RankingLabel nicknameLabel = new RankingLabel("NICKNAME") ;
        nicknameLabel.setAlignment(Pos.CENTER);
        nicknameLabel.setPadding(Insets.EMPTY);
        nicknameLabel.setImage(200);
        RankingLabel timeLabel = new RankingLabel("TIME");
        timeLabel.setAlignment(Pos.CENTER);
        timeLabel.setPadding(Insets.EMPTY);
        timeLabel.setImage(200);
        nickColumn.getChildren().add(nicknameLabel);
        timeColumn.getChildren().add(timeLabel);
        try {
            resultList = DatabaseManager.getResults(difficulty);
            int pos = 0;
            Time prev = Time.valueOf("10:10:10");
            String text;
            for (Result result : resultList) {
                if(result.getTime().getTime() != prev.getTime()) {
                    pos++;
                }
                text = pos + "."+ result.getNickname();
                fontSize = calculateFontSize(text);
                nicknameLabel = new RankingLabel(text, 200, fontSize);
                if(result.getTime().getTime() < 3600000) {
                    timeLabel = new RankingLabel(new SimpleDateFormat("mm:ss.SS").format(result.getTime()), 200, defaultFontSize);
                } else {
                    timeLabel = new RankingLabel(new SimpleDateFormat("HH:mm:ss.SS").format(result.getTime()), 200, defaultFontSize);
                }
                nickColumn.getChildren().add(nicknameLabel);
                timeColumn.getChildren().add(timeLabel);
                prev = result.getTime();

                if(pos == 1 || pos == 2 || pos == 3) {
                    addMedalImage(pos);
                }
            }
        } catch (Exception e) {
        }
        medalColumn.prefWidth(50);
        medalColumn.setMinWidth(50);
        medalColumn.setPadding(new Insets(55,0,0,0));
        medalColumn.setSpacing(10);
    }

    private void addMedalImage(int pos) {
        Image medalImage = new Image("leaderboardSubscene/medal" + pos + ".png", 40, 40, true, true);
        ImageView medalView = new ImageView(medalImage);
        medalView.setLayoutX(0);
        medalColumn.getChildren().add(medalView);
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
            return 14;
        }

        return fontSize;
    }

    public List<? extends Result> getResultList() {
        return resultList;
    }

    public void updateTable() {
        init();
    }

    public HBox getNode() {
        return table;
    }
}
