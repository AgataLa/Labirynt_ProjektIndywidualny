package gameComponentsTests;

import GUIComponents.DIFFICULTY;
import gameComponents.MazeGenerator;
import javafx.scene.layout.AnchorPane;
import org.junit.Assert;
import org.junit.Test;

public class MazeGeneratorTest {

    private final MazeGenerator mazeGenerator = new MazeGenerator();
    private final AnchorPane pane = new AnchorPane();

    @Test
    public void checkIfEasyMazeIsComplete() {
        boolean result = false;

        for(int i = 0; i < 100; i++) {
            mazeGenerator.generateMaze(DIFFICULTY.EASY, pane);
            result = mazeGenerator.isEveryCellVisited();
            if(!result) {
                break;
            }
        }

        boolean expected = true;

        Assert.assertEquals(result, expected);
    }

    @Test
    public void checkIfMediumMazeIsComplete() {
        boolean result = false;

        for(int i = 0; i < 100; i++) {
            mazeGenerator.generateMaze(DIFFICULTY.MEDIUM, pane);
            result = mazeGenerator.isEveryCellVisited();
            if(!result) {
                break;
            }
        }

        boolean expected = true;

        Assert.assertEquals(result, expected);
    }

    @Test
    public void checkIfHardMazeIsComplete() {
        boolean result = false;

        for(int i = 0; i < 100; i++) {
            mazeGenerator.generateMaze(DIFFICULTY.HARD, pane);
            result = mazeGenerator.isEveryCellVisited();
            if(!result) {
                break;
            }
        }

        boolean expected = true;

        Assert.assertEquals(result, expected);
    }


}
