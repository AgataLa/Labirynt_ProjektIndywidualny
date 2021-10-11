package gameComponents;

import GUIComponents.DIFFICULTY;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MazeGenerator {
    private int rows;
    private int cols;
    private Cell[][] grid;
    private Cell current;
    private Cell next;
    private List<Cell> stack;
    private int count = 0;
    private Image frontWall;
    private Image sideWall;
    private int startRow;
    private int startCol;
    private int endRow;
    private int endCol;

    public MazeGenerator() {
        stack = new LinkedList<>();
    }

    public void initializeMaze(DIFFICULTY difficulty) {
        stack.clear();
        try {
            frontWall = new Image("maze/wall_front.png", difficulty.getCellSize() + difficulty.getCellSize() * 1 / 7, difficulty.getCellSize() * 3 / 7, false, false);
            sideWall = new Image("maze/side.png", difficulty.getCellSize() * 1 / 7, difficulty.getCellSize() + difficulty.getCellSize() * 3 / 7, false, false);
        } catch (Exception e) {
        }
        this.rows = difficulty.getMazeSize();
        this.cols = difficulty.getMazeSize();
        grid = new Cell[rows][cols];

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                grid[i][j] = new Cell(i, j, difficulty.getCellSize());
            }
        }

        current = grid[0][0];
        current.setVisited();
        stack.add(current);
    }

    public Cell[][] generateMaze(DIFFICULTY difficulty, AnchorPane gamePane) {
        initializeMaze(difficulty);

        while (stack.size() > 0) {
            next = current.getNeighbour(grid);
            if (next != null) {
                count++;
                if(count != 10) {
                    next.setVisited();
                    stack.add(current);
                    removeWalls();
                    current = next;
                } else {
                    stack.add(current);
                    count = 0;
                    int ind;
                    if(stack.size() > 1) {
                        ind = stack.size() / 2 - 1;
                    } else {
                        ind = 0;
                    }
                    current = stack.remove(ind);
                }
            } else if (stack.size() > 0) {
                current = stack.remove(stack.size()-1);
            }
        }

        pickStartAndEndCells();

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                grid[i][j].displayTopWall(gamePane, frontWall, sideWall);
            }
            for(int j = 0; j < cols; j++) {
                grid[i][j].displayRightWall(gamePane, sideWall, frontWall);
            }
        }

        for(int i = 0; i < rows; i++) {
            grid[i][0].displayLeftWall(gamePane, sideWall, frontWall);
            grid[grid.length - 1][i].displayBottomWall(gamePane, frontWall, sideWall);
        }

        grid[startRow][startCol].addStartDoor(gamePane,rows - 1, sideWall, grid);
        grid[endRow][endCol].addEndDoor(gamePane, rows - 1, sideWall, grid);

        return grid;
    }

    private void pickStartAndEndCells() {
        Random random = new Random();

        int side = random.nextInt(4);
        if(side == 0) {
            startRow = 0;
            startCol = random.nextInt(cols);
            endRow = rows - 1;
            endCol = random.nextInt(cols);
        } else if(side == 1) {
            startRow = random.nextInt(rows);
            startCol = cols - 1;
            endRow = random.nextInt(rows);
            endCol = 0;
        } else if(side == 2) {
            startRow = rows - 1;
            startCol = random.nextInt(cols);
            endRow = 0;
            endCol = random.nextInt(cols);
        } else {
            startRow = random.nextInt(rows);
            startCol = 0;
            endRow = random.nextInt(rows);
            endCol = cols - 1;
        }
    }

    private void removeWalls() {
        int rowDiff = current.getRow() - next.getRow();
        int colDiff = current.getCol() - next.getCol();

        if (rowDiff == 1) {
            current.removeWall(0);
            next.removeWall(2);
        } else if (rowDiff == -1) {
            current.removeWall(2);
            next.removeWall(0);
        }

        if (colDiff == 1) {
            current.removeWall(3);
            next.removeWall(1);
        } else if (colDiff == -1) {
            current.removeWall(1);
            next.removeWall(3);
        }
    }

    public boolean isEveryCellVisited() {
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(!grid[i][j].isVisited()) {
                    return false;
                }
            }
        }
        return true;
    }

    public Cell getStartCell() {
        return grid[startRow][startCol];
    }

    public Cell getEndCell() {
        return grid[endRow][endCol];
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public double getWidthSideWall() {
        return sideWall.getWidth();
    }
}
