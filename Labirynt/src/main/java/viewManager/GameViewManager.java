package viewManager;

import GUIComponents.DIFFICULTY;
import GUIComponents.RankingTable;
import gameComponents.Dog;
import gameComponents.EndGameSubscene;
import gameComponents.GameLabel;
import gameComponents.MazeGenerator;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.lang3.time.StopWatch;

import java.sql.Time;

public class GameViewManager {

    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    private Stage menuStage;
    private Canvas canvas;
    private MazeGenerator mazeGenerator;
    private GraphicsContext gc;
    private MyAnimationTimer timer;
    private String nickname;
    private GameLabel gameLabel;
    public static final int MARGINX = 25;
    public static final int MARGINY = MARGINX + 50;
    private Dog dog;
    private StopWatch stopWatch;
    private static boolean END_REACHED;
    private EndGameSubscene endGameSubscene;
    private RankingTable rankingTable;
    private DIFFICULTY difficulty;

    public static final int GAME_WIDTH = 850;
    public static final int GAME_HEIGHT = 900;

    public GameViewManager(){
        initializeStage();
    }

    private void initializeStage() {
        stopWatch = new StopWatch();
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setTitle("aMAZEing");
        gameStage.setScene(gameScene);
        gameStage.setResizable(false);
        canvas = new Canvas(GAME_WIDTH,GAME_HEIGHT);

        gameStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                gameStage.close();
                menuStage.show();
            }
        });
    }

    public void createNewGame(Stage menuStage, DIFFICULTY chosenLevel, String nickname, RankingTable rankingTable) {
        END_REACHED = false;
        gamePane.getChildren().clear();
        addBackground();
        this.rankingTable = rankingTable;
        this.nickname = nickname;
        this.menuStage = menuStage;
        this.difficulty = chosenLevel;
        mazeGenerator.generateMaze(chosenLevel, gamePane);
        gameLabel = new GameLabel(nickname, chosenLevel, stopWatch, rankingTable.getResultList());
        gameLabel.getNode().setLayoutX(25);
        gameLabel.getNode().setLayoutY(0);
        gamePane.getChildren().add(gameLabel.getNode());
        dog = new Dog(gamePane, chosenLevel, mazeGenerator.getStartCell(), mazeGenerator.getGrid());
        timer = new MyAnimationTimer();
        this.menuStage.hide();
        timer.start();
        gameStage.show();
        stopWatch.start();
    }

    private void addBackground() {
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.DIMGRAY);
        gc.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gc.setFill(Color.GRAY);
        gc.fillRect(25, 75, 800, 800);

        mazeGenerator = new MazeGenerator();

        gamePane.getChildren().add(canvas);
    }

    public void createNewGame() {
        createNewGame(menuStage, difficulty, nickname, rankingTable);
    }

    private void addEndGameSubscene(Time resultTime) {
        endGameSubscene = new EndGameSubscene(resultTime, rankingTable, difficulty, nickname, gameStage, menuStage);
        gamePane.getChildren().add(endGameSubscene);
    }

    private class MyAnimationTimer extends AnimationTimer {

        @Override
        public void handle(long l) {
            gameLabel.updatePlayerTime();
            if(!END_REACHED) {

                if (dog.isUpPressed()) {
                    dog.moveUp();
                }
                if (dog.isDownPressed()) {
                    dog.moveDown();
                }
                if (dog.isRightPressed()) {
                    dog.moveRight();
                }
                if (dog.isLeftPressed()) {
                    dog.moveLeft();
                }
            } else {
                endGame();
            }
        }

    }

    private void endGame() {
        stopWatch.stop();
        gameLabel.updatePlayerTime();
        long time = stopWatch.getTime();
        stopWatch.reset();
        Time resultTime = new Time(time);
        if(time < 3600000) {
            resultTime.setHours(0);
        }

        timer.stop();
        addEndGameSubscene(resultTime);
        endGameSubscene.moveSubscene();
    }

    public static void setEndReached() {
        END_REACHED = true;
    }

}
