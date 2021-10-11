package viewManager;

import GUIComponents.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ViewManager {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;
    private final static int BUTTON_STARTX = 100;
    private final static int BUTTON_STARTY = 260;

    private List<MenuButton> menuButtons;
    private MenuSubscene playSubscene;
    private LeaderboardSubscene leaderboardSubscene;
    private MenuSubscene helpSubscene;
    private MenuSubscene sceneToHide;

    public ViewManager() {
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        createSubscenes();
        createButtons();
        createBackground();
        createLogo();
        mainScene.getStylesheets().add("leaderboardSubscene/scroll.css");
    }

    private void showSubscene(MenuSubscene subscene) {
        if(sceneToHide != null) {
            sceneToHide.moveSubscene();
        }

        if(!subscene.equals(sceneToHide)) {
            subscene.moveSubscene();
            sceneToHide = subscene;
        } else {
            sceneToHide = null;
        }
    }

    private void createPlaySubscene() {
        playSubscene = new PlaySubscene(mainPane, mainStage, leaderboardSubscene.getTables(), leaderboardSubscene);
        mainPane.getChildren().add(playSubscene);
    }

    private void createLeaderboardSubscene() {
        leaderboardSubscene = new LeaderboardSubscene();
        mainPane.getChildren().add(leaderboardSubscene);
    }

    private void createSubscenes() {
        createLeaderboardSubscene();
        createHelpSubscene();
        createPlaySubscene();
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void addMenuButton(MenuButton button) {
        button.setLayoutX(BUTTON_STARTX);
        button.setLayoutY(BUTTON_STARTY + menuButtons.size()*100);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }

    private void createButtons() {
        createPlayButton();
        createLeaderboardButton();
        createHelpButton();
        createExitButton();
    }

    private void createPlayButton() {
        MenuButton playButton = new MenuButton("PLAY");
        addMenuButton(playButton);

        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showSubscene(playSubscene);
            }
        });
    }

    private void createLeaderboardButton() {
        MenuButton leaderboardButton = new MenuButton("LEADERBOARD", 18);
        addMenuButton(leaderboardButton);

        leaderboardButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showSubscene(leaderboardSubscene);
            }
        });
    }

    private void createHelpButton() {
        MenuButton helpButton = new MenuButton("HELP");
        addMenuButton(helpButton);

        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showSubscene(helpSubscene);
            }
        });
    }

    private void createHelpSubscene() {
        helpSubscene = new HelpSubscene();
        mainPane.getChildren().add(helpSubscene);
    }

    private void createExitButton() {
        MenuButton exitButton = new MenuButton("EXIT");
        addMenuButton(exitButton);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainStage.close();
            }
        });
    }


    private void createBackground() {
        Image backgroundImage = new Image("menu/background.png", 1200, 1200, true, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, null );
        mainPane.setBackground(new Background(background));
    }

    private void createLogo() {
        ImageView logo = new ImageView("menu/logo.png");
        logo.setLayoutX(300);
        logo.setLayoutY(50);

        logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                logo.setEffect(new DropShadow());
            }
        });
        logo.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                logo.setEffect(null);
            }
        });
        mainPane.getChildren().add(logo);
    }
}
