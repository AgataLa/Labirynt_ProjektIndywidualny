package main;

import GUIComponents.AlertBox;
import database.DatabaseManager;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import viewManager.ViewManager;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        AlertBox alertBox = null;
        try {
            DatabaseManager.initSessionFactory();
        } catch (Exception e) {
            alertBox = new AlertBox("Could not connect to database. Leaderboards will not be available and your results will not be saved.");
        }

        ViewManager viewManager = new ViewManager();
        primaryStage = viewManager.getMainStage();
        primaryStage.setResizable(false);
        primaryStage.setTitle("aMAZEing");
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                windowEvent.consume();
            }
        });

        primaryStage.show();
        if(alertBox != null) {
            alertBox.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
