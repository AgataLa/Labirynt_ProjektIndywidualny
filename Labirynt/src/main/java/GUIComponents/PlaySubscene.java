package GUIComponents;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import viewManager.GameViewManager;

import java.util.ArrayList;
import java.util.List;

public class PlaySubscene extends MenuSubscene {

    private List<LevelPicker> levelList;
    private DIFFICULTY chosenLevel;
    private String nickname;
    private MenuTextField inputField;
    public static GameViewManager gameViewManager;
    private AnchorPane mainPane;
    private Stage mainStage;
    private List<RankingTable> rankingTables;
    private List<Image> sittingDog;
    private ImageView currentDog;
    private PlayAnimationTimer timer;
    private LeaderboardSubscene leaderboardSubscene;


    public PlaySubscene(AnchorPane mainPane, Stage mainStage, List<RankingTable> rankingTables, LeaderboardSubscene leaderboardSubscene) {
        super();
        this.leaderboardSubscene = leaderboardSubscene;
        this.rankingTables = rankingTables;
        this.mainPane = mainPane;
        this.mainStage = mainStage;
        initialize();
    }

    private void initialize() {
        AnchorPane playPane = (AnchorPane) this.getRoot();
        InfoLabel selectLevelLabel = new InfoLabel("Select difficulty level", 500);
        selectLevelLabel.setLayoutY(35);
        selectLevelLabel.setLayoutX(50);
        playPane.getChildren().add(selectLevelLabel);
        playPane.getChildren().add(createDifficultyLevelsToChoose());
        MenuTextField inputField = createInputField();
        playPane.getChildren().add(inputField);
        playPane.getChildren().add(inputField.getConfirmImage());
        playPane.getChildren().add(createStartGameButton());
        loadDogImages(playPane);
        timer = new PlayAnimationTimer();
        timer.start();
    }

    private VBox createDifficultyLevelsToChoose() {
        VBox vbox = new VBox();
        vbox.setSpacing(20);
        levelList = new ArrayList<>();
        for(DIFFICULTY diff: DIFFICULTY.values()) {
            LevelPicker levelToPick = new LevelPicker(diff);
            levelList.add(levelToPick);
            vbox.getChildren().add(levelToPick);
            levelToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    for(LevelPicker level: levelList) {
                        level.setIsBoxChosen(false);
                    }
                    levelToPick.setIsBoxChosen(true);
                    chosenLevel = levelToPick.getDifficulty();
                }
            });
        }
        vbox.setLayoutX(70);
        vbox.setLayoutY(120);

        return vbox;
    }

    private MenuTextField createInputField() {
        inputField = new MenuTextField("Enter your name", 20);
        inputField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode().equals(KeyCode.ENTER)) {
                    nickname = inputField.getText().trim();
                    inputField.confirmInput();
                    mainPane.requestFocus();
                }
            }
        });

        return inputField;
    }

    private MenuButton createStartGameButton() {
        MenuButton startButton = new MenuButton("START");
        startButton.setLayoutX(350);
        startButton.setLayoutY(400);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(chosenLevel != null && inputField.isConfirmed()) {
                    gameViewManager = new GameViewManager();
                    int index = 0;
                    switch (chosenLevel) {
                        case EASY: index = 0; break;
                        case MEDIUM: index = 1; break;
                        case HARD: index = 2; break;
                    }
                    gameViewManager.createNewGame(mainStage, chosenLevel, nickname, rankingTables.get(index));
                }
            }
        });

        return startButton;
    }

    private void loadDogImages(AnchorPane playPane) {
        sittingDog = new ArrayList<>();
        Image image;
        for(int i = 1; i < 3; i++) {
            image = new Image("character/sleep" + i + ".png", 150,150, true, true);
            sittingDog.add(image);
        }
        currentDog = new ImageView(sittingDog.get(0));
        currentDog.setLayoutY(150);
        currentDog.setLayoutX(320);
        playPane.getChildren().add(currentDog);
    }

    private class PlayAnimationTimer extends AnimationTimer {
        int frame = 0;

        @Override
        public void handle(long l) {
            frame++;
            currentDog.setImage(sittingDog.get((frame % 60)/30));
        }
    }
}
