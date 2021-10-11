package gameComponents;

import GUIComponents.*;
import database.DatabaseManager;
import database.Result;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import viewManager.GameViewManager;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EndGameSubscene extends MenuSubscene {

    private final AnchorPane endGamePane;
    private ImageView currentDog;
    private ImageView home;
    private ImageView playAgain;
    private List<Image> endDogs;
    private EndGameAnimationTimer timer;
    private List<? extends Result> results;
    private RankingTable rankingTable;
    private Time resultTime;
    private DropShadow dropShadow;
    private final Stage menuStage;
    private final Stage gameStage;
    private String nickname;
    private DIFFICULTY difficulty;
    private int buttonSize = 80;
    private int place;

    public EndGameSubscene(Time resultTime, RankingTable rankingTable, DIFFICULTY difficulty, String nickname, Stage gameStage, Stage menuStage) {
        super();
        this.resultTime = resultTime;
        this.rankingTable = rankingTable;
        this.results = rankingTable.getResultList();
        this.nickname = nickname;
        this.difficulty = difficulty;
        setLayoutY(-getHeight());
        setLayoutX((GameViewManager.GAME_WIDTH - getWidth())/2);
        endGamePane = (AnchorPane) this.getRoot();
        timer = new EndGameAnimationTimer();
        this.menuStage = menuStage;
        this.gameStage = gameStage;
        initialize();
    }

    private void initialize() {
        dropShadow = new DropShadow();
        addCongratsLabel();
        addDog();
        addResultLabels();
        addHomeButton();
        addPlayAgainButton();

        try {
            DatabaseManager.addResult(difficulty, nickname, resultTime.getTime());
            if(place < 21) {
                rankingTable.updateTable();
            }
        } catch (Exception e) {
            AlertBox alertBox = new AlertBox("Could not connect to database, your result will not be saved.");
            alertBox.show();
        }

        timer.start();
    }

    private void addHomeButton() {
        Image image = new Image("endSubscene/home.png", buttonSize, buttonSize, true, true);
        home = new ImageView(image);
        home.setLayoutX(getWidth()/2 - 25 - image.getWidth());
        home.setLayoutY(380);
        home.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                home.setEffect(dropShadow);
            }
        });
        home.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                home.setEffect(null);
            }
        });
        home.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameStage.close();
                menuStage.show();
            }
        });
        endGamePane.getChildren().add(home);
    }

    private void addPlayAgainButton() {
        Image image = new Image("endSubscene/replay.png", buttonSize, buttonSize, true, true);
        playAgain = new ImageView(image);
        playAgain.setLayoutX(getWidth()/2 + 25);
        playAgain.setLayoutY(380);
        endGamePane.getChildren().add(playAgain);

        playAgain.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                playAgain.setEffect(dropShadow);
            }
        });

        playAgain.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                playAgain.setEffect(null);
            }
        });

        playAgain.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                PlaySubscene.gameViewManager.createNewGame();
            }
        });
    }

    private void addDog() {
        endDogs = new ArrayList<>();
        Image image;
        for(int i = 1; i < 5; i++) {
            image = new Image("character/endDog" + i + ".png", 150,150, true, true);
            endDogs.add(image);
        }
        currentDog = new ImageView(endDogs.get(0));
        currentDog.setLayoutX((this.getWidth() - endDogs.get(0).getWidth())/2);
        currentDog.setLayoutY(100);
        endGamePane.getChildren().add(currentDog);
    }

    private void addCongratsLabel() {
        InfoLabel congratsLabel = new InfoLabel("CONGRATULATIONS!", 500);
        congratsLabel.setLayoutY(35);
        congratsLabel.setLayoutX(50);
        endGamePane.getChildren().add(congratsLabel);
    }

    private void addResultLabels() {
        int width = 330;
        int fontSize = 19;
        InfoLabel timeLabel;

        if(resultTime.getTime() < 3600000) {
            timeLabel = new InfoLabel("Your time: " + new SimpleDateFormat("mm:ss.SS").format(resultTime), width, fontSize);
        } else {
            timeLabel = new InfoLabel("Your time: " + new SimpleDateFormat("HH:mm:ss.SS").format(resultTime), width, fontSize);
        }
        timeLabel.setLayoutY(260);
        timeLabel.setLayoutX(50);

        place = calculatePlace();
        InfoLabel placeLabel;
        if(place != 21) {
            placeLabel = new InfoLabel("Your place: " + place, width, fontSize);
        } else {
            placeLabel = new InfoLabel("Your place: 20+", width, fontSize);
        }
        placeLabel.setLayoutY(310);
        placeLabel.setLayoutX(50);

        addResultImage(place);

        endGamePane.getChildren().add(timeLabel);
        endGamePane.getChildren().add(placeLabel);
    }


    private void addResultImage(int place) {
        Image image;
        if(place != 1) {
            image = new Image("endSubscene/like.png", 100, 100, true, true);
        } else {
            image = new Image("endSubscene/best.png", 100, 100, true, true);
        }
        ImageView imageView = new ImageView(image);
        imageView.setLayoutY(260);
        imageView.setLayoutX(380 + 50);
        endGamePane.getChildren().add(imageView);
    }


    private int calculatePlace() {
        int place = 0;
        Time prev = new Time(0);
        if(results != null && results.size() != 0) {
            for (Result r : results) {
                if(r.getTime().getTime() != prev.getTime()) {
                    place++;
                }
                if (r.getTime().getTime() > resultTime.getTime()) {
                    return place;
                }
                prev = r.getTime();
            }
            return results.size() + 1;
        } else {
            return place;
        }
    }

    @Override
    public void moveSubscene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);

        transition.setToY(getHeight() + (GameViewManager.GAME_HEIGHT-getHeight())/2);

        transition.play();
    }

    private class EndGameAnimationTimer extends AnimationTimer {
        int frame = 0;

        @Override
        public void handle(long l) {
            frame++;
            currentDog.setImage(endDogs.get((frame % 20)/5));
        }
    }
}
