package GUIComponents;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardSubscene extends MenuSubscene {

    private final AnchorPane leaderPane;
    private ScrollBar scrollBar;
    private SubScene tablePane;
    private HBox levelChanger;
    private ArrowButton leftArrow;
    private ArrowButton rightArrow;
    private RankingTable easyTable;
    private RankingTable mediumTable;
    private RankingTable hardTable;
    private HBox currentTable;
    private final AnchorPane pane;

    public LeaderboardSubscene() {
        super();
        leaderPane = (AnchorPane) this.getRoot();
        tablePane = new SubScene(new AnchorPane(), 450, 7*50);
        tablePane.setLayoutX(50);
        tablePane.setLayoutY(100);
        pane = (AnchorPane) tablePane.getRoot();
        intitialize();
    }

    private void intitialize() {
        addTables();
        addScrollBar();
        addLevelChangerLine();

        pane.setBackground(Background.EMPTY);
        pane.getChildren().add(currentTable);

        leaderPane.getChildren().add(levelChanger);
        leaderPane.getChildren().add(scrollBar);
        leaderPane.getChildren().add(tablePane);
    }


    private void addScrollBar() {
        scrollBar = new ScrollBar();
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.setLayoutY(100);
        scrollBar.setLayoutX(500 + 5);
        scrollBar.setPrefHeight(350);
        scrollBar.setPrefWidth(20);
        scrollBar.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                if(currentTable.getHeight() > 350) {
                    double diff = currentTable.getHeight() - 350;
                    currentTable.setLayoutY(-(diff / 100) * new_val.doubleValue());
                }
            }
        });
    }


    private void addLevelChangerLine() {
        levelChanger = new HBox();

        RankingLabel levelLabel = new RankingLabel(DIFFICULTY.EASY.getText(), 150);
        levelLabel.setPadding(Insets.EMPTY);
        levelLabel.setAlignment(Pos.CENTER);

        addArrowButtons(levelLabel);

        levelChanger.getChildren().add(leftArrow);
        levelChanger.setSpacing(50);
        levelChanger.setAlignment(Pos.CENTER);
        levelChanger.getChildren().add(levelLabel);
        levelChanger.getChildren().add(rightArrow);
        levelChanger.setLayoutX(138);
        levelChanger.setLayoutY(35);
    }


    private void addArrowButtons(RankingLabel levelLabel) {
        leftArrow = new ArrowButton(0);
        rightArrow = new ArrowButton(1);

        leftArrow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(levelLabel.getText().equals(DIFFICULTY.EASY.getText())) {
                    levelLabel.setText(DIFFICULTY.HARD.getText());
                    changeTable(hardTable, pane);
                } else if (levelLabel.getText().equals(DIFFICULTY.MEDIUM.getText())) {
                    levelLabel.setText(DIFFICULTY.EASY.getText());
                    changeTable(easyTable, pane);
                } else {
                    levelLabel.setText(DIFFICULTY.MEDIUM.getText());
                    changeTable(mediumTable, pane);
                }
            }
        });
        rightArrow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(levelLabel.getText().equals(DIFFICULTY.EASY.getText())) {
                    levelLabel.setText(DIFFICULTY.MEDIUM.getText());
                    changeTable(mediumTable, pane);
                } else if (levelLabel.getText().equals(DIFFICULTY.MEDIUM.getText())) {
                    levelLabel.setText(DIFFICULTY.HARD.getText());
                    changeTable(hardTable, pane);
                } else {
                    levelLabel.setText(DIFFICULTY.EASY.getText());
                    changeTable(easyTable, pane);
                }
            }
        });
    }

    private void addTables() {
        easyTable = new RankingTable(DIFFICULTY.EASY, pane);
        mediumTable = new RankingTable(DIFFICULTY.MEDIUM, pane);
        hardTable = new RankingTable(DIFFICULTY.HARD, pane);
        currentTable = easyTable.getNode();
    }

    private void changeTable(RankingTable newTable, AnchorPane pane) {
        pane.getChildren().remove(currentTable);
        currentTable = newTable.getNode();
        currentTable.setLayoutY(0);
        pane.getChildren().add(currentTable);
    }

    public List<RankingTable> getTables() {
        List<RankingTable> tables = new ArrayList<>();
        tables.add(easyTable);
        tables.add(mediumTable);
        tables.add(hardTable);
        return tables;
    }
}
